package com.dgit.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dgit.domain.MemberVO;
import com.dgit.domain.PictureVO;
import com.dgit.service.MemberService;
import com.dgit.service.PictureService;
import com.dgit.util.MediaUtils;
import com.dgit.util.UploadFileUtils;

@Controller
@RequestMapping("/login")
public class LoginController {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	@Autowired private MemberService mService;
	@Autowired private PictureService pService;
	@Resource(name="uploadPath") private String outUploadPath;
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String login(){
		return "login";
	}
	
	@RequestMapping(value="/", method=RequestMethod.POST)
	public String loginPost(HttpServletRequest req, String id, String pw, Model model){
		logger.info("[LOGIN CONTROLLER] USER LOGIN = POST");
		
		// SESSION 유저정보 확인
		HttpSession session = req.getSession();
		Object object = session.getAttribute("login");
		
		if(object == null){
			MemberVO vo = mService.selectById(id);
			String targetPw = vo.getPw();
			if(targetPw.equals(pw)){
				logger.info("[LOGIN CONTROLLER] USER LOGIN = SUCCESS");
				vo.setPw("");
				session.setAttribute("login", vo);
				List<PictureVO> result = pService.selectById(vo.getId());
				
				model.addAttribute("lists", result);
			}else{
				logger.info("[LOGIN CONTROLLER] USER LOGIN = FAIL");
				session.invalidate();
				return "";
			}
		}
		
		return "list";
	}
	
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String logout(HttpServletRequest req){
		logger.info("[LOGIN CONTROLLER] USER LOGOUT");
		
		// SESSION 유저정보 확인
		HttpSession session = req.getSession();
		Object object = session.getAttribute("login");
		
		if(object != null){
			logger.info("[LOGIN CONTROLLER] LOGOUT SUCCESS");
			session.invalidate();
			return "redirect:/login/";
		}
		
		return "redirect:/login/";
	}
	
	@RequestMapping(value="/join", method=RequestMethod.GET)
	public String join(){
		return "join";
	}
	
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String joinPost(MemberVO vo){
		logger.info("[LOGIN CONTROLLER] USER JOIN = POST");
		mService.insertMember(vo);
		
		return "redirect:/login/";
	}
	
	@ResponseBody
	@RequestMapping(value="idCheck", method=RequestMethod.GET)
	public ResponseEntity<String> idCheck(String id){
		logger.info("[LOGIN CONTROLLER] ID CHECKER FIND TARGET : " +id);
		ResponseEntity<String> entity = null;
		
		MemberVO vo = mService.selectById(id);
		if(vo != null){
			entity = new ResponseEntity<String>("true", HttpStatus.OK);			
		}else{
			entity = new ResponseEntity<String>("false", HttpStatus.OK);
		}
		
		return entity; 
	}
	
	@RequestMapping(value="/upload", method=RequestMethod.POST)
	public String registerPOST(String id, List<MultipartFile> imageFiles, RedirectAttributes rttr){
		logger.info("[LOGIN CONTROLLER] UPLOAD POST CALL"); 
		logger.info("[LOGIN CONTROLLER] IMAGE FILES LENGTH : " + imageFiles.size());
  
		MemberVO vo = mService.selectById(id);
		PictureVO tempVO = new PictureVO();
		tempVO.setId(vo.getId());
		
		if(imageFiles!=null && imageFiles.size() > 0){
			for(int i=0;i<imageFiles.size();i++){
				String savedName;
				try {
					savedName = UploadFileUtils.uploadFile(outUploadPath,imageFiles.get(i).getOriginalFilename(),imageFiles.get(i).getBytes());
					tempVO.setFullname(savedName);
					pService.upload(tempVO);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
		}
		
		List<PictureVO> lists = pService.selectById(id);
		rttr.addAttribute("lists", lists);
  
		return "redirect:/login/list";
	}
	
	@ResponseBody
	@RequestMapping(value="displayFile", method=RequestMethod.GET)
	public ResponseEntity<byte[]> displayFile(String filename){
		ResponseEntity<byte[]> entity = null;
		logger.info("[LOGIN CONTROLLER] DISPLAY FILE CALL : "+filename);
		
		InputStream in = null;
		
		try {
			// IMAGE TYPE을 설정해서 헤더로 작성하게해주는 코드
			String formatName = filename.substring(filename.lastIndexOf(".")+1);
			MediaType type = MediaUtils.getMediaType(formatName);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(type);
			
			in = new FileInputStream(outUploadPath + filename);
						
			entity = new ResponseEntity<>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}	
		
		return entity;
	}
	
	@ResponseBody
	@RequestMapping(value="deleteFile", method=RequestMethod.GET)
	public ResponseEntity<String> deleteFile(String filename){
		ResponseEntity<String> entity = null;
		logger.info("[LOGIN CONTROLLER] DELETE FILE CALL : " + filename);
		
		try {
			System.gc();
			File file = new File(outUploadPath + filename);
			file.delete();
			System.gc();
			String targetPath = filename.substring(0,12);
			String target = filename.substring(14);
			File targetfile = new File(outUploadPath + targetPath + target);
			targetfile.delete();
			
			pService.delete(filename);
			
			entity = new ResponseEntity<>("DELETE SUCCESS", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>("FAIL", HttpStatus.BAD_REQUEST);
		}	
		
		return entity;
	}
}