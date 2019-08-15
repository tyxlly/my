package cn.yx.ly.dao;

import java.util.List;
import java.util.Map;

import cn.yx.ly.pojo.Account;



public interface IAccountDAO {

	public List<Account> findAccByUserid(Integer userid);
	/**
	 * 根据账户编号，查询账户余额
	 * @param accid
	 * @return
	 */
	public Double findBalanceByAccid(Integer accid);
	
	/**
	 * 账户余额减少
	 * @param map
	 */
	public void changeBalance(Map<String,Object> map);
}
