package com.eastcom.ripple.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Hongzh
 * @date 2020/09/23
 * @description 电脑分配历史记录的视图包装类
 */
@Setter
@Getter
@ToString
public class ComHistoricalRecordVO implements Serializable {
    //历史记录ID
    private Integer historicalId;
    //电脑SN码
    private String historicalComSn;
    //资产编号
    private String historicalAssetNum;
    //员工工号
    private Long historicalEmpNum;
    //员工名称
    private String historicalEmpName;
    //得到电脑时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date getComTime;
    //归还电脑时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date returnComTime;

    /**
     * 将对象转换为字符串数组
     * @return
     */
    public String[] toStringArray() {
        String returnTime = "";
        if (getReturnComTime() != null){
            returnTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getReturnComTime());
        }
        return new String[]{historicalId.toString(),historicalComSn,historicalAssetNum,historicalEmpNum.toString(),
                historicalEmpName,new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getGetComTime()),returnTime};
    }
}
