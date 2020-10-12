package com.eastcom.ripple.entity.computer;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Hongzh
 * @date 2020/09/09
 * @description   电脑实体类
 */
@Setter
@Getter
@ToString
public class Computer implements Serializable {
    //电脑SN码
    private String comSn;
    //资产编号
    private String assetNum;
    //电脑类型
    private String comType;
    //电脑名称
    private String comName;
    //电脑处理器
    private String comCpu;
    //电脑内存
    private String comMemory;
    //持有人工号
    private Long holderNum;
    //电脑状态
    private Integer comStatus;
    //创建时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    //更新时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
}
