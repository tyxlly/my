package cn.yx.ly.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class FirstPageController {

	@RequestMapping(value="/")
	public String showfirstpage(){
		
		return "redirect:/bc/findall";
	}
}
