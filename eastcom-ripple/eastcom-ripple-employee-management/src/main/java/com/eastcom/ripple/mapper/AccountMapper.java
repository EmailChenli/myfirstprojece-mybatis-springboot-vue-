package com.eastcom.ripple.mapper;

import com.eastcom.ripple.entity.Account;
import com.eastcom.ripple.util.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AccountMapper {
	//发现所有账号
	List<Account> findAll(@Param("page") Page page);
	//根据ID发现
	Account findById(@Param("accountId") Long accountId);
	//添加账号
	int addAccount(@Param("account") Account account);
	//根据Id删除
	int deleteAccountById(@Param("accountId") Long accountId);
	//根据对象id更新
	int updateAccountById(@Param("account") Account account);
	//根据关键字查询
	List<Account> findAccountLike(@Param("account") Account account);

    Account findByAccountName(@Param("accountName") String accountName);
}
