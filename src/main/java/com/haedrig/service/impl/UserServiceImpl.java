package com.haedrig.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haedrig.dao.IBaseDao;
import com.haedrig.service.IUserService;
import com.haedrig.util.Page;
import com.haedrig.util.ParamUtil;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	protected IBaseDao iBaseDao;
	
	public Page findUsersByPage(Map<String, Object> map) {
		StringBuilder countSql = new StringBuilder("select count(*) from User");
		StringBuilder statusSql = new StringBuilder("from User");
		List<Object> objectList = new ArrayList<Object>();
		Page page = this.iBaseDao.getPage(ParamUtil.getInteger(map,"pageIndex",1),ParamUtil.getInteger(map, "pageSize", 10),
				map, countSql.toString(), statusSql.toString(), objectList);
		return page;
	}

}
