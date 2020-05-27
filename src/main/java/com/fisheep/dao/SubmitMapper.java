package com.fisheep.dao;

import com.fisheep.bean.Submit;
import org.apache.ibatis.annotations.Param;

public interface SubmitMapper {

    void insertSubmit(Submit submit);


    int selectCountByHomeworkIdAndUploader(@Param("submitHomeworkId") int submitHomeworkId,
                                           @Param("uploaderName") String uploaderName);

    void updateSubmit(Submit submit);

    void deleteSubmitByHomeworkIdAndUploader(int submitHomeworkId, String uploaderName);
}
