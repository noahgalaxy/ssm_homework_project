package com.fisheep.dao;

import com.fisheep.bean.Submit;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SubmitMapper {

    void insertSubmit(Submit submit);


    int selectCountByHomeworkIdAndUploader(@Param("submitHomeworkId") int submitHomeworkId,
                                           @Param("uploaderName") String uploaderName);

    void updateSubmit(Submit submit);

    void deleteSubmitByHomeworkIdAndUploader(int submitHomeworkId, String uploaderName);

    List<Submit> selectPrefixAndFileNameByHomeworkIdAndUploader(@Param("submitHomeworkId") int submitHomeworkId,
                                                                @Param("uploaderName") String uploaderName);


    List<Submit> selectAllByHomeworkId(int submitHomeworkId);

    List<Submit> getSubmittedFilesByHomeworkId(int homeworkid);
}
