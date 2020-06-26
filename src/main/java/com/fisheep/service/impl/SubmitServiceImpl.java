package com.fisheep.service.impl;

import com.fisheep.bean.Submit;
import com.fisheep.dao.HomeworkMapper;
import com.fisheep.dao.SubmitMapper;
import com.fisheep.service.SubmitService;
import com.fisheep.utils.UploadFile;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.util.List;


@Service
public class SubmitServiceImpl implements SubmitService {

    @Autowired
    private SubmitMapper submitMapper;

    @Autowired
    private HomeworkMapper homeworkMapper;

    /**
     * 这里需要新增或者修改submit， 于此同时，需要redis里面新增计数器
     * @param submit
     * @param multipartFile
     * @param uploadFilesPath:要保存文件到哪个目录下面
     * @return
     */
    @Override
    public Boolean insertSubmit(Submit submit, CommonsMultipartFile multipartFile, String uploadFilesPath) {

//        int count = submitMapper.selectCountByHomeworkIdAndUploader(submit.getSubmitHomeworkId(), submit.getUploaderName());
        List<Submit> submits = submitMapper.selectPrefixAndFileNameByHomeworkIdAndUploader(submit.getSubmitHomeworkId(), submit.getUploaderName());
        System.out.println(submit.getSubmitHomeworkId()+"\t和"+submit.getUploaderName() +"查询到的记录submits：\t"+submits.toString());
        int count = submits.size();
        System.out.println("selectCountByHomeworkIdAndUploader:" +count);
        switch (count) {
            case 0:
//                没有就插入
                submitMapper.insertSubmit(submit);
//                同时hw_homework表里面提交数量+1
                homeworkMapper.updateHomeworkSubmittedNumsPlus(submit.getSubmitHomeworkId());
                break;
            case 1:
//                一条就更新， 并删除文件，因为后面要重新存储，所以这里直接遍历list删除；
                submitMapper.updateSubmit(submit);
                for (Submit submitTmp : submits) {
//                    String filePath = "src/main/webapp/uploadfiles/"+submitTmp.getSubmitLocation()+"/"+submitTmp.getFileSuffix()+submitTmp.getSubmitFileName();
//                    String existFilePath = "C:\\Users\\Fisheep\\Desktop\\Code\\homework\\src\\main\\webapp\\uploadfiles\\"
//                            +submitTmp.getFileSuffix()+"_"+submitTmp.getSubmitFileName();
                    String existFilePath = uploadFilesPath+ File.separator
                            +submitTmp.getFileSuffix()+"_"+submitTmp.getSubmitFileName();

                    boolean isDelete = UploadFile.deleteFile(existFilePath);
                    if(!isDelete){
                        System.out.println("文件删除失败，事务回滚:"+existFilePath);
                        throw new RuntimeException("文件删除失败，事务回滚");
                    }
                }
                break;
            default:
//                条数>=2条的时候先删除所有符合条件的，再插入
//                此种情况很难出现
                submitMapper.deleteSubmitByHomeworkIdAndUploader(submit.getSubmitHomeworkId(), submit.getUploaderName());
                submitMapper.insertSubmit(submit);
        }

        //保存文件
        String fileName = submit.getFileSuffix() + "_" + submit.getSubmitFileName();
        Boolean fileSave = UploadFile.fileSave(multipartFile, uploadFilesPath, fileName);
        return fileSave;
    }


    @Override
    public List<Submit> selectAllByHomeworkId(int submitHomeworkId) {
        return submitMapper.selectAllByHomeworkId(submitHomeworkId);
    }

    @Override
    public List<Submit> getSubmittedFilesByHomeworkId(int homeworkid) {
        List<Submit> submits =  submitMapper.getSubmittedFilesByHomeworkId(homeworkid);
        return submits;
    }
}
