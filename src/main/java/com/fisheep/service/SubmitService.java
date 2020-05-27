package com.fisheep.service;

import com.fisheep.bean.Submit;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public interface SubmitService {

    Boolean insertSubmit(Submit submit , CommonsMultipartFile multipartFile);
}
