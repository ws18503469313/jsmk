package com.itmuch;

import java.io.Serializable;

/**
 * Created by apple on 2018/7/5.
 */
public class Scale implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 422725227755540560L;
	/**
     * 485地址
     */
    private int address485;
    /**
     * 重量 单位g
     */
    private String weight;
    /**
     * 单重
     */
    private String singleWeight;
    /**
     * 最大量程
     */
    private String maxWeightRange;

    /**
     * 网关序列号
     */
    private String serialId;
    /**
     * 状态
     */
    private int state;

    @Override
    public String toString() {
        return "Scale{" +
                "address485=" + address485 +
                ", weight='" + weight + '\'' +
                ", singleWeight='" + singleWeight + '\'' +
                ", maxWeightRange='" + maxWeightRange + '\'' +
                ", serialId='" + serialId + '\'' +
                ", state=" + state +
                '}';
    }

    public int getAddress485() {
        return address485;
    }

    public void setAddress485(int address485) {
        this.address485 = address485;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getSingleWeight() {
        return singleWeight;
    }

    public void setSingleWeight(String singleWeight) {
        this.singleWeight = singleWeight;
    }

    public String getMaxWeightRange() {
        return maxWeightRange;
    }

    public void setMaxWeightRange(String maxWeightRange) {
        this.maxWeightRange = maxWeightRange;
    }

    public String getSerialId() {
        return serialId;
    }

    public void setSerialId(String serialId) {
        this.serialId = serialId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
