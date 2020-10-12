package com.eastcom.ripple.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Hongzh
 * @date 2020/09/18
 * @description 电脑视图包装对象
 */
@Setter
@Getter
@ToString
public class ComputerVO {
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
    //持有人姓名
    private String holderName;
    //电脑状态
    private Integer comStatus;
}
