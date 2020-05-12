package com.fisheep.bean;

import java.io.Serializable;

public class UserHasGroup implements Serializable {
    private Integer uhsId;
    private Integer uhgUid;
    private Integer uhgGroupId;
    private Group group;

    @Override
    public String toString() {
        return "UserHasGroup{" +
                "uhsId=" + uhsId +
                ", uhgUid=" + uhgUid +
                ", uhgGroupId=" + uhgGroupId +
                ", groupl=" + group +
                '}';
    }

    public Integer getUhsId() {
        return uhsId;
    }

    public void setUhsId(Integer uhsId) {
        this.uhsId = uhsId;
    }

    public Integer getUhgUid() {
        return uhgUid;
    }

    public void setUhgUid(Integer uhgUid) {
        this.uhgUid = uhgUid;
    }

    public Integer getUhgGroupId() {
        return uhgGroupId;
    }

    public void setUhgGroupId(Integer uhgGroupId) {
        this.uhgGroupId = uhgGroupId;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
