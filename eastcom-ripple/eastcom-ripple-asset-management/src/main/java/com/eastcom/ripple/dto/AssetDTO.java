package com.eastcom.ripple.dto;

import com.eastcom.ripple.entity.asset.Asset;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Hongzh
 * @date 2020/09/14
 * @description 资产数据传输对象
 */
@Setter
@Getter
@ToString
public class AssetDTO extends Asset implements Serializable {
    //资产类型名称
    private String assetTypeName;
}
