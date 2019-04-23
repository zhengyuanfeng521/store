package com.zzl.sso.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.zzl.common.pojo.TaotaoResult;
import com.zzl.mapper.TbUserMapper;
import com.zzl.pojo.TbUser;
import com.zzl.pojo.TbUserExample;
import com.zzl.pojo.TbUserExample.Criteria;
import com.zzl.sso.service.UserRegisterService;


@Service
public class UserRegisterServiceImpl implements UserRegisterService {

	@Autowired
	private TbUserMapper userMapper;
	
	@Override
	public TaotaoResult checkDate(String param, Integer type) {
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		
		if(type ==1 ){//1 username
			criteria.andUsernameEqualTo(param);
		}else if(type ==2){//2 phone
			criteria.andPhoneEqualTo(param);
		}else if(type ==3){//3 email
			criteria.andEmailEqualTo(param);
		}else{
			return TaotaoResult.build(400, "非法数据，请更换");
		}
		
		List<TbUser> list = userMapper.selectByExample(example);
		
		if(list != null && list.size() >0){
			return TaotaoResult.ok(false);
		}
		
		return TaotaoResult.ok(true);
	}



	@Override
	public TaotaoResult register(TbUser tbUser) {
		if(StringUtils.isEmpty(tbUser.getUsername())){
			return TaotaoResult.build(400, "注册失败，请校验数据后在提交");
		}
		if(StringUtils.isEmpty(tbUser.getPassword())){
			return TaotaoResult.build(400, "注册失败，请校验数据后在提交");
		}
		TaotaoResult result = checkDate(tbUser.getUsername(), 1);
		
		if(!(boolean)result.getData()){
			return TaotaoResult.build(400, "注册失败，请校验数据后在提交");
		}
		
		if(StringUtils.isNotBlank(tbUser.getPhone())){
			TaotaoResult result2 = checkDate(tbUser.getPhone(), 2);
			if(!(boolean)result2.getData()){
				return TaotaoResult.build(400, "注册失败，请校验数据后在提交");
			}
		}
		
		if(StringUtils.isNotBlank(tbUser.getEmail())){
			TaotaoResult result3 = checkDate(tbUser.getEmail(), 3);
			if(!(boolean)result3.getData()){
				return TaotaoResult.build(400, "注册失败，请校验数据后在提交");
			}
		}
		
		tbUser.setCreated(new Date());
		tbUser.setUpdated(new Date());
		
		tbUser.setPassword(new String(DigestUtils.md5DigestAsHex(tbUser.getPassword().getBytes())));
		
		userMapper.insertSelective(tbUser);
		
		return TaotaoResult.ok();
	}




}
