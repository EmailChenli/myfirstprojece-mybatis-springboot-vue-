package com.eastcom.ripple.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRole<R,P> {
    private R role;
    private P page;
}
