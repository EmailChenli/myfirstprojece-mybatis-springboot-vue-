package com.eastcom.ripple.util;

import com.eastcom.ripple.entity.SysUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageSysUser<S,P> {
    private S sysUser;
    private P page;
}
