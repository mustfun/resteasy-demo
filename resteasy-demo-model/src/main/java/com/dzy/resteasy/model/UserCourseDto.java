package com.dzy.resteasy.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author dengzhiyuan
 * @version 1.0
 * @date 2017/8/25
 * @since 1.0
 */
public class UserCourseDto {

    private String openType;
    private Long userID;
    private Long classID;
    private String className;
    private BigDecimal chargeFee;
    private Integer classStudyTime;
    private BigDecimal openRMB;
    private BigDecimal rechargeFee;
    private BigDecimal awardFee;
    private BigDecimal openFee;
    private Date dateAdded;
    private Date expiredDate;

    public String getOpenType() {
        return openType;
    }

    public void setOpenType(String openType) {
        this.openType = openType;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public Long getClassID() {
        return classID;
    }

    public void setClassID(Long classID) {
        this.classID = classID;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public BigDecimal getChargeFee() {
        return chargeFee;
    }

    public void setChargeFee(BigDecimal chargeFee) {
        this.chargeFee = chargeFee;
    }

    public Integer getClassStudyTime() {
        return classStudyTime;
    }

    public void setClassStudyTime(Integer classStudyTime) {
        this.classStudyTime = classStudyTime;
    }

    public BigDecimal getOpenRMB() {
        return openRMB;
    }

    public void setOpenRMB(BigDecimal openRMB) {
        this.openRMB = openRMB;
    }

    public BigDecimal getRechargeFee() {
        return rechargeFee;
    }

    public void setRechargeFee(BigDecimal rechargeFee) {
        this.rechargeFee = rechargeFee;
    }

    public BigDecimal getAwardFee() {
        return awardFee;
    }

    public void setAwardFee(BigDecimal awardFee) {
        this.awardFee = awardFee;
    }

    public BigDecimal getOpenFee() {
        return openFee;
    }

    public void setOpenFee(BigDecimal openFee) {
        this.openFee = openFee;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }
}
