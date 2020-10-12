package com.eastcom.ripple.dto;

import com.eastcom.ripple.entity.computer.ComEmpRecord;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Hongzh
 * @date 2020/09/15
 * @description 电脑与员工一对一关系实体类的数据传输对象
 */
@ToString
@Setter
@Getter
public class ComEmpRecordDTO extends ComEmpRecord implements Serializable {
    //资产编号
    private String assetNum;
}