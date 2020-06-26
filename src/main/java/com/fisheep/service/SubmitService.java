package com.fisheep.service;

import com.fisheep.bean.Submit;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.List;

public interface SubmitService {

    Boolean insertSubmit(Submit submit , CommonsMultipartFile multipartFile, String fileuploads);


    List<Submit> selectAllByHomeworkId(int submitHomeworkId);

    List<Submit> getSubmittedFilesByHomeworkId(int homeworkid);
}
