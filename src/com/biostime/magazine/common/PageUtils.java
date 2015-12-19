package com.biostime.magazine.common;

import java.util.ArrayList;
import java.util.List;

import com.baiytfp.commonlib.base.service.CommonService;

/**
 * 分页封装类
 *	
 * @author xc 新增日期：2014-3-17下午07:52:28
 * @version 1.0
 */
public class PageUtils {
	/**
	 * 获取结果集，可分页
	 * 
	 * @param cs
	 * @param sql
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 * @author xc 新增日期：2014-3-17下午07:52:09
	 * @version 1.0
	 */
	@SuppressWarnings("unchecked")
	public static List getItemMapList(CommonService cs, String sql, Object[] params, int pageIndex, int pageSize) throws Exception{
		String limit = " limit " + (pageIndex-1)*pageSize + "," + pageSize;
		List pageList = new ArrayList();
		pageList = cs.getItemMapList(DBManager.getConnection(), sql + limit, params);
		return pageList;
	}
}
