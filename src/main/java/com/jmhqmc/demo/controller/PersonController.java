package com.jmhqmc.demo.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


import com.jmhqmc.demo.Constants;
import com.jmhqmc.demo.domain.Person;
import com.jmhqmc.demo.service.PersonService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/mongo!")
@Scope("request")
public class PersonController extends BaseController{
	@Resource
	PersonService personService;
	
	@RequestMapping(value = "/init", method = { RequestMethod.GET,RequestMethod.POST })
	public ModelAndView init(HttpServletRequest request) throws Exception {
		return new ModelAndView("/demo/mongodb");
	} 
	
	@RequestMapping(value = "/findAll", method = { RequestMethod.GET,RequestMethod.POST })
	public void findAll(@ModelAttribute Person person,HttpServletRequest request,HttpServletResponse response) throws Exception {
		JSONObject datas = personService.findPerson(person, "test");

		if (datas != null) {
			response.setCharacterEncoding(Constants.ENCODING_UTF8);
			response.getWriter().print(datas.toString());
		}
	}
	
	@RequestMapping(value = "/addUser", method = { RequestMethod.POST })
	public void addUser(@ModelAttribute Person person,HttpServletRequest request,HttpServletResponse response) throws Exception {
		personService.insert(person, "test");

		response.setCharacterEncoding(Constants.ENCODING_UTF8);
		response.getWriter().print(JSONObject.fromObject(getResult()));
	}
	
	@RequestMapping(value = "/deleteUser", method = { RequestMethod.POST })
	public void deleteUser(@ModelAttribute Person person,HttpServletRequest request,HttpServletResponse response) throws Exception {
		personService.remove(person.getId(), "test");

		response.setCharacterEncoding(Constants.ENCODING_UTF8);
		response.getWriter().print(JSONObject.fromObject(getResult()));
	}
	
	@RequestMapping(value = "/updateUser", method = { RequestMethod.POST })
	public void updateUser(@ModelAttribute Person person,HttpServletRequest request,HttpServletResponse response) throws Exception {
		personService.update(person,"test");

		response.setCharacterEncoding(Constants.ENCODING_UTF8);
		response.getWriter().print(JSONObject.fromObject(getResult()));
	}
}
