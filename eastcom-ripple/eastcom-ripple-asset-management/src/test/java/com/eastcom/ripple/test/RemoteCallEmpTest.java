package com.eastcom.ripple.test;

import com.eastcom.ripple.common.vo.ResultVO;
import com.eastcom.ripple.util.RemoteCallEmpUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author Hongzh
 * @date 2020/09/21
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RemoteCallEmpTest {

    @Resource
    private RemoteCallEmpUtils remoteCallEmpUtils;

    @Test
    public void getBatchQueryEmpNameTest(){
        Long holderNum = 1L;
        String token = "12333.233.423";
        ArrayList<Long> al = new ArrayList<>();
        al.add(1L);
        al.add(2L);
        al.add(3L);
        ResultVO<Map<String, Map<String,String>>> batchQueryEmpName = remoteCallEmpUtils.getBatchQueryEmpName(al,token);

//        Map<String, Map<String,String>> data = batchQueryEmpName.getData();
//        System.out.println(holderNum.toString());
//        System.out.println(data.get(holderNum.toString()).get("employeeName"));
//        System.out.println(batchQueryEmpName);
    }

    @Test
    public void findEmployeeByIdTest(){
        Long holderNum = 1L;
        String token = "12333.233.423";
        ResultVO<Map<String,Object>> employee = remoteCallEmpUtils.findEmployeeById(holderNum,token);
        System.out.println(employee.getData().get("employeeName"));
    }
}
