package com.eastcom.ripple.entity;

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
public class MeetingRoom implements Serializable {

private static final long serialVersionUID=1L;

    private String roomId;

    private String roomName;

    private Integer roomSize;

    private Integer roomStatus;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private String description;


}
