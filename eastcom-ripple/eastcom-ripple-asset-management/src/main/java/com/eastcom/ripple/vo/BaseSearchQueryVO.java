package com.eastcom.ripple.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Hongzh
 * @date 2020/09/17
 * @description 通用查询参数包装类对象
 */
@Setter
@Getter
@ToString
public class BaseSearchQueryVO {
    //关键词
    private String keyword;
    //当前页
    private Integer currentPage;
    //每页记录数
    private Integer pageSize;
}
