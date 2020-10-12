package com.eastcom.ripple.controller;

import com.eastcom.ripple.entity.Account;
import com.eastcom.ripple.util.BCrypt;
import com.eastcom.ripple.util.Page;
import com.eastcom.ripple.util.ResultVO;
import com.eastcom.ripple.serviceinter.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("sys/account")
@Slf4j
public class AccountController {
	@Autowired
	private AccountService accountService;

	@PostMapping("findall")
	public ResultVO<List<Account>> findAccountAll(@RequestBody Page page) {
        log.info("All_Account!"+page.toString());
	    try {
			ResultVO<List<Account>> result = new ResultVO<List<Account>>();
            page.setCurrentPage(page.getCurrentPage()-1);
			result.setCode(200);
			result.setMsg("获取成功");
			result.setData(accountService.findAll(page));
			return result;
		} catch (Exception e) {
			ResultVO<List<Account>> result = new ResultVO<List<Account>>();
			result.setCode(444);
			result.setMsg("获取失败");
			return result;
		}
	}

	@GetMapping("findbyid/{accountId}")
	public ResultVO<Account> findAccountById(@PathVariable("accountId") Long accountId) {
		log.info("Account_ID:" + accountId);
		try {
			ResultVO<Account> result = new ResultVO<Account>();
			result.setCode(200);
			result.setMsg("获取成功");
			result.setData(accountService.findById(accountId));
			return result;
		} catch (Exception e) {
			ResultVO<Account> result = new ResultVO<Account>();
			result.setCode(444);
			result.setMsg("获取失败");
			return result;
		}
	}
	@GetMapping("delete/{accountId}")
	public ResultVO<Integer> deleteAccountById(@PathVariable("accountId") Long accountId) {
		try {
			log.info("Account_ID:" + accountId);
			ResultVO<Integer> result = new ResultVO<Integer>();
			result.setCode(200);
			result.setMsg("获取成功");
			result.setData(accountService.deleteAccountById(accountId));
			return result;
		} catch (Exception e) {
			ResultVO<Integer> result = new ResultVO<Integer>();
			result.setCode(444);
			result.setMsg("获取失败");
			return result;
		}
	}

	@PostMapping("update")
	public ResultVO<Integer> AccountUpdate(@RequestBody Account account) {

		try {
			log.info("Department_Update:" + accountService.updateAccountById(account));
			ResultVO<Integer> result = new ResultVO<Integer>();
            account.setAccountPassword(BCrypt.hashpw(account.getAccountPassword(),BCrypt.gensalt(12)));
			result.setCode(200);
			result.setMsg("更新成功");
			result.setData(accountService.updateAccountById(account));
			return result;
		} catch (Exception e) {
			ResultVO<Integer> result = new ResultVO<Integer>();
			result.setCode(444);
			result.setMsg("更新失败");
			return result;
		}
	}

	@PostMapping("findlike")
	public ResultVO<List<Account>> AccountFindlike(@RequestBody Account account){
		try {
			log.info("Department_findlike:"+account.getAccountId());
			ResultVO<List<Account>> result = new ResultVO<List<Account>>();
			result.setCode(200);
			result.setMsg("查询成功");
			result.setData(accountService.findAccountLike(account));
			return result;
		}catch(Exception e){
			ResultVO<List<Account>> result = new ResultVO<List<Account>>();
			result.setCode(444);
			result.setMsg("查询失败");
			return result;
		}
	}


	@PostMapping("add")
	public ResultVO<Integer> accountAdd(@RequestBody Account account){
		try {
			log.info("Account_add"+account.getAccountName());
			ResultVO<Integer> result = new ResultVO<Integer>();
            account.setAccountPassword(BCrypt.hashpw(account.getAccountPassword(),BCrypt.gensalt(12)));
            result.setCode(200);
			result.setMsg("添加成功");
			result.setData(accountService.addAccount(account));
			return result;
		}catch(Exception e){
			ResultVO<Integer> result = new ResultVO<Integer>();
			result.setCode(444);
			result.setMsg("添加失败");
			return result;
		}
	}

	@PostMapping("/findByAccountName/{accountName}")
	public ResultVO<Account> findByAccountName(@PathVariable("accountName") String accountName){
		log.info("查询employeeId、employeeName，账号：" + accountName);
		return accountService.findByAccountName(accountName);
	}

}

