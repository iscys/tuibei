package com.tuibei.utils;

import java.io.Serializable;

/**
 * 类名: Page
 * <br/>作用1: 可接收页面收集到的数据使用
 * <br/>作用2: 可作为map集合使用
 * <br/>作用3: 可作为分页数据使用
 * <br/>作者: yanpengjie 
 * <br/>日期: 2015年1月4日 下午8:55:16 
 */
public class DataPager implements Serializable {

	private static final long serialVersionUID = 2133414293322453120L;
	
	private Object records;			//每页要显示的内容集合
	private int pageSize = 100;				//每页条数100条//默认值
	private int startIndex;					//每页开始的索引
	private int totalPage;					//总页数
	private int totalRecords;				//总条数
	private int pageNum;					//当前页
	private int nextPageNum;				//下一页
	private int prePageNum;					//上一页
	private String url;						//URL地址
	private String formId;					//formId
	private int startPage;					//起始页
	private int endPage;					//结束页

	/**
	 * @param pageNum 当前页码
	 * @param totalRecords 总记录条数
	 * @param pageSize 每页要显示的条数
	 */
	public DataPager(){}
	public DataPager(int pageNum , int totalRecords, int pageSize){
		this.pageNum = pageNum;
		this.totalRecords = totalRecords;
		this.pageSize = pageSize;
		totalPage = (totalRecords%pageSize==0)?totalRecords/pageSize:totalRecords/pageSize+1;
		
		if(this.pageNum>totalPage){
			this.pageNum=totalPage;
		}
		if(this.pageNum<1){
			this.pageNum=1;
		}
		startIndex = (this.pageNum-1)*pageSize;
		if(totalPage<=9){
			startPage = 1;
			endPage = totalPage;
		}else{
			startPage = pageNum-4;
			endPage = pageNum+4;
			
			if(startPage<1){
				startPage = 1;
				endPage = startPage+8;
			}
			if(endPage>totalPage){
				endPage = totalPage;
				startPage = totalPage-8;
			}
		}
	}
	/**
	 * 默认每页显示100条
	 * @param pageNum 当前页码
	 * @param totalRecords 总记录条数
	 */
	public DataPager(int pageNum , int totalRecords){
		this.pageNum = pageNum;
		this.totalRecords = totalRecords;
		totalPage = (totalRecords%pageSize==0)?totalRecords/pageSize:totalRecords/pageSize+1;
		
		if(this.pageNum>totalPage){
			this.pageNum=totalPage;
		}
		if(this.pageNum<1){
			this.pageNum=1;
		}
		startIndex = (this.pageNum-1)*pageSize;
		if(totalPage<=9){
			startPage = 1;
			endPage = totalPage;
		}else{
			startPage = pageNum-4;
			endPage = pageNum+4;
			
			if(startPage<1){
				startPage = 1;
				endPage = startPage+8;
			}
			if(endPage>totalPage){
				endPage = totalPage;
				startPage = totalPage-8;
			}
		}
	}
	
	/*** 参数: formId the formId to set ***/
	public void setFormId(String formId) {
		this.formId = formId;
	}

	/*** 返回: the formId ***/
	public String getFormId() {
		return formId;
	}
	
	public int getStartPage() {
		return startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public Object getRecords() {
		return records;
	}

	public void setRecords(Object records) {
		this.records = records;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public int getPageNum() {
		return pageNum;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getNextPageNum() {
		nextPageNum = pageNum+1<totalPage?pageNum+1:totalPage;
		if(nextPageNum==0){
			nextPageNum=1;
		}
		return nextPageNum;
		
	}

	public int getPrePageNum() {
		prePageNum = pageNum-1>0?pageNum-1:1;
		return prePageNum;
		
	}
	
	/**
	 * 设置分页详情数据，用于简化分页操作
	 * @param pageN   :当前页码数 ，会被pd 动态修改
	 * @param pageS		：展示的数据数量，会被pd 动态修改
	 * @param pd	：前台传过来的PageData
	 * @param num	:数据总数量，一般我们是从数据库中查询获取的
	 * @return
	 * cys
	 */
	public static DataPager page(int pageN, int pageS,PageData pd,Integer num) {
		int pageNum=pageN;
		int pageSize=pageS;
		
		if(pd.getString("page")!=null){
			pageNum=Integer.valueOf(pd.getString("page"));
		}
		if(pd.get("limit")!=null){
			pageSize=Integer.valueOf(pd.getString("limit"));
		}
		DataPager dp =new DataPager(pageNum, num, pageSize);
		pd.put("startIndex", dp.getStartIndex());
		pd.put("pageSize", pageSize);
		pd.put("total", dp.getTotalRecords());
		
		return dp;
	}
}
