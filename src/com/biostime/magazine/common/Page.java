/**
 * 
 */
package com.biostime.magazine.common;

import java.util.List;

/**
 * 分页类
 *	
 * @author xc 新增日期：2014-3-17下午07:51:18
 * @version 1.0
 */
public class Page {
	
	/**
	 * 当前页
	 */
	public Integer currentPage;
	/**
	 * 上一页。current等于1时，上一页为null
	 */
	private Integer previousPage;
	/**
	 * 下一页。current等于pageCount时，下一页为null
	 */
	private Integer nextPage;
	/**
	 * 每页记录数
	 */
	private Integer pageSize;
	/**
	 * 总页数。rowCount等于0时，pageCount等于1（相当于空页），保证至少有一页。
	 */
	private Integer pageCount;
	/**
	 * 总记录数
	 */
	private Integer rowCount;
	/**
	 * 页码索引显示数，经常出现有以下这种分页形式的，比如百度：<br/>
	 * 上一页 2 3 4 5 6 7 8 9 10 11 下一页<br />
	 * indexCount正是定义页码索引的数量。
	 */
	private Integer indexCount;
	/**
	 * 开始索引页码
	 */
	private Integer startIndex;
	/**
	 * 结束索引页码
	 */
	private Integer endIndex;
	/**
	 * 结果集
	 */
	@SuppressWarnings("unchecked")
	private List result;
	
	@SuppressWarnings("unchecked")
	public Page(Integer currentPage, Integer pageSize, Integer rowCount, List result) {
		this.pageSize = pageSize;
		this.currentPage = currentPage;
		/**设置总记录数，一并设置总页数、上一页和下一页*/
		this.setRowCount(rowCount);
		this.result = result;
		/**设置配置文件定义的页码索引数量*/
		String paginationIndexCount = "10000";
		this.setIndexCount(Integer.valueOf(paginationIndexCount));
	}
	
	public Integer getPageCount() {
		return pageCount;
	}
	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
		
	}
	public Integer getRowCount() {
		return rowCount;
	}
	public void setRowCount(Integer rowCount) {
		this.rowCount = rowCount;
		this.pageCount = rowCount % pageSize == 0 ? rowCount / pageSize
				: rowCount / pageSize + 1;
		
		/**保证至少有一页，即使是0条记录*/
		this.pageCount = (this.pageCount == 0 ? 1 : this.pageCount);
		this.previousPage = (currentPage == 1 ? null : currentPage - 1);
		this.nextPage = (currentPage == pageCount ? null : currentPage + 1);
	}
	public Integer getIndexCount() {
		return indexCount;
	}
	public void setIndexCount(Integer indexCount) {
		this.indexCount = indexCount;
		int leftCount = indexCount / 2;
		int rightCount = indexCount / 2 - 1;

		int startPage = currentPage - leftCount > 0
				&& pageCount > indexCount ? currentPage - leftCount : 1;
		int endPage = currentPage + rightCount < pageCount
				&& pageCount > indexCount ? currentPage + rightCount : pageCount;
		endPage = endPage <= indexCount
				&& pageCount > indexCount ? endPage + indexCount - endPage : endPage;
				
		startPage = endPage - startPage == indexCount-1 ? startPage : 1 + startPage - (indexCount - (endPage - startPage));
		startPage = startPage < 1 ? 1 : startPage;
		endPage = endPage < 1 ? 1 : endPage;
		
		setStartIndex(startPage);
		setEndIndex(endPage);
	}
	public Integer getStartIndex() {
		return startIndex;
	}
	public void setStartIndex(Integer startIndex) {
		this.startIndex = startIndex;
	}
	public Integer getEndIndex() {
		return endIndex;
	}
	public void setEndIndex(Integer endIndex) {
		this.endIndex = endIndex;
	}
	@SuppressWarnings("unchecked")
	public List getResult() {
		return result;
	}
	@SuppressWarnings("unchecked")
	public void setResult(List result) {
		this.result = result;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 返回成员属性 currentPage
	 * @return currentPage
	 */
	public Integer getCurrentPage() {
		return currentPage;
	}

	/**
	 * 将传入参数currentPage 赋给成员属性 currentPage
	 * @param currentPage
	 */
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	/**
	 * 返回成员属性 previousPage
	 * @return previousPage
	 */
	public Integer getPreviousPage() {
		return previousPage;
	}

	/**
	 * 将传入参数previousPage 赋给成员属性 previousPage
	 * @param previousPage
	 */
	public void setPreviousPage(Integer previousPage) {
		this.previousPage = previousPage;
	}

	/**
	 * 返回成员属性 nextPage
	 * @return nextPage
	 */
	public Integer getNextPage() {
		return nextPage;
	}

	/**
	 * 将传入参数nextPage 赋给成员属性 nextPage
	 * @param nextPage
	 */
	public void setNextPage(Integer nextPage) {
		this.nextPage = nextPage;
	}
}
