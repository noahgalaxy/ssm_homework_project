package com.fisheep.controller;

import com.fisheep.bean.Submit;
import com.fisheep.dao.HomeworkMapper;
import com.fisheep.service.HomeworkService;
import com.fisheep.service.SubmitService;
import com.fisheep.utils.Msg;
import com.fisheep.utils.SnowAlgorithum;
import com.fisheep.utils.UploadFile;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.runners.Parameterized;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Controller
public class CommonController {

    private static Logger logger = LogManager.getLogger(CommonController.class.getName());

    @Autowired
    private SubmitService submitServiceImpl;

    @Autowired
    private HomeworkService homeworkServiceImpl;

    //    再session里面获取uid
    @RequestMapping(path = "/getUidBySession")
    @ResponseBody
    public Msg getUidBySession(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        System.out.println("控制端 session：" + session.toString() + "\n" + (Integer) session.getAttribute("uid") +
                "\nsessionID" + session.getId());
        if (session.isNew()) {
            System.out.println("getUidBySession fail");
            logger.error("getUidBySession fail");
            return Msg.fail();
        }
        System.out.println("getUidBySession success");
        return Msg.success().add("uid", session.getAttribute("uid"));
    }

//    @RequestMapping(path = "/fileUpload", method = RequestMethod.POST)
//    @ResponseBody
//    public Msg fileUpload(MultipartFile multipartFile,
//                          @RequestParam("uploaderName") String uploaderName,
//                          @RequestParam("homeworkId") int homeworkId,
//                          @RequestParam("homeworkCode") String homeworkCode,
//                          @RequestParam("location") String location){
//        //1.获取上传文件真实名字
//        String originalFilename = multipartFile.getOriginalFilename();
//        long size = multipartFile.getSize();
//        System.out.println("上传文件true filename："+originalFilename +"\nsize："+size/1024 +"M");
//        String newFIleName = homeworkId+homeworkCode+"_"+originalFilename;
//        String Dir = "";
//        System.out.println("newFIleName: "+newFIleName);
////
////        File file = new File(Dir+newFIleName);
////        try {
////            multipartFile.transferTo(file);
////        } catch (IOException e) {
////            e.printStackTrace();
////            return Msg.fail();
////        }
//        return Msg.success();
//    }

    @RequestMapping(path = "/fileUpload", method = RequestMethod.POST)
    @ResponseBody
    public Msg fileUpload1(@RequestParam("input-b2") CommonsMultipartFile multipartFile,
                           @RequestParam("uploaderName") String uploaderName,
                           @RequestParam("homeworkId") int homeworkId,
                           @RequestParam("homeworkCode") String homeworkCode,
                           @RequestParam("location") String location,
                           HttpServletRequest request) {
        String uploadFilesPath = request.getSession().getServletContext().getRealPath("/uploadfiles");
        if (multipartFile.isEmpty()) {
            logger.info("上传文件为空");
//            System.out.println("上传文件为空");
            return Msg.fail();
        }
        //1.获取上传文件真实名字
        String originalFilename = multipartFile.getOriginalFilename();
        long size = multipartFile.getSize();
        logger.info("上传文件true filename：" + originalFilename + "\nsize：" + size / 1024. / 1024. + "M");
        logger.info("uploaderName" + uploaderName+"\nhomeworkId  " + homeworkId + "\thomeworkCode  " + homeworkCode+"\nlocation:" + location);
//        String code = SnowAlgorithum.getCode();

        //2.获取一个UUID用作文件名前缀
        String uuid = UUID.randomUUID().toString();
//        System.out.println("UUID：" + uuid);
        logger.info("UUID：" + uuid);
//        String fileName = homeworkId+"_"+uploaderName+"_"+originalFilename;
//        String fileName = uuid+"_"+originalFilename;

        //3.服务层处理，服务层加事务，处理保存和数据库插入
        Submit submit = new Submit();
        submit.setSubmitHomeworkId(homeworkId);
        submit.setUploaderName(uploaderName);
        submit.setSubmitLocation(location);
        submit.setSubmitFileName(originalFilename);
        submit.setFileSuffix(uuid);
        Boolean fileSave = submitServiceImpl.insertSubmit(submit, multipartFile, uploadFilesPath);
        if (fileSave) {
            logger.info("文件存储成功");
            return Msg.success();
        }
        logger.info("文件存储失败");
        return Msg.fail();
    }

    @RequestMapping(path = "/filesDownload/{id}")
//    @ResponseBody
    public String downloadFilesTest(HttpServletRequest request, HttpServletResponse res, @PathVariable("id") int homeworkid) throws IOException {
        logger.info("下载目录获取的下载作业id："+homeworkid);
        res.setCharacterEncoding("UTF-8"); //设置编码字符
        request.setCharacterEncoding("UTF-8");
        res.setContentType("text/html;charset=utf-8");
        List<Submit> submits = submitServiceImpl.getSubmittedFilesByHomeworkId(homeworkid);
        if (submits.isEmpty()){
//            return Msg.fail().add("message", "数据库没有提交记录");
            res.getWriter().print("<div> 数据库没有提交记录</div>");
            res.getWriter().flush();
            res.getWriter().close();
            return null;
        }
        try {
            //获取文件根目录，不同框架获取的方式不一样，可自由切换
            String basePath = request.getSession().getServletContext().getRealPath("/uploadfiles");
            logger.info("获取文件根目录:"+basePath);
            //获取文件名称（包括文件格式）
//            String fileName = "4cfa3aed-b81d-4bee-8068-357121704eb2_专业英语 - 论文.docx";
            List<String> filePaths = new ArrayList<String>();
            Iterator<Submit> submitIterator = submits.iterator();
            while (submitIterator.hasNext()){
                Submit submit = submitIterator.next();
                int submitHomeworkId = submit.getSubmitHomeworkId();
                String fileName = submit.getSubmitFileName();
                String uploaderName = submit.getUploaderName();
                String fileSuffix = submit.getFileSuffix();
                String targetFilePath = basePath + File.separator + fileSuffix+"_"+fileName;
                logger.info("targetFilePath: "+targetFilePath);
                filePaths.add(targetFilePath);
            }

            //方法1：IO流实现下载的功能
            res.setContentType("application/octet-stream;charset=UTF-8"); //设置下载内容类型
//            res.setHeader("Content-disposition", "attachment;filename=" + fileName);//设置下载的文件名称
            OutputStream out = res.getOutputStream();   //创建页面返回方式为输出流，会自动弹出下载框

/*
            //方法1-1：IO字节流下载，用于小文件
            System.out.println("字节流下载");
            InputStream is = new FileInputStream(targetPath);  //创建文件输入流
            byte[] Buffer = new byte[2048];  //设置每次读取数据大小，即缓存大小
            int size = 0;  //用于计算缓存数据是否已经读取完毕，如果数据已经读取完了，则会返回-1
            while ((size = is.read(Buffer)) != -1) {  //循环读取数据，如果数据读取完毕则返回-1
                out.write(Buffer, 0, size); //将每次读取到的数据写入客户端
            }
            is.close();
 */
		  //方法1-3：将附件中多个文件进行压缩，批量打包下载文件
		    //创建压缩文件需要的空的zip包
		    String zipBasePath=request.getSession().getServletContext().getRealPath("/uploadfiles/zip");
		    String homeworkName = homeworkServiceImpl.getHomeworkNameById(homeworkid);
		    logger.info("homeworkServiceImpl.getHomeworkNameById: "+homeworkid);
		    if(homeworkName == null){
                res.getWriter().print("<div> 数据库没有提交记录</div>");
                res.getWriter().flush();
                res.getWriter().close();
                return null;
            }
		    String zipName = homeworkName+".zip";
		    String zipFilePath = zipBasePath+File.separator+zipName;
            logger.info("zip路径："+zipFilePath);

		    //压缩文件
		    File zip = new File(zipFilePath);
		    if (!zip.exists()){
		        zip.createNewFile();
		    }
		    //创建zip文件输出流
		    ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zip));
		    this.zipFile(zipBasePath,zipName, zipFilePath,filePaths,zos);
		    zos.close();
//		    res.setHeader("Content-disposition", "attachment;filename="+new String(zipName.getBytes("utf-8")));//设置下载的压缩文件名称
            //防止文件乱码
		    res.setHeader("Content-disposition", "attachment;filename="+new String(zipName.getBytes("utf-8"), "ISO-8859-1"));//设置下载的压缩文件名称

		    //将打包后的文件写到客户端，输出的方法同上，使用缓冲流输出
		    BufferedInputStream bis = new BufferedInputStream(new FileInputStream(zipFilePath));
//		    byte[] buff = new byte[bis.available()];
		    byte[] buff = new byte[1024];
		    int size = 0;
		    while ((size = bis.read(buff)) != -1){
                //输出数据文件
		        out.write(buff, 0, size);
            }
		    bis.close();
			out.flush();//释放缓存
			out.close();//关闭输出流
        }catch(Exception e) {
            logger.info("文件输出异常");
            e.printStackTrace();
            res.reset();
            res.setCharacterEncoding("UTF-8");
            res.setContentType("text/html;charset=UTF-8");
            res.getWriter().print("<div align=\"center\" style=\"font-size: 30px;font-family: serif;color: red;\">系统内部错误，下载未成功，请联系管理员！</div>"
                    + "<div>错误信息："+e.getMessage()+"</div>");
            res.getWriter().flush();
            res.getWriter().close();
        }
		return null;
    }

    private String zipFile(String zipBasePath, String zipName, String zipFilePath, List<String> filePaths,ZipOutputStream zos) throws IOException {

        //循环读取文件路径集合，获取每一个文件的路径
        for (String filePath : filePaths) {
            File inputFile = new File(filePath);  //根据文件路径创建文件
            if (inputFile.exists()) { //判断文件是否存在
                if (inputFile.isFile()) {  //判断是否属于文件，还是文件夹
                    //创建输入流读取文件
                    BufferedInputStream bis = new BufferedInputStream(new FileInputStream(inputFile));

                    //将文件写入zip内，即将文件进行打包
                    zos.putNextEntry(new ZipEntry(inputFile.getName().substring(36)));
                    //写入文件的方法，同上
                    int size = 0;
                    byte[] buffer = new byte[1024];  //设置读取数据缓存大小
                    while ((size = bis.read(buffer)) > 0) {
                        zos.write(buffer, 0, size);
                    }
                    //关闭输入输出流
                    zos.closeEntry();
                    bis.close();

                } else {  //如果是文件夹，则使用穷举的方法获取文件，写入zip
                    try {
                        File[] files = inputFile.listFiles();
                        List<String> filePathsTem = new ArrayList<String>();
                        for (File fileTem : files) {
                            filePathsTem.add(fileTem.toString());
                        }
                        return zipFile(zipBasePath, zipName, zipFilePath, filePathsTem, zos);
                    } catch (Exception e) {
                        logger.info("文件读入异常");
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }
}