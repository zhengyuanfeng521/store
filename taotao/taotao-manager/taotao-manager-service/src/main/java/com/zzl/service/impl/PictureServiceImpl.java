package com.zzl.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Random;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.zzl.common.pojo.PictureResult;
import com.zzl.common.util.FtpUtil;
import com.zzl.common.util.IDUtils;
import com.zzl.service.PictureService;

@Service
public class PictureServiceImpl implements PictureService {
	public final static String TAG="PictureServiceImpl  ";
	
	@Value("${FILI_UPLOAD_PATH}")
	private String FILI_UPLOAD_PATH;
	@Value("${IMAGE_BASE_URL}")
	private String IMAGE_BASE_URL;
	
	@Value("${FTP_SERVER_IP}")
	private String FTP_SERVER_IP;
	@Value("${FTP_SERVER_PORT}")
	private Integer FTP_SERVER_PORT;
	@Value("${FTP_SERVER_USERNAME}")
	private String FTP_SERVER_USERNAME;
	@Value("${FTP_SERVER_PASSWORD}")
	private String FTP_SERVER_PASSWORD;
	
	@Override
	public PictureResult uploadFile(MultipartFile uploadFile){
		System.out.println(TAG+" is comming!!");
		String result = savePicture(uploadFile);
		return (result == null | result.trim().length() == 0)?new PictureResult(1, result, "图片上传失败"):new PictureResult(0, IMAGE_BASE_URL+result);
	}
	
	public String savePicture(MultipartFile uploadFile) {
		String result = null;
		try {
			if (uploadFile.isEmpty()) return null;
			System.out.println(TAG+" 不为空！！");
			// 取原始文件名
			String originalFilename = uploadFile.getOriginalFilename();
			// 新文件名
			String newFileName = IDUtils.genImageName() + originalFilename.substring(originalFilename.lastIndexOf("."));
			// 转存文件，上传到ftp服务器
			FtpUtil.uploadFile(FTP_SERVER_IP, FTP_SERVER_PORT, FTP_SERVER_USERNAME, FTP_SERVER_PASSWORD,
					FILI_UPLOAD_PATH, new SimpleDateFormat("/yyyy/MM/dd").format(new Date()), newFileName, uploadFile.getInputStream());
//			/2018/11/08/567.jpg
			result = new SimpleDateFormat("/yyyy/MM/dd").format(new Date()) + "/" + newFileName;
			System.out.println(TAG+result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	

	@Override
	public String TestM1() {
		System.out.println(TAG+" is comming!!");
		return "我能执行";
	}


}
