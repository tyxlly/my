package cn.yx.ly.service;

import cn.yx.ly.pojo.Users;
import cn.yx.ly.util.BalanceLessException;
import cn.yx.ly.util.StoreHouseLessException;

public interface IUserService {

	public Users islogin(Users u);
	
	/**
	 * ¬Ú È
	 * @return
	 */
	public boolean buybook(Integer bookid,Integer accid)throws StoreHouseLessException, BalanceLessException;
	
}
