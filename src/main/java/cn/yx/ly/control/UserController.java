package cn.yx.ly.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.yx.ly.pojo.Account;
import cn.yx.ly.pojo.Book;
import cn.yx.ly.pojo.Users;
import cn.yx.ly.service.IAccountService;
import cn.yx.ly.service.IBookService;
import cn.yx.ly.service.IUserService;
import cn.yx.ly.util.BalanceLessException;
import cn.yx.ly.util.StoreHouseLessException;

@Controller
@RequestMapping("/uc")
public class UserController {

	@Autowired
	private IUserService userServiceImpl;
	@Autowired
	private IAccountService accService;
	@Autowired
	private IBookService bookService;
	
	
	
	@RequestMapping(value="login")
	@ResponseBody
	public Users islogin(String username,String password ,HttpServletRequest request){
		
		Users u=new Users();
				u.setLoginname(username);
		u.setLoginpwd(password);
		  return userServiceImpl.islogin(u);
/*		Users users = userServiceImpl.islogin(u);
		if(users!=null){
			request.getSession().setAttribute("myusers", users);
			return "redirect:/bc/findall";
		}
		return "login";*/
		
		
	}
	
	@RequestMapping(value="findaccount")
	public String findAccountByUser(HttpServletRequest request,Model model,Integer bookid){
		Users u = (Users) request.getSession().getAttribute("myusers");
		System.out.println(u);
		Book book = bookService.findBookById(bookid);
		model.addAttribute("book", book);
		return "book_prebuy";
	}
	
	@ResponseBody
	@RequestMapping(value="fa",method=RequestMethod.POST)
	public List<Account> findAccount(HttpServletRequest request){
		Users u = (Users) request.getSession().getAttribute("myusers");
		List<Account> acclist = accService.findAccByUserid(u.getUserid());
		return acclist;
	}
	
	@RequestMapping(value="findbalance",method=RequestMethod.POST)
	public void findBalanceByAccid(Integer accid,HttpServletResponse response) throws IOException{
		Double balance = accService.findBalanceByAccid(accid);
		PrintWriter out = response.getWriter();
		out.print(balance);
		out.flush();
		out.close();
	}
	
	@RequestMapping(value="buybook",method=RequestMethod.POST)
	public String buybook(Integer bookid,Integer accid){
		try {
			boolean flag = userServiceImpl.buybook(bookid,accid);
			System.out.println(flag);
		} catch (StoreHouseLessException e) {
			e.printStackTrace();
		} catch (BalanceLessException e) {
			e.printStackTrace();
		}
		return "redirect:/bc/findall";
	}
	
}
