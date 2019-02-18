package com.jmhqmc.demo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/d!")
@Scope("request")
public class DriverController {
	@RequestMapping(value = "/init", method = { RequestMethod.GET,RequestMethod.POST })
	public ModelAndView init(HttpServletRequest request) throws Exception {
		return new ModelAndView("/demo/driver");
	}
}
