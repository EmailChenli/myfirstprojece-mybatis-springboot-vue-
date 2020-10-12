package com.eastcom.ripple.util;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DataFormat<T,D> {
    private T token;
    private D data;
}
