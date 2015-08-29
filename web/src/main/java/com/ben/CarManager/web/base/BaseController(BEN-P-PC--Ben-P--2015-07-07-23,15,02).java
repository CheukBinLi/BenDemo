package com.ben.CarManager.web.base;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;

import com.ben.CarManager.annotation.Permissions;
import com.ben.CarManager.core.entity.*;

@Controller
@RequestMapping({ "/*", "/*/**", "/", "" })
@Scope("prototype")
public class BaseController {

	@Permissions(code = "123123")
	public a A;

	public BaseController() {
		System.out.println(this.getClass().getName());
		try {
			ClassLoader cl = Thread.currentThread().getContextClassLoader();
			System.out.println(cl.getResource(""));
			Enumeration<URL> us;
			us = cl.getResources("com/ben/");
			while (us.hasMoreElements())
				System.out.println(us.nextElement().toString());
			us = cl.getSystemResources("com/ben/");
			while (us.hasMoreElements())
				System.out.println(us.nextElement().toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping("{path}")
	public ModelAndView basePath(HttpServletRequest request, HttpServletResponse response, @PathVariable("path") String path) {
		return new ModelAndView(path, request.getParameterMap());
	}

	// @RequestMapping("index")
	// public ModelAndView index(HttpServletRequest request, HttpServletResponse response, @PathVariable("path") String path) {
	// return new ModelAndView("/index");
	// }
}
