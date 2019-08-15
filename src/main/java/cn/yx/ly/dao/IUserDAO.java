package cn.yx.ly.dao;

import cn.yx.ly.pojo.Users;

public interface IUserDAO {

	public Users findUserByNameAndPwd(Users u);
	
	/**
	 * 库存数量减一
	 * @param bookid
	 */
	public void countplusone(Integer bookid);
	
	/**
	 * 根据书籍编号，查询库存数量
	 * @param bookid
	 * @return
	 */
	public Integer findCountByBookid(Integer bookid);
}
