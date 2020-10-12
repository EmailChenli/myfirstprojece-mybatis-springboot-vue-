package com.eastcom.ripple.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author Lijj
 * @since 2020-09-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MeetingBooking implements Serializable {

private static final long serialVersionUID=1L;

    @TableId(value = "booking_id", type = IdType.AUTO)
    private Integer bookingId;

    private String meetingName;

    private Integer meetingNumber;

    private String userName;

    private String roomId;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    private Integer status;

    private String rejectReason;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date applyTime;

    private String meetingDescription;


}
