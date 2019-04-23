package com.zzl.sso.service.impl;

import java.util.List;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import com.zzl.common.pojo.TaotaoResult;
import com.zzl.common.util.JsonUtils;
import com.zzl.jedis.JedisClient;
import com.zzl.mapper.TbUserMapper;
import com.zzl.pojo.TbUser;
import com.zzl.pojo.TbUserExample;
import com.zzl.pojo.TbUserExample.Criteria;
import com.zzl.sso.service.UserLoginService;


@Service
public class UserLoginServiceImpl implements UserLoginService {
	
	@Autowired
	private TbUserMapper userMapper;
	
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${USER_INFO}")
	private String USER_INFO;
	
	@Value("${EXPIRE_TIME}")
	private int EXPIRE_TIME;
	
	@Override
	public TaotaoResult login(String username, String password) {
		
		if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
			return TaotaoResult.build(400, "用户名或密码错误");
		}
		
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		
		List<TbUser> list = userMapper.selectByExample(example);
		if(list ==null || list.size() == 0){
			return TaotaoResult.build(400, "用户名不存在");
		}
		TbUser user = list.get(0);
		
		String md5DigestAsHex = DigestUtils.md5DigestAsHex(password.getBytes());
		if(!md5DigestAsHex.equals(user.getPassword())){
			TaotaoResult.build(400, "密码错误");
		}
//		生成token
		String token = UUID.randomUUID().toString();
		user.setPassword(null);
		jedisClient.set(USER_INFO+":"+token, JsonUtils.objectToJson(user));
		jedisClient.expire(USER_INFO+":"+token, EXPIRE_TIME);
		
		return TaotaoResult.ok(token);
	}

	@Override
	public TaotaoResult getUserByToken(String token) {
		String strjson = jedisClient.get(USER_INFO+":"+token);
		if(StringUtils.isNotBlank(strjson)){
			TbUser user = JsonUtils.jsonToPojo(strjson, TbUser.class);
			jedisClient.expire(USER_INFO+":"+token, EXPIRE_TIME);
			return TaotaoResult.ok(user);
		}
		return TaotaoResult.build(400, "用户已过期");
	}

	@Override
	public TaotaoResult loginOut(String token) {
		jedisClient.expire(USER_INFO+":"+token, 0);
		return TaotaoResult.ok();
	}

}
