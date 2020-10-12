package com.eastcom.ripple.util;

import com.eastcom.ripple.entity.Job;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobInfo extends Job {
    private String departmentName;
}
