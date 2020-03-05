package com.itmuch.testhugedata;

import com.alibaba.fastjson.JSON;
import com.cloud.util.excel.export.ExportExcel;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
public class Data {
    private static String getUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }
    @ExportExcel(name = "uuid0", order = 0)
    private String uuid0;
    @ExportExcel(name = "uuid1", order = 1)
    private String uuid1;
    @ExportExcel(name = "uuid2", order = 2)
    private String uuid2;
    @ExportExcel(name = "uuid3", order = 3)
    private String uuid3;
    @ExportExcel(name = "uuid4", order = 4)
    private String uuid4;
    @ExportExcel(name = "uuid5", order = 5)
    private String uuid5;
    @ExportExcel(name = "uuid6", order = 6)
    private String uuid6;
    @ExportExcel(name = "uuid7", order = 7)
    private String uuid7;
    @ExportExcel(name = "uuid8", order = 8)
    private String uuid8;
    @ExportExcel(name = "uuid9", order = 9)
    private String uuid9;
    @ExportExcel(name = "uuid10", order = 10)
    private String uuid10;
    @ExportExcel(name = "uuid11", order = 11)
    private String uuid11;
    @ExportExcel(name = "uuid12", order = 12)
    private String uuid12;
    @ExportExcel(name = "uuid13", order = 13)
    private String uuid13;
    @ExportExcel(name = "uuid14", order = 14)
    private String uuid14;
    @ExportExcel(name = "uuid15", order =15)
    private String uuid15;
    @ExportExcel(name = "uuid16", order = 16)
    private String uuid16;
    @ExportExcel(name = "uuid17", order = 17)
    private String uuid17;
    @ExportExcel(name = "uuid18", order = 18)
    private String uuid18;
    @ExportExcel(name = "uuid19", order = 19)
    private String uuid19;

    public Data() {
        this.uuid0 = getUUID();
        this.uuid1 = getUUID();
        this.uuid2 = getUUID();
        this.uuid3 = getUUID();
        this.uuid4 = getUUID();
        this.uuid5 = getUUID();
        this.uuid6 = getUUID();
        this.uuid7 = getUUID();
        this.uuid8 = getUUID();
        this.uuid9 = getUUID();
        this.uuid10 = getUUID();
        this.uuid11 = getUUID();
        this.uuid12 = getUUID();
        this.uuid13 = getUUID();
        this.uuid14 = getUUID();
        this.uuid15 = getUUID();
        this.uuid16 = getUUID();
        this.uuid17 = getUUID();
        this.uuid18 = getUUID();
        this.uuid19 = getUUID();
    }


    public static void main(String args[]) throws Exception{
        Data data = new Data();
        System.out.println(JSON.toJSONString(data));
    }
}
