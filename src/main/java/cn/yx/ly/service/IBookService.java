package cn.yx.ly.service;

import java.util.List;

import cn.yx.ly.pojo.Book;

public interface IBookService {

	public List<Book> findall();
	
	public Book findBookById(Integer bookid);
}
