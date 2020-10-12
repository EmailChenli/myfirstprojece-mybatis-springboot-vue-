package com.eastcom.ripple.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Hongzh
 * @date 2020/09/16
 * @description  资产类型视图包装对象
 */
@Setter
@Getter
@ToString
public class AssetTypeVO {
    //类型ID
    private Integer typeId;
    //资产类型名称
    private String assetTypeName;
}
