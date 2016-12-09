package com.wang.lucene;

import org.apache.lucene.search.ScoreDoc;

import java.util.Collection;

/**
 * 分页对象
 *
 * @param <T>
 * @author wang
 */
public class Page<T> {
    /**
     * 当前第几页(从1开始计算)
     */
    private int currentPage;
    /**
     * 每页显示几条
     */
    private int pageSize;
    /**
     * 总记录数
     */
    private int totalRecord;
    /**
     * 总页数
     */
    private int totalPage;
    /**
     * 分页数据集合[用泛型T来限定集合元素类型]
     */
    private Collection<T> items;
    /**
     * 上一页最后一个ScoreDoc对象
     */
    private ScoreDoc afterDoc;
    /**
     * 上一页最后一个ScoreDoc对象的Document对象ID
     */
    private int afterDocId;

    public int getCurrentPage() {
        if (currentPage <= 0) {
            currentPage = 1;
        } else {
            int totalPage = getTotalPage();
            if (totalPage > 0 && currentPage > getTotalPage()) {
                currentPage = totalPage;
            }
        }
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        if (pageSize <= 0) {
            pageSize = 10;
        }
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    public int getTotalPage() {
        int totalRecord = getTotalRecord();
        if (totalRecord == 0) {
            totalPage = 0;
        } else {
            int pageSize = getPageSize();
            totalPage = totalRecord % pageSize == 0 ? totalRecord / pageSize : (totalRecord / pageSize) + 1;
        }
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public Collection<T> getItems() {
        return items;
    }

    public void setItems(Collection<T> items) {
        this.items = items;
    }

    public ScoreDoc getAfterDoc() {
        setAfterDocId(afterDocId);
        return afterDoc;
    }

    public void setAfterDoc(ScoreDoc afterDoc) {
        this.afterDoc = afterDoc;
    }

    public int getAfterDocId() {
        return afterDocId;
    }

    public void setAfterDocId(int afterDocId) {
        this.afterDocId = afterDocId;
        if (null == afterDoc) {
            this.afterDoc = new ScoreDoc(afterDocId, 1.0f);
        }
    }

    public Page() {
    }

    public Page(int currentPage, int pageSize) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }

    public Page(int currentPage, int pageSize, Collection<T> items) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.items = items;
    }

    public Page(int currentPage, int pageSize, int afterDocId) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.afterDocId = afterDocId;
    }

}