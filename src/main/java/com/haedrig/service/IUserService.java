package com.haedrig.service;

import java.util.Map;

import com.haedrig.util.Page;

public interface IUserService {

	Page findUsersByPage(Map<String, Object> map);
	
}
