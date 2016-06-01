package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import service.IUserService;

import javax.servlet.http.HttpServletRequest;

/**
 * Created  on 2016/6/1.
 */
@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private IUserService userService;

	@RequestMapping("list")
	public String List(HttpServletRequest request){
		request.setAttribute("list",userService.findAll());
		return "list";
	}

}
