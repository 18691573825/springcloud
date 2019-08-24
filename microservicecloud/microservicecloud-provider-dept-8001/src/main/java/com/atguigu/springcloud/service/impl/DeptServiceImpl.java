package com.atguigu.springcloud.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.atguigu.springcloud.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.springcloud.dao.DeptDao;
import com.atguigu.springcloud.entities.Dept;
import com.atguigu.springcloud.service.DeptService;

@Service
public class DeptServiceImpl implements DeptService
{
	@Autowired
	private DeptDao dao;

	@Override
	public boolean add(Dept dept)
	{
		return dao.addDept(dept);
	}

	@Override
	public Dept get(Long id)
	{
		return dao.findById(id);
	}

	@Override
	public List<Dept> list()
	{
		RedisUtil redisUtil=new RedisUtil();
		Object list=null;
		List<Dept> all=null;
		if(redisUtil.hasKey("list")){
			System.out.println("缓存中有");
			list = redisUtil.get("list");
			List<Dept> deptList=new ArrayList<Dept>();
			Dept dept=new Dept(list.toString());
			deptList.add(dept);
			return deptList;
		}else{
			System.out.println("缓存中没有");
			all = dao.findAll();
			redisUtil.set("list",all);
		}
		return all;
//		return dao.findAll();
	}

}
