package com.eastcom.ripple.entity.asset;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Hongzh
 * @date 2020/09/11
 * @description 资产实体类
 */
@Setter
@Getter
@ToString
public class Asset implements Serializable {
    //序号
    private Integer id;
    //资产编号
    private String assetNum;
    //资产名称
    private String assetName;
    //单价
    private Double unitPrice;
    //生产厂商
    private String producer;
    //生产日期
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date productionDate;
    //入库时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date storageTime;
    //购买人
    private String purchaser;
    //资产类型ID
    private Integer assetTypeId;
    //创建时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    //更新时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
}
