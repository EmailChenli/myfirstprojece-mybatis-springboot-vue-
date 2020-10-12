package com.eastcom.ripple.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordUtil {
    private String accountName;
    private String oldPassword;
    private String newPassword;
}
