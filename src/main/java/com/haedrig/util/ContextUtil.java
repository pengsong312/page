package com.haedrig.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class ContextUtil {
	
	private static final Log log = LogFactory.getLog(ContextUtil.class);
	
	private String pageName="page";
	private String jiajiaxinxicailiao;
	private String cailiaogongshi;
	
	public StringBuffer buffer = new StringBuffer();
	
	public void setPageName(String pageName){
		this.pageName=pageName;
	}
	public String getPageName(){
		return pageName;
	}
	
	/**
	 * @author QZY
	 * @param params
	 * @说明 返回查询条件
	 */
	public static String getQuery(Map<String, Object> params){
		String query="";
		
		if(params==null)
			return query;
		
		Iterator<String> iter=params.keySet().iterator();
		while(iter.hasNext()){
			String key=iter.next();
		
			// 不对 PAGENAME 做参数拼接
			if(!key.equals("page")){
				String value=ParamUtil.getArray(params, key);
				if(null!=value && !"".equals(value)){
					try {
						query+="&"+key+"="+java.net.URLEncoder.encode(value,"UTF-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		if(query.length()>1)
			query=query.substring(1);
		
		return query;
	}
	
	public static String getQueryString(Map<String, Object> params){
//		ContextUtil util=new ContextUtil();
		return ContextUtil.getQuery(params);
	}
	/**
	 * 生成按照日期编码
	 * @return
	 */
	public static String builderTradeNo(){
		Date date=new Date();
		Calendar  c = java.util.Calendar.getInstance(); 		
		SimpleDateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
		format.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));

		c.setTime(date);
		String mill=new Integer(c.get(Calendar.MILLISECOND)).toString();
		StringBuffer buffer=new StringBuffer(mill);
		
		for(int i=0;i<(3-mill.length());i++){
			buffer.insert(0, new StringBuffer("0"));
		}
		
		if(mill.equals("0"))
			buffer.append("0");
		buffer.insert(0, format.format(date));
		return buffer.toString();
	}
	/**
	 * 发送邮件模板
	 * @param filename
	 * @param items
	 * @return
	 */

	@SuppressWarnings({ "rawtypes", "resource" })
	public static String getTemplateMessage(String filename, List items) {
		try{
			InputStream in=new FileInputStream(filename);
			InputStreamReader r = new InputStreamReader(in,"UTF-8");
			BufferedReader rr = new BufferedReader(r);
			String inputLine;
			StringBuffer str=new StringBuffer();
			while((inputLine = rr.readLine()) != null){
				str.append(inputLine);
				str.append("\n");
			}
			
			
			return formatText(items,str.toString());
		}catch(Exception e){
			log.error(e);
		}
		return null;
	}
	/**
	 * 格式化发送邮件模板
	 * @param items
	 * @param text
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String formatText(List items, String text){
		String key="";
		for(int i=0;i<items.size();i++){
			key="{"+i+"}";
			text=text.replace(key, (String)items.get(i));			
		}
		return text;
	}
	
	public static void main(String[] args) {
		System.out.println(builderTradeNo());
	}
	
	public String getJiajiaxinxicailiao() {
		return jiajiaxinxicailiao;
	}
	public void setJiajiaxinxicailiao(String jiajiaxinxicailiao) {
		this.jiajiaxinxicailiao = jiajiaxinxicailiao;
	}
	public String getCailiaogongshi() {
		return cailiaogongshi;
	}
	public void setCailiaogongshi(String cailiaogongshi) {
		this.cailiaogongshi = cailiaogongshi;
	}
}
