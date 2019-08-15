package cn.yx.ly.dao;

import java.util.List;

import cn.yx.ly.pojo.Book;



public interface IBookDAO {

	public List<Book> findallbook();
	
	public Book findBookById(Integer bookid);
	/**
	 * 根据书籍编号，查询书籍价格
	 * @param bookid
	 * @return
	 */
	public Double findPriceByBookid(Integer bookid);
}
