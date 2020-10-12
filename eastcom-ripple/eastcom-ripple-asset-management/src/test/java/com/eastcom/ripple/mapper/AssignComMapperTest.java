package com.eastcom.ripple.mapper;

import com.eastcom.ripple.mapper.computer.ComEmpRecordMapper;
import com.eastcom.ripple.vo.AssignComVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Hongzh
 * @date 2020/09/22
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AssignComMapperTest {

    @Resource
    private ComEmpRecordMapper comEmpRecordMapper;

    @Test
    public void findAllAssignComRecordTest(){
        List<AssignComVO> assignComRecords = comEmpRecordMapper.findAllAssignComRecord();
        System.out.println(assignComRecords);
    }
}
