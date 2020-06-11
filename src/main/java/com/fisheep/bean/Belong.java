package com.fisheep.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;


public class Belong implements Serializable {
    private int belongId;
    private int belongHomweorkId;
    private int belongGroupId;

    public Belong() {
    }

    public Belong(int belongHomweorkId, int belongGroupId) {
        this.belongHomweorkId = belongHomweorkId;
        this.belongGroupId = belongGroupId;
    }

    public Belong(int belongId, int belongHomweorkId, int belongGroupId){
        this.belongId = belongId;
        this.belongHomweorkId = belongHomweorkId;
        this.belongGroupId = belongGroupId;
    }

    @Override
    public String toString() {
        return "Belong{" +
                "belongId=" + belongId +
                ", belongHomweorkId=" + belongHomweorkId +
                ", belongGroupId=" + belongGroupId +
                '}';
    }

    public int getBelongId() {
        return belongId;
    }

    public void setBelongId(int belongId) {
        this.belongId = belongId;
    }

    public int getBelongHomweorkId() {
        return belongHomweorkId;
    }

    public void setBelongHomweorkId(int belongHomweorkId) {
        this.belongHomweorkId = belongHomweorkId;
    }

    public int getBelongGroupId() {
        return belongGroupId;
    }

    public void setBelongGroupId(int belongGroupId) {
        this.belongGroupId = belongGroupId;
    }
}
