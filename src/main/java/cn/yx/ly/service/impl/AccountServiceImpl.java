package cn.yx.ly.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.yx.ly.dao.IAccountDAO;
import cn.yx.ly.pojo.Account;
import cn.yx.ly.service.IAccountService;

@Service
public class AccountServiceImpl implements IAccountService {

	@Autowired
	private IAccountDAO accdao;
	
	public List<Account> findAccByUserid(Integer userid) {
		if(userid==null){
			return null;
		}
		return accdao.findAccByUserid(userid);
	}

	public Double findBalanceByAccid(Integer accid) {
		return accdao.findBalanceByAccid(accid);
	}

}
