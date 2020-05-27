package com.fisheep.service.impl;

import com.fisheep.bean.Submit;
import com.fisheep.dao.SubmitMapper;
import com.fisheep.service.SubmitService;
import com.fisheep.utils.UploadFile;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;


@Service
public class SubmitServiceImpl implements SubmitService {

    @Autowired
    private SubmitMapper submitMapper;

    @Override
    public Boolean insertSubmit(Submit submit, CommonsMultipartFile multipartFile) {

        int count = submitMapper.selectCountByHomeworkIdAndUploader(submit.getSubmitHomeworkId(), submit.getUploaderName());
        System.out.println("selectCountByHomeworkIdAndUploader:" +count);
        switch (count) {
            case 0:
//                没有就插入
                submitMapper.insertSubmit(submit);
                break;
            case 1:
//                一条就更新
                submitMapper.updateSubmit(submit);
                break;
            default:
//                条数>=2条的时候先删除所有符合条件的，再插入
//                此种情况很难出现
                submitMapper.deleteSubmitByHomeworkIdAndUploader(submit.getSubmitHomeworkId(), submit.getUploaderName());
                submitMapper.insertSubmit(submit);
        }

        //保存文件
        String fileName = submit.getFileSuffix() + "_" + submit.getSubmitFileName();
        Boolean fileSave = UploadFile.fileSave(multipartFile, UploadFile.getFileSavePath(), fileName);
        return fileSave;
    }
}
