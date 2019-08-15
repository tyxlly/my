package cn.yx.ly.service;

import java.util.List;

import cn.yx.ly.pojo.Account;

public interface IAccountService {

	public List<Account> findAccByUserid(Integer userid);
	
	public Double findBalanceByAccid(Integer accid);
}
