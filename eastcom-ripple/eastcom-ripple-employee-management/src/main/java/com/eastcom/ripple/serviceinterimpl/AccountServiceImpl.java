package com.eastcom.ripple.serviceinterimpl;

import com.eastcom.ripple.entity.Account;
import com.eastcom.ripple.mapper.AccountMapper;

import com.eastcom.ripple.serviceinter.AccountService;
import com.eastcom.ripple.util.Page;
import com.eastcom.ripple.util.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
	@Autowired
	private AccountMapper accountMapper;
	@Override
	public Account findById(Long accountId) {
		return accountMapper.findById(accountId);
	}

	@Override
	public List<Account> findAll(Page page) {
		return accountMapper.findAll(page);
	}

	@Override
	public int addAccount(Account account) {
		return accountMapper.addAccount(account);
	}

	@Override
	public int deleteAccountById(Long accountId) {
		return accountMapper.deleteAccountById(accountId);
	}

	@Override
	public int updateAccountById(Account account) {
		return accountMapper.updateAccountById(account);
	}

	@Override
	public List<Account> findAccountLike(Account account) {
		return accountMapper.findAccountLike(account);
	}

	@Override
	public ResultVO<Account> findByAccountName(String accountName) {
		try{
			Account acc = accountMapper.findByAccountName(accountName);
			ResultVO<Account> result = new ResultVO<>();
			result.setCode(200);
			result.setMsg("查询成功");
			result.setData(acc);
			return result;
		}catch(Exception e){
			ResultVO<Account> result = new ResultVO<>();
			result.setCode(444);
			result.setMsg("查询失败");
			return result;
		}
	}

}
