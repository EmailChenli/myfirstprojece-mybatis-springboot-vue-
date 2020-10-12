package com.eastcom.ripple.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Page {
	private int pageSize;
	private int currentPage;
}
