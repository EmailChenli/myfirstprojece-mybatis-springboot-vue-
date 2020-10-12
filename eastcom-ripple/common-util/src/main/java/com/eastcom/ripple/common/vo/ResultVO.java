package com.eastcom.ripple.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @author Chench
 * @date 2020/8/25 9:28
 * @description 统一返回结果的VO类
 */
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ResultVO<T> {
    @ApiModelProperty(value = "返回码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private T data;
}
