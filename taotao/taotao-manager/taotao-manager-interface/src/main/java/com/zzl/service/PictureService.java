package com.zzl.service;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

import com.zzl.common.pojo.PictureResult;

public interface PictureService {

	
	
	public PictureResult uploadFile(MultipartFile uploadFile);
	
	String TestM1();
	
}
