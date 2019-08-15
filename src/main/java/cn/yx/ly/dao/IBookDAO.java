package cn.yx.ly.dao;

import java.util.List;

import cn.yx.ly.pojo.Book;



public interface IBookDAO {

	public List<Book> findallbook();
	
	public Book findBookById(Integer bookid);
	/**
	 * �����鼮��ţ���ѯ�鼮�۸�
	 * @param bookid
	 * @return
	 */
	public Double findPriceByBookid(Integer bookid);
}
