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
	 * 页面上,有日期要接收的时候:
	 * 1.直接通过注解@DateTimeFormat来接收
	 * 2.在实体类中,添加注解@DateTimeFormat(pattern="yyyy-MM-dd")
	 * 3.自定义类型转换器
	 * 
	 * 文件上传:
	 * 1.添加commons-fileupload依赖,commons-io依赖
	 * 2.在app.xml文件中,配置文件上传操作对象;
	 * 		上传操作对象的id,必须叫做multipartResolver
	 * 		在该对象中,可以设定上传文件的大小,编码方式等
	 * 3.页面上:
	 * 		表单元素,准备file组件,而且该标签必须添加name属性;
	 * 				表单(form)必须添加enctype="multipart/form-data"属性
	 * 4.在控制器中,通过@RequestParam MultipartFile pic方式,来接收页面传递的文件;
	 * 		此时页面上的文件组件的name,不能与实体类属性名一致,实体类的属性,使用来保存文件的路径和名字的;
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
		System.out.println("接收到日期："+book.getPublicDate());
		System.out.println("接收book信息："+book.getBookName());
		System.out.println("文件："+pic.getOriginalFilename());
		
		MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
		MultipartFile p = req.getFile("pic");
		String name = p.getName();
		System.out.println("name:"+name);
		
		try {
			InputStream is = pic.getInputStream();
			//获取bookimg文件夹的绝对路径
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
