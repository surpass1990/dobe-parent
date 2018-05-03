package com.dobe.web.back.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/")
public class UserController {

	@RequestMapping("toLogin")
	public String toLogin(ModelMap mm) {
		mm.addAttribute("host", "http://blog.csdn.net/hry2015/article/");
		return "user/login";
	}
}
