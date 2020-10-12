package com.eastcom.ripple.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eastcom.ripple.common.util.ResultUtils;
import com.eastcom.ripple.common.vo.ResultVO;
import com.eastcom.ripple.entity.MeetingRecord;
import com.eastcom.ripple.mapper.MeetingRecordMapper;
import com.eastcom.ripple.service.MeetingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Lijj
 * @since 2020-09-15
 */
@Service
public class MeetingRecordServiceImpl extends ServiceImpl<MeetingRecordMapper, MeetingRecord> implements MeetingRecordService {

    @Autowired
    private MeetingRecordMapper meetingRecordMapper;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public ResultVO findAll(int page, int rows) {
        try{
            Page<MeetingRecord> meetingRecordPage = new Page<>(page, rows);
            baseMapper.selectPage(meetingRecordPage,null);
            List<MeetingRecord> list = meetingRecordPage.getRecords();
            Map<String,Object> map = new HashMap<>();
            map.put("list",list);
            map.put("count",meetingRecordPage.getTotal());
            return ResultUtils.success("查询成功",map);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResultUtils.error("查询失败");
    }

    @Transactional
    @Override
    public ResultVO deleteRecord(int recordId) {
        QueryWrapper<MeetingRecord> wrapper = new QueryWrapper<>();
        wrapper.eq("record_id",recordId);
        try{
            int count = meetingRecordMapper.delete(wrapper);
            return count > 0 ? ResultUtils.success("删除成功") : ResultUtils.error("删除失败");
        }catch(Exception e){
            e.printStackTrace();
        }
        return ResultUtils.error("错误");
    }

    @Override
    public ResultVO searchRecord(Map<String,Object> paras, int page, int rows) {
        try{
            String para = (String)paras.get("para");
            Page<MeetingRecord> meetingRecordPage = new Page<>(page, rows);
            QueryWrapper<MeetingRecord> wrapper = new QueryWrapper<>();
            wrapper.like("user_name",para)
                    .or().like("realname",para)
                    .or().like("room_id",para);
            baseMapper.selectPage(meetingRecordPage,wrapper);
            List<MeetingRecord> list = meetingRecordPage.getRecords();
            Map<String,Object> map = new HashMap<>();
            map.put("count",meetingRecordPage.getTotal());
            map.put("list",list);
            return ResultUtils.success("查询成功",map);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResultUtils.error("查询失败");
    }

    @Override
    public ResultVO findReserveTimes(int page, int rows, String token) {
        String url = "http://localhost:8003/sys/employee/findReserveTimes";
        //headers
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("token", token);
        //body
        //MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        //requestBody.add("roundid", "1");
        //HttpEntity
        HttpEntity<MultiValueMap> requestEntity = new HttpEntity<>(requestHeaders);
        ResultVO resultVO = restTemplate.postForObject(url + "/" + page + "/" + rows, requestEntity, ResultVO.class);
        if(resultVO.getCode() == 200){
            return ResultUtils.success(resultVO.getData());
        }else{
            return ResultUtils.error();
        }

    }
}
