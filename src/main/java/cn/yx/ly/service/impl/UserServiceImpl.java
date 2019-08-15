package cn.yx.ly.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.yx.ly.dao.IAccountDAO;
import cn.yx.ly.dao.IBookDAO;
import cn.yx.ly.dao.IUserDAO;
import cn.yx.ly.pojo.Users;
import cn.yx.ly.service.IUserService;
import cn.yx.ly.util.BalanceLessException;
import cn.yx.ly.util.StoreHouseLessException;
@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private IUserDAO userDAOImpl;
	@Autowired
	private IAccountDAO accdao;
	@Autowired
	private IBookDAO bookdao;
	
	public Users islogin(Users u) {
		return userDAOImpl.findUserByNameAndPwd(u);
	}

	@Transactional(isolation=Isolation.READ_COMMITTED,propagation=Propagation.REQUIRED,rollbackFor={StoreHouseLessException.class,BalanceLessException.class},readOnly=false,timeout=20)
	public boolean buybook(Integer bookid,Integer accid) throws StoreHouseLessException, BalanceLessException {
		//1.�������Ҫ��һ
		//��Ҫ�ѿ�������õ�
		int count = userDAOImpl.findCountByBookid(bookid);
		if(count<=0){
			throw new StoreHouseLessException("�����������");
		}
		userDAOImpl.countplusone(bookid);
		//2.�˻����Ҫ����
		Map<String,Object> map = new HashMap<String,Object>();
		Double balance = accdao.findBalanceByAccid(accid);//�õ��˻����е����
		Double price = bookdao.findPriceByBookid(bookid);//�����鼮��ţ���ѯ�鼮�۸�
		if(balance - price<0){
			throw new BalanceLessException("�˻�����");
		}
		Double newmoney = balance - price;
		map.put("newmoney", newmoney);
		map.put("accid", accid);
		accdao.changeBalance(map);
		return true;
	}

}
