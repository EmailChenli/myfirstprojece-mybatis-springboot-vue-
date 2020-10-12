package com.eastcom.ripple.mapper;

import com.eastcom.ripple.mapper.computer.ComHistoricalRecordMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Hongzh
 * @date 2020/09/22
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ComHistoricalMapperTest {

    @Resource
    private ComHistoricalRecordMapper comHistoricalRecordMapper;

    @Test
    public void findHistoricalIdTest(){
        String date = "2020-09-21 23:33:11";
        try {
            Date date1 =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
            Integer historicalId = comHistoricalRecordMapper.findHistoricalId("6ZRF242", 1L, date1);
            System.out.println(historicalId);
        } catch (ParseException e) {
            e.printStackTrace();
        } }
}
