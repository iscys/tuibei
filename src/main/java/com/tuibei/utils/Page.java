package com.tuibei.utils;

import lombok.Data;

@Data
public class Page {

    private int pageSize;           //每页条数20条
    private int startIndex;                 //每页开始的索引
    private int totalPage;                  //总页数
    private int totalRecords;               //总条数
    private int pageNum;                    //当前页



    public Page(int pageNum , int totalRecords,int pageSize){
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
    }

}
