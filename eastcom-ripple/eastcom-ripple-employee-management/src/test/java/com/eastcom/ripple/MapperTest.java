package com.eastcom.ripple;

import com.eastcom.ripple.common.vo.employee.BatchQueryEmpVO;
import com.eastcom.ripple.mapper.EmployeeMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MapperTest {

    @Resource
    private EmployeeMapper employeeMapper;
    @Test
    public void testEmp(){
        ArrayList<Long> al = new ArrayList<>();
//        al.add(1L);
//        al.add(2L);
//        al.add(3L);
        Map<Long, BatchQueryEmpVO> map = employeeMapper.batchQueryEmpName(al);
        System.out.println(map);
    }
}
