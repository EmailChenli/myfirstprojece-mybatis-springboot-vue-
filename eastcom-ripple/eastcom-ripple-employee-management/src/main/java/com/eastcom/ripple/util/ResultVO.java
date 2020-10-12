package com.eastcom.ripple.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultVO<T> {
	private int code;
	private String msg;
	private T data;
}
