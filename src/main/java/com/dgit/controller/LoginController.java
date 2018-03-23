package com.dgit.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dgit.domain.MemberVO;
import com.dgit.service.MemberService;

@Controller
@RequestMapping("/login")
public class LoginController {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	@Autowired private MemberService service;
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String login(){
		return "login";
	}
	
	@RequestMapping(value="/", method=RequestMethod.POST)
	public String loginPost(HttpServletRequest req, String id, String pw){
		logger.info("[LOGIN CONTROLLER] USER LOGIN = POST");
		
		// SESSION 유저정보 확인
		HttpSession session = req.getSession();
		Object object = session.getAttribute("login");
		
		if(object == null){
			MemberVO vo = service.selectById(id);
			String targetPw = vo.getPw();
			if(targetPw.equals(pw)){
				logger.info("[LOGIN CONTROLLER] USER LOGIN = SUCCESS");
				vo.setPw("");
				session.setAttribute("login", vo);
			}else{
				logger.info("[LOGIN CONTROLLER] USER LOGIN = FAIL");
				session.invalidate();
				return "";
			}
		}
		
		return "list";
	}
	
	@RequestMapping(value="/join", method=RequestMethod.GET)
	public String join(){
		return "join";
	}
	
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String joinPost(MemberVO vo){
		logger.info("[LOGIN CONTROLLER] USER JOIN = POST");
		service.insertMember(vo);
		
		return "login";
	}
	
	@ResponseBody
	@RequestMapping(value="idCheck", method=RequestMethod.GET)
	public ResponseEntity<String> idCheck(String id){
		logger.info("[LOGIN CONTROLLER] ID CHECKER FIND TARGET : " +id);
		ResponseEntity<String> entity = null;
		
		MemberVO vo = service.selectById(id);
		if(vo != null){
			entity = new ResponseEntity<String>("true", HttpStatus.OK);			
		}else{
			entity = new ResponseEntity<String>("false", HttpStatus.OK);
		}
		
		return entity; 
	}
}