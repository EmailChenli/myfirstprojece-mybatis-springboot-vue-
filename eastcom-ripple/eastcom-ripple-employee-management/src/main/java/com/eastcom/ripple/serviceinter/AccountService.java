package com.eastcom.ripple.serviceinter;

import com.eastcom.ripple.entity.Account;
import com.eastcom.ripple.util.Page;
import com.eastcom.ripple.util.ResultVO;

import java.util.List;

public interface AccountService {
	Account findById(Long accountId);
	List<Account> findAll(Page page);
	//添加部门
	int addAccount(Account account);
	//根据Id删除
	int deleteAccountById(Long accountId);
	//根据对象id更新
	int updateAccountById(Account account);
	//根据关键字查询
	List<Account> findAccountLike(Account account);

    ResultVO<Account> findByAccountName(String accountName);
}
