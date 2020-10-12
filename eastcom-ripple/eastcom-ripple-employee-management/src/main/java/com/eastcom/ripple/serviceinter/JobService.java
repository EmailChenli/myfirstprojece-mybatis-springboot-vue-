package com.eastcom.ripple.serviceinter;

import com.eastcom.ripple.entity.Department;
import com.eastcom.ripple.entity.Job;
import com.eastcom.ripple.util.JobInfo;
import com.eastcom.ripple.util.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface JobService {
	//根据Jobinfo获取所有职位信息
	List<JobInfo> findAll(Page page);

	JobInfo findById(Integer jobId);
	//根据对象更新
	int updateJob(JobInfo jobInfo);
	//根据关键字获取对象
	List<JobInfo> findJobLike(JobInfo jobInfo);
	//添加部门对象
	int addJob(JobInfo jobInfo);
	//根据id删除
	int deleteJobById(int jobId);
	//获取职位Id
	JobInfo findByName(String jobName,int departmentId);
}
