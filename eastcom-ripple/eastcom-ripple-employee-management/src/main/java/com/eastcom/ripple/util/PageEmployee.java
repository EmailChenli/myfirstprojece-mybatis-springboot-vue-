package com.eastcom.ripple.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageEmployee<E,P> {
    private E employee;
    private P page;
}
