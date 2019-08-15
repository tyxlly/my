package cn.yx.ly.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.yx.ly.dao.IBookDAO;
import cn.yx.ly.pojo.Book;
import cn.yx.ly.service.IBookService;

@Service
public class BookServiceImpl implements IBookService {

	@Autowired
	private IBookDAO bookdao;
	
	public List<Book> findall() {
		return bookdao.findallbook();
	}

	public Book findBookById(Integer bookid) {
		if(bookid==null){
			return null;
		}
		return bookdao.findBookById(bookid);
	}

}
