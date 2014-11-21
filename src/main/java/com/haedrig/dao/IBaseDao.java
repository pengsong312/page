package com.haedrig.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.haedrig.util.Condition;
import com.haedrig.util.Page;

public interface IBaseDao {

	/**
	 * @desc 通过主键id获取对象，返回对象需要强制转换
	 * @param c
	 * @param id
	 * @return Object
	 * @author liny
	 * @datetime 2014年8月4日 上午10:35:26
	 */
	public Object getObject(Class<?> c, Serializable id);
	
	/**
	 * @desc 通过hql查选对象，返回对象需要强制转换
	 * @param hql
	 * @param value
	 * @return Object
	 * @author liny
	 * @datetime 2014年8月4日 上午10:37:14
	 */
	public Object getObjectByHql(String hql, Object... value);
	
	/**
	 * @desc 通过hql做更新操作，根据条件传递0-n个参宿，返回布尔类型
	 * @param hql
	 * @param value
	 * @return boolean
	 * @author liny
	 * @datetime 2014年8月4日 上午10:40:32
	 */
	public boolean updateHql(String hql, Object... value);
	
	/**
	 * @desc 保存或更新对象
	 * @param o
	 * @return boolean
	 * @author liny
	 * @datetime 2014年8月4日 上午10:43:22
	 */
	public boolean saveObject(Object o);
	
	/**
	 * @desc 保存对象
	 * @param o
	 * @return boolean
	 * @author liny
	 * @datetime 2014年8月4日 上午10:50:28
	 */
	public boolean addObject(Object o);
	
	/**
	 * @desc 删除对象
	 * @param o
	 * @author liny
	 * @datetime 2014年8月4日 上午10:50:54
	 */
	public void deleteObject(Object o);
	
	/**
	 * @desc 通过hql查询数据集合
	 * @param hql
	 * @param rows
	 * @param value
	 * @return List
	 * @author liny
	 * @datetime 2014年8月4日 上午10:51:14
	 */
	@SuppressWarnings("rawtypes")
	public List getBySql(String hql,Integer rows,Object ... value);
	
	/**
	 * @desc 通过hql查询数据数量
	 * @param hql
	 * @param value
	 * @return int
	 * @author liy
	 * @datetime 2014年8月5日,下午3:27:12
	 */
	public int getIntCount(String hql,Object ... value);
	
	/**
	 * @desc 分页中获得查询记录的总条数
	 * @param c
	 * @return int
	 * @author liy
	 * @datetime 2014年8月5日,下午3:29:03
	 */
	public int getIntCount(Condition c);
	
	/**
	 * @desc 获得分页对象
	 * @param currentPage 当前第几页
	 * @param numPerPage 每页多少条记录
	 * @param map 页面传来的分页参数
	 * @param countSql 查询记录条数的HQL语句
	 * @param statusSql 查询记录的HQL语句
	 * @param objectList
	 * @return Page
	 * @author liy
	 * @datetime 2014年8月5日,下午3:36:33
	 */
	public Page getPage(int currentPage, int numPerPage, Map<String, Object> map, String countSql, String statusSql, List<Object> objectList);

}
