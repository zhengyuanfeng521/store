package com.zzl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.zzl.common.pojo.PictureResult;
import com.zzl.service.PictureService;
import com.zzl.service.TestService;


@Controller
public class PictureController {
	
	@Autowired
	private PictureService pictureService;
	
	@Autowired
	private TestService testService;

	@RequestMapping("/pic/upload")
	@ResponseBody
	public PictureResult upload(MultipartFile uploadFile) throws Exception {
		System.out.println("HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH");
		System.out.println("pictureService   ====   "+pictureService.toString());
//		PictureResult pictureResult = pictureService.uploadFile(uploadFile);
//		new PictureResult(0,"http://www.zzlhs.club/testFtp1.jpg");
		System.out.println("successful !!");
		return new PictureResult(0,"http://www.zzlhs.club/123.jpg");
	} 
}
