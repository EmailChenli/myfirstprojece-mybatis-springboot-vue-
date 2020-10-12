package com.eastcom.ripple.controller;

import com.eastcom.ripple.util.JobInfo;
import com.eastcom.ripple.util.Page;
import com.eastcom.ripple.util.ResultVO;
import com.eastcom.ripple.serviceinter.JobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("sys/job")
@Slf4j
public class JobController {
	@Autowired
	private JobService jobService;
	@PostMapping("findall")
	public ResultVO<List<JobInfo>> findJobAll(@RequestBody Page page){

		try {
			page.setCurrentPage(page.getCurrentPage()-1);
			log.info("All_job!"+page.toString());
			ResultVO<List<JobInfo>> result = new ResultVO<List<JobInfo>>();
			result.setCode(200);
			result.setMsg("获取成功");
			result.setData(jobService.findAll(page));
			return result;
		}catch(Exception e){
			ResultVO<List<JobInfo>> result = new ResultVO<List<JobInfo>>();
			result.setCode(444);
			result.setMsg("获取失败");
			return result;
		}
	} 
	
	@GetMapping("findbyid/{jobId}")
	public ResultVO<JobInfo> findJobByid(@PathVariable("jobId") Integer jobId){

		try {
			log.info("Job_ID:"+jobId);
			ResultVO<JobInfo> result = new ResultVO<JobInfo>();
			result.setCode(200);
			result.setMsg("获取成功");
			result.setData(jobService.findById(jobId));
			return result;
		}catch(Exception e){
			ResultVO<JobInfo> result = new ResultVO<JobInfo>();
			result.setCode(444);
			result.setMsg("获取失败");
			return result;
		}
	}
	@PostMapping("add")
	public ResultVO<Integer> jobAdd(@RequestBody JobInfo jobInfo){
		try {
			log.info("JOB_Add:"+jobInfo.getJobMsg());
			ResultVO<Integer> result = new ResultVO<Integer>();
			result.setCode(200);
			result.setMsg("添加成功");
			result.setData(jobService.addJob(jobInfo));
			return result;
		}catch(Exception e){
			ResultVO<Integer> result = new ResultVO<Integer>();
			result.setCode(444);
			result.setMsg("添加失败");
			return result;
		}
	}

	@PostMapping("update")
	public ResultVO<Integer> jobUpdate(@RequestBody JobInfo jobInfo){
		try {
			log.info("JOB_Update:"+jobInfo.getJobMsg());
			ResultVO<Integer> result = new ResultVO<Integer>();
			result.setCode(200);
			result.setMsg("更新成功");
			result.setData(jobService.updateJob(jobInfo));
			return result;
		}catch(Exception e){
			ResultVO<Integer> result = new ResultVO<Integer>();
			result.setCode(444);
			result.setMsg("更新失败");
			return result;
		}
	}


	@PostMapping("findlike")
	public ResultVO<List<JobInfo>> jobFindlike(@RequestBody JobInfo jobInfo){

		try {
			log.info("Department_findlike:"+jobInfo.getJobName());
			ResultVO<List<JobInfo>> result = new ResultVO<List<JobInfo>>();
			result.setCode(200);
			result.setMsg("查询成功");
			result.setData(jobService.findJobLike(jobInfo));
			return result;
		}catch(Exception e){
			ResultVO<List<JobInfo>> result = new ResultVO<List<JobInfo>>();
			result.setCode(444);
			result.setMsg("查询失败");
			return result;
		}
	}

	@GetMapping("delete")
	public ResultVO<Integer> jobFindlike(@RequestBody Integer jobId){
		try {
			log.info("Department_findlike:"+jobId);
			ResultVO<Integer> result = new ResultVO<Integer>();
			result.setCode(200);
			result.setMsg("删除成功");
			result.setData(jobService.deleteJobById(jobId));
			return result;
		}catch(Exception e){
			ResultVO<Integer> result = new ResultVO<Integer>();
			result.setCode(444);
			result.setMsg("删除失败");
			return result;
		}
	}
}
