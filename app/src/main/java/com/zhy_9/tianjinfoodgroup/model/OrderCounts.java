package com.zhy_9.tianjinfoodgroup.model;

/**
 * Created by ZHY_9 on 2016/9/7.
 */
public class OrderCounts {
    private String low_stack;
    private String beAcceptedCount;
    private String hasAcceptedCount;
    private String packagedCount;
    private String appraisesCount;
    private String problemCount;

    public OrderCounts() {
    }

    public String getLow_stack() {
        return low_stack;
    }

    public void setLow_stack(String low_stack) {
        this.low_stack = low_stack;
    }

    public String getBeAcceptedCount() {
        return beAcceptedCount;
    }

    public void setBeAcceptedCount(String beAcceptedCount) {
        this.beAcceptedCount = beAcceptedCount;
    }

    public String getHasAcceptedCount() {
        return hasAcceptedCount;
    }

    public void setHasAcceptedCount(String hasAcceptedCount) {
        this.hasAcceptedCount = hasAcceptedCount;
    }

    public String getPackagedCount() {
        return packagedCount;
    }

    public void setPackagedCount(String packagedCount) {
        this.packagedCount = packagedCount;
    }

    public String getAppraisesCount() {
        return appraisesCount;
    }

    public void setAppraisesCount(String appraisesCount) {
        this.appraisesCount = appraisesCount;
    }

    public String getProblemCount() {
        return problemCount;
    }

    public void setProblemCount(String problemCount) {
        this.problemCount = problemCount;
    }

    @Override
    public String toString() {
        return "OrderCounts{" +
                "low_stack='" + low_stack + '\'' +
                ", beAcceptedCount='" + beAcceptedCount + '\'' +
                ", hasAcceptedCount='" + hasAcceptedCount + '\'' +
                ", packagedCount='" + packagedCount + '\'' +
                ", appraisesCount='" + appraisesCount + '\'' +
                ", problemCount='" + problemCount + '\'' +
                '}';
    }
}
