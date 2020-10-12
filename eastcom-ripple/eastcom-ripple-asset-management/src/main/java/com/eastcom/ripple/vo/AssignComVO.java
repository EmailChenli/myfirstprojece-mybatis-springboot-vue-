package com.eastcom.ripple.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Hongzh
 * @date 2020/09/22
 * @description  分配电脑记录视图包装类
 */
@Setter
@Getter
@ToString
public class AssignComVO implements Serializable {
    //记录ID
    private Integer recordId;
    //电脑SN码
    private String recordComSn;
    //资产编号
    private String recordAssetNum;
    //员工工号
    private Long recordEmpNum;
    //得到电脑时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date getComTime;
    //归还电脑时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date returnComTime;
}
