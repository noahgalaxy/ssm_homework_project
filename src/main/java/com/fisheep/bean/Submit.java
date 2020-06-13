package com.fisheep.bean;

public class Submit {
    private int submitId;
    private String uploaderName;
    private int submitHomeworkId;
    private String fileSuffix;
    private String  submitFileName;
    private String submitLocation;

    public Submit() {
    }

    public Submit(String uploaderName, int submitHomeworkId, String fileSuffix, String submitFileName, String submitLocation) {
        this.uploaderName = uploaderName;
        this.submitHomeworkId = submitHomeworkId;
        this.fileSuffix = fileSuffix;
        this.submitFileName = submitFileName;
        this.submitLocation = submitLocation;
    }

    @Override
    public String toString() {
        return "Submit{" +
                "submitId=" + submitId +
                ", uploaderName='" + uploaderName + '\'' +
                ", submitHomeworkId=" + submitHomeworkId +
                ", fileSuffix='" + fileSuffix + '\'' +
                ", submitFileName='" + submitFileName + '\'' +
                ", submitLocation='" + submitLocation + '\'' +
                '}';
    }

    public int getSubmitId() {
        return submitId;
    }

    public void setSubmitId(int submitId) {
        this.submitId = submitId;
    }

    public String getUploaderName() {
        return uploaderName;
    }

    public void setUploaderName(String uploaderName) {
        this.uploaderName = uploaderName;
    }

    public int getSubmitHomeworkId() {
        return submitHomeworkId;
    }

    public void setSubmitHomeworkId(int submitHomeworkId) {
        this.submitHomeworkId = submitHomeworkId;
    }

    public String getFileSuffix() {
        return fileSuffix;
    }

    public void setFileSuffix(String fileSuffix) {
        this.fileSuffix = fileSuffix;
    }

    public String getSubmitFileName() {
        return submitFileName;
    }

    public void setSubmitFileName(String submitFileName) {
        this.submitFileName = submitFileName;
    }

    public String getSubmitLocation() {
        return submitLocation;
    }

    public void setSubmitLocation(String submitLocation) {
        this.submitLocation = submitLocation;
    }
}
