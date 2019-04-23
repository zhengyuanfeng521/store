package com.zzl.service.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.apache.ibatis.builder.annotation.MapperAnnotationBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;
import com.zzl.mapper.Mapper;
import com.zzl.pojo.Example;
import com.zzl.service.BaseService;


public class BaseServiceImpl<T> implements BaseService<T> {
	
	@Autowired
	private Mapper<T> mapper;

	private Class<T> clazz;

	public BaseServiceImpl() {
		
		Type type = this.getClass().getGenericSuperclass();
		
		ParameterizedType pType = (ParameterizedType) type;
		
		this.clazz = (Class<T>) pType.getActualTypeArguments()[0];
	}
	
	@Override
	public T queryById(Long id) {
		T t = this.mapper.selectByPrimaryKey(id);
		return t;
	}

	@Override
	public List<T> queryAll() {
		List<T> list = this.mapper.select(null);
		return list;
	}

	@Override
	public Integer queryCountByWhere(T t) {
		int count = this.mapper.selectCount(t);
		return count;
	}

	@Override
	public List<T> queryListByWhere(T t) {
		List<T> list = this.mapper.select(t);
		return list;
	}

	@Override
	public List<T> queryListByPage(Integer page, Integer rows) {
		PageHelper.startPage(page, rows);

		List<T> list = this.mapper.select(null);

		return list;
	}

	@Override
	public T queryOne(T t) {
		T result = this.mapper.selectOne(t);
		return result;
	}

	@Override
	public void save(T t) {
		this.mapper.insert(t);
	}

	@Override
	public void saveSelective(T t) {
		this.mapper.insertSelective(t);
	}

	@Override
	public void updateById(T t) {
		this.mapper.updateByPrimaryKey(t);
	}

	@Override
	public void updateByIdSelective(T t) {
		this.mapper.updateByPrimaryKeySelective(t);
	}

	@Override
	public void deleteById(Long id) {
		this.mapper.deleteByPrimaryKey(id);
	}

	@Override
	public void deleteByIds(List<Object> ids) {
		// 声明条件
//		Example example = new Example(this.clazz);
//		example.createCriteria().andIn("id", ids);
//
//		this.mapper.deleteByExample(example);
		/*
		 * 这里先这样待最后完成
		 */
	}

	@Override
	public List<T> queryByPage(Integer page, Integer rows) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
