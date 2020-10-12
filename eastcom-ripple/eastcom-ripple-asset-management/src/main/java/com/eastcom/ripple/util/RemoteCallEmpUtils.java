package com.eastcom.ripple.util;

import com.eastcom.ripple.common.vo.ResultVO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author Hongzh
 * @date 2020/09/21
 * @description  远程调用员工模块的工具类
 */
@Component
public class RemoteCallEmpUtils {
    //定义要访问的服务地址
    private static final String PAYMENT_URL = "http://localhost:8003/sys/employee";

    //引入restTemplate
    @Resource
    private RestTemplate restTemplate;

    /**
     * 根据员工id集合远程调用员工模块接口，进行批量查询员工姓名
     * @param employeeIds
     * @return
     */
    public ResultVO<Map<String, Map<String,String>>> getBatchQueryEmpName(List<Long> employeeIds,String token){
        //headers
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("token", token);
        //HttpEntity
        HttpEntity<List<Long>> requestEntity = new HttpEntity<>(employeeIds, requestHeaders);
        //post
       return restTemplate.postForObject(PAYMENT_URL + "/batchQueryEmpName", requestEntity, ResultVO.class);
    }

    /**
     * 根据员工id查询对应员工信息
     * @param employeeId
     * @return
     */
    public ResultVO<Map<String,Object>> findEmployeeById(Long employeeId,String token){
        //url
        String url = PAYMENT_URL+"/findbyid/"+employeeId;
        //headers
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("token", token);
        //HttpEntity
        HttpEntity<MultiValueMap<String,String>> requestEntity = new HttpEntity<>(requestHeaders);
//        restTemplate.getForObject(PAYMENT_URL+"/findbyid/"+employeeId,requestEntity,ResultVO.class);
        ResponseEntity<ResultVO> exchange = restTemplate.exchange(url, HttpMethod.GET, requestEntity, ResultVO.class);
        ResultVO<Map<String,Object>> body = (ResultVO<Map<String,Object>>) exchange.getBody();
        return body;
    }
}
