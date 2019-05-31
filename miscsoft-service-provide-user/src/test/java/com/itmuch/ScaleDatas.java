package com.itmuch;


import java.io.Serializable;
import java.util.List;

/**
 * Created by apple on 2018/7/5.
 */
public class ScaleDatas implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 5320225803379269593L;
	/**
     * 网关序列号
     */
    private String serialId;
    /**
     * 每台秤的数据集
     */
    private List<Scale> scales;

    public String getSerialId() {
        return serialId;
    }

    public void setSerialId(String serialId) {
        this.serialId = serialId;
    }

    public List<Scale> getScales() {
        return scales;
    }

    public void setScales(List<Scale> scales) {
        this.scales = scales;
    }

    @Override
    public String toString() {
        return "ScaleDatas{" +
                "serialId='" + serialId + '\'' +
                ", scales=" + scales +
                '}';
    }
}
