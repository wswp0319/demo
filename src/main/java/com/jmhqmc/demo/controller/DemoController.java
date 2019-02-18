package com.jmhqmc.demo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/m!")
@Scope("request")
public class DemoController extends BaseController {
	@RequestMapping(value = "/main", method = { RequestMethod.GET,RequestMethod.POST })
	public ModelAndView main(HttpServletRequest request) throws Exception {
		return new ModelAndView("/main");
	}

	public static void main(String[] args) {
		List list = new ArrayList();
		System.out.println(list.size());
	}
}
