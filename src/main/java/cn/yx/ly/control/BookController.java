package cn.yx.ly.control;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import cn.yx.ly.pojo.Book;
import cn.yx.ly.service.IBookService;



@Controller
@RequestMapping("/bc")
public class BookController {

	@Autowired
	private IBookService bookService;
	
	@RequestMapping(value="findall",method=RequestMethod.GET)
	public String showallbook(Model model){
		
		List<Book>  booklist = bookService.findall();
		model.addAttribute("booklist", booklist);
		return "book_list";
	}
	
	@RequestMapping(value="findbyid",method=RequestMethod.GET)
	public String findBookById(Integer bookid,Model model){
		
		Book book = bookService.findBookById(bookid);
		model.addAttribute("book", book);
		return "book_info";
	}
	
	/**
	 * ҳ����,������Ҫ���յ�ʱ��:
	 * 1.ֱ��ͨ��ע��@DateTimeFormat������
	 * 2.��ʵ������,���ע��@DateTimeFormat(pattern="yyyy-MM-dd")
	 * 3.�Զ�������ת����
	 * 
	 * �ļ��ϴ�:
	 * 1.���commons-fileupload����,commons-io����
	 * 2.��app.xml�ļ���,�����ļ��ϴ���������;
	 * 		�ϴ����������id,�������multipartResolver
	 * 		�ڸö�����,�����趨�ϴ��ļ��Ĵ�С,���뷽ʽ��
	 * 3.ҳ����:
	 * 		��Ԫ��,׼��file���,���Ҹñ�ǩ�������name����;
	 * 				��(form)�������enctype="multipart/form-data"����
	 * 4.�ڿ�������,ͨ��@RequestParam MultipartFile pic��ʽ,������ҳ�洫�ݵ��ļ�;
	 * 		��ʱҳ���ϵ��ļ������name,������ʵ����������һ��,ʵ���������,ʹ���������ļ���·�������ֵ�;
	 * 
	 * 
	 * 
	 * 
	 * @param book
	 * @param mydate
	 * @return
	 */
	@RequestMapping(value="addbook",method=RequestMethod.POST)
	//public String addbook(Book book,@DateTimeFormat(pattern="yyyy-MM-dd")Date mydate){
	public String addbook(Book book,@RequestParam MultipartFile pic,HttpServletRequest request){
		System.out.println("addbook...");
		System.out.println("���յ����ڣ�"+book.getPublicDate());
		System.out.println("����book��Ϣ��"+book.getBookName());
		System.out.println("�ļ���"+pic.getOriginalFilename());
		
		MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
		MultipartFile p = req.getFile("pic");
		String name = p.getName();
		System.out.println("name:"+name);
		
		try {
			InputStream is = pic.getInputStream();
			//��ȡbookimg�ļ��еľ���·��
			String realpath = request.getSession().getServletContext().getRealPath("/bookimg");
			
			String newfilename = UUID.randomUUID().toString();
			String endname = pic.getOriginalFilename().substring(pic.getOriginalFilename().lastIndexOf("."));
			OutputStream os = new FileOutputStream(new File(realpath+"/"+newfilename+endname));			
			
			FileCopyUtils.copy(is, os);
			
			book.setImgPath(newfilename+endname);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return "redirect:/bc/findall";
	}
	
	
}
