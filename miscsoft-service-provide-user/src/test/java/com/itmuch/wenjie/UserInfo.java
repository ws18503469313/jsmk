package com.itmuch.wenjie;

import com.cloud.util.excel.export.ExportExcel;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    @ExportExcel(order = 0, name = "序号")
    private Integer id;
    @ExportExcel(order = 1, name = "姓名")
    private String name;
    @ExportExcel(order = 2, name = "身份证")
    private String IDCard;
    @ExportExcel(order = 3, name = "手机号")
    private String phone;
    @ExportExcel(order = 4, name = "地址")
    private String address;

    public UserInfo(Integer id) {
        this.id = id;
    }

}
