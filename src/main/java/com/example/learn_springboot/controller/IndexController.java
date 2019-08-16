package com.example.learn_springboot.controller;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.example.learn_springboot.service.ShareService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Handles requests for the application home page.
 */
@Controller
public class IndexController {

	private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

	@Autowired
	private ShareService service;

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		List<Object> resultList = new ArrayList<Object>();

		Map<String, Object> data01 = new HashMap<String, Object>();

		data01.put("MEMBER_ID", "103029301202");
		data01.put("NAME", "name 01");
		data01.put("CELLPHONE", "001-2930-2938");		
		data01.put("EMAIL", "Anders@gmail.com");		
		data01.put("COMMON_CODE_ID", "490293232849");
		data01.put("DESCRIPTION", "anything DESCRIPTION 01");
		data01.put("ORGANIZATION_SEQ", "UUID-00482983293");
		data01.put("TELEPHONE", "031-2930-3920");
		data01.put("ORDER_NUMBER", 1);
		resultList.add(data01);

		return "index";
	}

	@RequestMapping(value = "/member/{action}", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView actionMethod(@RequestParam Map<String, Object> paramMap, @PathVariable String action,
			ModelAndView modelandView) {

		String viewName = "/member/" + action ;

		Map<String, Object> resultMap = new HashMap<String, Object>() ;
		List<Object> resultList = new ArrayList<Object>();

		// divided depending on action value
		if ("edit".equalsIgnoreCase(action)) {
		} else if ("update".equalsIgnoreCase(action)) {
			resultMap = (Map) service.getObject(viewName, paramMap);
			paramMap.put("action", action);
		} else if ("merge".equalsIgnoreCase(action)) {
			resultMap = (Map) service.saveObject(viewName, paramMap);
		} else if ("read".equalsIgnoreCase(action)) {
			resultMap = (Map) service.getObject(viewName, paramMap);
		} else if ("list".equalsIgnoreCase(action)) {
			resultList = (List) service.getList(viewName, paramMap);
		} else if ("delete".equalsIgnoreCase(action)) {
			resultList = (List) service.deleteObject(viewName, paramMap);
		} 
		
		modelandView.setViewName(viewName);

		modelandView.addObject("paramMap", paramMap);
		modelandView.addObject("resultMap", resultMap);
		modelandView.addObject("resultList", resultList);
		return modelandView;
	}
}
