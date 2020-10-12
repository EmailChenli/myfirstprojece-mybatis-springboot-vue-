package com.eastcom.ripple.serviceinterimpl;

import com.eastcom.ripple.entity.Job;
import com.eastcom.ripple.mapper.JobMapper;
import com.eastcom.ripple.serviceinter.JobService;
import com.eastcom.ripple.util.JobInfo;
import com.eastcom.ripple.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobServiceImpl implements JobService {
	@Autowired
	private JobMapper jobMapper;

	@Override
	public List<JobInfo> findAll(Page page) {
		return jobMapper.findAll(page);
	}

	@Override
	public JobInfo findById(Integer jobId) {
		return jobMapper.findById(jobId);
	}

	@Override
	public int updateJob(JobInfo jobInfo) {
		return jobMapper.updateJob(jobInfo);
	}

	@Override
	public List<JobInfo> findJobLike(JobInfo jobInfo) {
		return jobMapper.findJobLike(jobInfo);
	}

	@Override
	public int addJob(JobInfo jobInfo) {
		return jobMapper.addJob(jobInfo);
	}

	@Override
	public int deleteJobById(int jobId) {
		return jobMapper.deleteJobById(jobId);
	}

	@Override
	public JobInfo findByName(String jobName,int departmentId) {
		return jobMapper.findByName(jobName,departmentId);
	}
}
