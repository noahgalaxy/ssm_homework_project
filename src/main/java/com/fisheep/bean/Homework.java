package com.fisheep.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
    private List<Group> groups;
    private boolean expired;

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public Homework() {
    }

    public Homework(String homeworkName, String homeworkCode, String homeworkDead, String location, int homeworkCreatorId, int homeworktotalnums) {
        this.homeworkName = homeworkName;
        this.homeworkCode = homeworkCode;
        this.homeworkDead = homeworkDead;
        this.location = location;
        this.homeworkCreatorId = homeworkCreatorId;
        this.homeworktotalnums = homeworktotalnums;
    }

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
                ", groups=" + groups +
                ", expired=" + expired +
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

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
}
