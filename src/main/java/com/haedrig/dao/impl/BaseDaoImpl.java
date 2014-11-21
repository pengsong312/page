package com.haedrig.dao.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.haedrig.dao.IBaseDao;
import com.haedrig.util.Condition;
import com.haedrig.util.ContextUtil;
import com.haedrig.util.Page;
import com.haedrig.util.PageUtil;

@Repository
public class BaseDaoImpl implements IBaseDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	public Session getSession() throws HibernateException { 
		Session session = sessionFactory.getCurrentSession();
		session.setFlushMode(FlushMode.COMMIT);
		return session;
    }
	
	public Object getObject(@SuppressWarnings("rawtypes") Class c, Serializable id) {
		return getSession().get(c, id);
	}
	
	@SuppressWarnings("rawtypes")
	public Object getObjectByHql(String hql, Object... value) {
		List list = getBySql(hql, 1, value);
		if(list != null && list.size() >0){
			return list.get(0);
		}
		return null;
	}
	
	public boolean saveObject(Object o) {
		try {
			Field id = o.getClass().getDeclaredField("id");
			id.setAccessible(true);
			String str = (String)id.get(o);
			id.setAccessible(false);
			if(str == null || "".equals(str)){
				getSession().save(o);
			}else{
				getSession().update(o);
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public boolean addObject(Object o) {
		getSession().save(o);
		return true;
	}
	
	public boolean updateHql(String hql, Object... value) {
		Query q = getSession().createQuery(hql);
		if (hql.indexOf("?") != -1){
    		for(int i=0;i<value.length;i++){
    			q.setParameter(i, value[i]);
    		}
    	}
		q.executeUpdate();
		return true;
	}
	
	public void deleteObject(Object o) {
		getSession().delete(o);
	}
	
	@SuppressWarnings("rawtypes")
	public List getBySql(String hql, Integer rows, Object... value) {
		Query q = getSession().createQuery(hql);
		if(rows != null){
			q.setMaxResults(rows);
		}
    	if (hql.indexOf("?") != -1){
    		for(int i=0;i<value.length;i++){
    			q.setParameter(i, value[i]);
    		}
    	}
    	List l = q.list();
    	return l;
	}
	
	@SuppressWarnings("rawtypes")
	public int getIntCount(String hql,Object ... value) {
		List l = null;
		Query q = getSession().createQuery(hql);
		if (hql.indexOf("?") != -1){
    		for(int i=0;i<value.length;i++){
    			q.setParameter(i, value[i]);
    		}
    	}
		l = q.list();
		return new Integer(String.valueOf(l.get(0)));
	}
	
	@SuppressWarnings("rawtypes")
	public int getIntCount(Condition c) {
		String hql = c.getSql();
		List l = null;
		Query q = getSession().createQuery(hql);
		if (hql.indexOf("?") != -1){
    		for(int i=0;i<c.getObject().length;i++){
    			q.setParameter(i, c.getObject()[i]);
    		}
    	}
		l = q.list();
		return new Integer(String.valueOf(l.get(0)));
	}
	
	public Page getPage(int pageNumber, int maxRows, String url,
    		Map<String, Object> map,String countSql,String statusSql,List<Object> objectList,List<Type> typeList) {
    	String method = ContextUtil.getQueryString(map);//取
    	Condition condition = new Condition(countSql,objectList,typeList);
    	int totalRow = getIntCount(condition);
    	condition.setSql(statusSql);
    	Page page = new Page(maxRows, url, method);
    	page.setCurPage(pageNumber);
    	page.setCondition(condition);
    	page = PageUtil.createPage(page, totalRow);
    	page=findByPage(page);
    	return page;
    }
	
	public Page getPage(int currentPage, int numPerPage, Map<String, Object> map, String countSql, String statusSql, List<Object> objectList) {
    	List<Type> typeList = new ArrayList<Type>();
    	Condition condition = new Condition(countSql,objectList,typeList);
    	int totalRow = getIntCount(condition);
    	condition.setSql(statusSql);
    	Page page = new Page(numPerPage, null, null);
    	page.setCurPage(currentPage);
    	page.setCondition(condition);
    	page.setTotalRow(totalRow);
    	page.setBeginIndex(getBeginIndex(numPerPage, currentPage));
    	page = PageUtil.createPage(page, totalRow);
    	page = findByPage(page);
    	return page;
    }
	
	private int getBeginIndex(int pageSize, int curPage) {
		return (curPage - 1) * pageSize;
	}
	
	@SuppressWarnings("rawtypes")
	public Page findByPage(final Page page) {
		Condition condition = page.getCondition();
		String hql = condition.getSql();
		Query query = getSession().createQuery(hql);
		if (condition.getSql().indexOf("?") != -1){
			for(int i=0;i<condition.getObject().length;i++){
				query.setParameter(i, condition.getObject()[i]);
    		}
		}
		query.setFirstResult(page.getBeginIndex());
		query.setMaxResults(page.getPageSize());
		List list = query.list();
		page.setResult(list);
		return page;
	}
	
}
