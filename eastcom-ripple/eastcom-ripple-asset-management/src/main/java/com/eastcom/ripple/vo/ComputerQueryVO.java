package com.eastcom.ripple.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Hongzh
 * @date 2020/09/17
 * @description 查询电脑信息的参数包装类对象
 */
@Setter
@Getter
@ToString
public class ComputerQueryVO extends BaseSearchQueryVO {
    //电脑状态
    private Integer comStatus;
}
