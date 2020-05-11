package com.fisheep.service.impl;


import com.fisheep.bean.Belong;
import com.fisheep.bean.Homework;
import com.fisheep.service.BelongService;
import com.fisheep.service.HomeworkAndBelongService;
import com.fisheep.service.HomeworkService;
import com.fisheep.utils.Msg;
import com.fisheep.utils.StringToNum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class HomeworkAndBelongServiceImpl implements HomeworkAndBelongService {
    @Autowired
    @Qualifier("homeworkServiceImpl")
    HomeworkService homeworkServiceImpl;

    @Autowired
    BelongService belongServiceImpl;

    /**
     * 接收一个待插入的homerk对象，对象里面包含groupsString，取出这个属性解析出groupIds，
     * 首先插入作业，然后返回刚插入的作业的id，拿到这个id。
     * 如果解析出来的groupIds为空，则返回，不为空则插入插入belong表。
     * @param homework
     * @return
     */
    @Override
    public boolean insertHomeworkAndBelong(Homework homework) {

        int rowsAffected = homeworkServiceImpl.insertHomework(homework);
        System.out.println("HomeworkController lastInsertId:"+ rowsAffected);
        //处理作业所属组字符串，返回由单个数字组成的列表
        List<Integer> groupsIdsList = StringToNum.numStringToSingleNum(homework.getGroupsIdString());
        System.out.println("groupsIdsList:"+groupsIdsList.toString()+"\nsize:"+groupsIdsList.size());
        //返回的列表不为空，且长度大于0，有值才能插入。
        if (null != groupsIdsList && groupsIdsList.size() > 0){
            /*测试异常回滚
            System.out.println("1/0之前");
            float i = 1/0;
            System.out.println("1/0之后");
             */
            List<Belong> belongList = new ArrayList<>();
            for(int num: groupsIdsList){
                belongList.add(new Belong(homework.getHomeworkId(), num));
            }
            System.out.println("belongList:"+belongList);
            //插入的行数
            int belongRowsAffected = belongServiceImpl.insertBelong(belongList);
            System.out.println("belongRowsAffected:"+belongRowsAffected+"\tgroupsIdsList.size():"+groupsIdsList.size());
            if(belongRowsAffected != groupsIdsList.size()){
                return false;
            }
        }
//        homeworkServiceImpl.insertBelong()
        if(rowsAffected > 0 ){
            return true;
        }
        return false;
    }
}
