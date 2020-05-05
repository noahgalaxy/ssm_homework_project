package com.fisheep.bean;

import java.io.Serializable;
import java.util.Date;

public class Homework implements Serializable {
    private int homeworkId;
    private String homeworkName;
    private String homeworkCode;
    private String homeworkDead;
    private String location;
    private int homeworkCreatorId;
    private int homeworktotalnums;
    private int homeworksubmittednums;
    private String groupsIdString;

    @Override
    public String toString() {
        return "Homework{" +
                "homeworkId=" + homeworkId +
                ", homeworkName='" + homeworkName + '\'' +
                ", homeworkCode='" + homeworkCode + '\'' +
                ", homeworkDead='" + homeworkDead + '\'' +
                ", location='" + location + '\'' +
                ", homeworkCreatorId=" + homeworkCreatorId +
                ", homeworktotalnums=" + homeworktotalnums +
                ", homeworksubmittednums=" + homeworksubmittednums +
                ", groupsIdString='" + groupsIdString + '\'' +
                '}';
    }

    public int getHomeworkId() {
        return homeworkId;
    }

    public void setHomeworkId(int homeworkId) {
        this.homeworkId = homeworkId;
    }

    public String getHomeworkName() {
        return homeworkName;
    }

    public void setHomeworkName(String homeworkName) {
        this.homeworkName = homeworkName;
    }

    public String getHomeworkCode() {
        return homeworkCode;
    }

    public void setHomeworkCode(String homeworkCode) {
        this.homeworkCode = homeworkCode;
    }

    public String getHomeworkDead() {
        return homeworkDead;
    }

    public void setHomeworkDead(String homeworkDead) {
        this.homeworkDead = homeworkDead;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getHomeworkCreatorId() {
        return homeworkCreatorId;
    }

    public void setHomeworkCreatorId(int homeworkCreatorId) {
        this.homeworkCreatorId = homeworkCreatorId;
    }

    public int getHomeworktotalnums() {
        return homeworktotalnums;
    }

    public void setHomeworktotalnums(int homeworktotalnums) {
        this.homeworktotalnums = homeworktotalnums;
    }

    public int getHomeworksubmittednums() {
        return homeworksubmittednums;
    }

    public void setHomeworksubmittednums(int homeworksubmittednums) {
        this.homeworksubmittednums = homeworksubmittednums;
    }

    public String getGroupsIdString() {
        return groupsIdString;
    }

    public void setGroupsIdString(String groupsIdString) {
        this.groupsIdString = groupsIdString;
    }
}
