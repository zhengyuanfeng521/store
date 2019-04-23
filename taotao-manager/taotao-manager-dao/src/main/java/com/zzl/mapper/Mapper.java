package com.zzl.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zzl.pojo.TbContentCategory;
import com.zzl.pojo.TbContentCategoryExample;

public interface Mapper<T> {
	


    int deleteByPrimaryKey(Long id);

    int insert(T t);

    int insertSelective(T t);

    T selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(T t);

    int updateByPrimaryKey(T t);

	/**
	 * 查询的方法 如果为空则查询所有
	 */
	List<T> select(Object object);
	
	int selectCount(T t);
	
	T selectOne(T t);

}
