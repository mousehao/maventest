package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import service.IOrderService;
import service.IUserService;

import javax.servlet.http.HttpServletRequest;

/**
 * Created  on 2016/6/1.
 */
@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private IOrderService orderService;

	@RequestMapping("list")
	public String List(HttpServletRequest request){
		request.setAttribute("list",orderService.findAll());
		return "list";
	}

}
