package com.eastcom.ripple.vo;

import lombok.Data;

/**
 * @author Lijj
 * @date 2020/9/17 15:38
 */
@Data
public class BookingStatusVo {

    private Integer bookingId;

    private String rejectReason;

    private Integer status;
}
