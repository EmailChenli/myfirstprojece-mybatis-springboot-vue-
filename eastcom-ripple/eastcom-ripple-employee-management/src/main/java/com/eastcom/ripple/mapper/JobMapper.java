package com.eastcom.ripple.mapper;

import com.eastcom.ripple.entity.Job;
import com.eastcom.ripple.util.JobInfo;
import com.eastcom.ripple.util.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface JobMapper {
	//根据Jobinfo获取所有职位信息
	List<JobInfo> findAll(@Param("page")Page page);
	//获取职位Id
	JobInfo findByName(@Param("jobName") String jobName,@Param("departmentId") int departmentId);
	//获取职位信息
	JobInfo findById(@Param("jobId") Integer jobId);
	//根据对象更新
	int updateJob(@Param("jobInfo") JobInfo jobInfo);
	//根据关键字获取对象
	List<JobInfo> findJobLike(@Param("jobInfo")JobInfo jobInfo);
	//添加部门对象
	int addJob(@Param("jobInfo")JobInfo jobInfo);
	//根据id删除
	int deleteJobById(@Param("jobId") int jobId);
}
