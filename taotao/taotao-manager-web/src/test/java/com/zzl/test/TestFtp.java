package com.zzl.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;

import com.zzl.common.util.FtpUtil;

public class TestFtp {
	
	@Test
	public void mm1(){
		
		String sign = DigestUtils.md5Hex("59ca7c6488b4d805good2JLJQr2iC8ZTtb6QwZ7cSzfcnq4H1uaHa");
		System.out.println(sign);
	}
	
	@Test
	public void m1() {
		System.out.println("123");
		try {
			FTPClient ftpClient = new FTPClient();
			ftpClient.connect("118.24.194.103");
			ftpClient.login("frpadmin", "123");
			FileInputStream inputStream = new FileInputStream(new File("C:\\Users\\HP\\Desktop\\图标\\bg.jpg"));
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			ftpClient.changeWorkingDirectory("/home/frpadmin/www");
			ftpClient.storeFile("zyf.jpg", inputStream);
			inputStream.close();
			ftpClient.logout();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("finish");
	}
	
	@Test
	public void m2() throws FileNotFoundException{
//		FileInputStream inputStream = new FileInputStream(new File("C:\\Users\\HP\\Desktop\\图标\\bg.jpg"));
//		FtpUtil.uploadFile("118.24.194.103", 21, "ftpuser", "1234", "/images",new SimpleDateFormat("/yyyy/MM/dd").format(new Date()),
//				"567.jpg", inputStream);
	}
	
}
