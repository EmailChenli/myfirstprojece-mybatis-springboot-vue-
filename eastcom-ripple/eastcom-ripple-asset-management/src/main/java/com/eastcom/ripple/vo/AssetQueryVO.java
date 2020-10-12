package com.eastcom.ripple.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Hongzh
 * @date 2020/09/16
 * @description  查询资产信息的参数包装类对象
 */
@Setter
@Getter
@ToString
public class AssetQueryVO extends BaseSearchQueryVO {
    //资产类型对应的资产类型ID
    private Integer assetType;
}
