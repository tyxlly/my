package cn.yx.ly.dao;

import cn.yx.ly.pojo.Users;

public interface IUserDAO {

	public Users findUserByNameAndPwd(Users u);
	
	/**
	 * ���������һ
	 * @param bookid
	 */
	public void countplusone(Integer bookid);
	
	/**
	 * �����鼮��ţ���ѯ�������
	 * @param bookid
	 * @return
	 */
	public Integer findCountByBookid(Integer bookid);
}
