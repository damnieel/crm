package com.cvc.crm.util;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

public class Pager<T> {

	private String sql;
	private String sqlCount;
	private String sqlLimit;
	private ResultSetHandler<List<T>> rsh;
	private Object[] params;

	private int pageNumber;
	private int pageSize;
	private int totalPages;
	private int totalElements;

	private List<T> content;

	public Pager(int pageNumber, int pageSize, String sql, ResultSetHandler<List<T>> rsh, Object... params) throws SQLException {
		this.sql = sql;
		this.rsh = rsh;
		this.params = params;
		setPageSize(pageSize);
		setTotalElements();
		setTotalPages();
		setPageNumber(pageNumber);
		setContent();
	}

	public Pager(Object pageNumber, Object pageSize, String sql, ResultSetHandler<List<T>> rsh, Object... params) throws SQLException {
		this(parseInt(pageNumber), parseInt(pageSize), sql, rsh, params);
	}

	public Pager(Object pageNumber, Object pageSize, String sqlCount, String sqlLimit, ResultSetHandler<List<T>> rsh, Object... params) throws SQLException {
		this.sqlCount = sqlCount;
		this.sqlLimit = sqlLimit;
		this.rsh = rsh;
		this.params = params;
		setPageSize(parseInt(pageSize));
		setTotalElements();
		setTotalPages();
		setPageNumber(parseInt(pageNumber));
		setContent();
	}

	private static int parseInt(Object input) {
		try {
			return Integer.parseInt(String.valueOf(input));
		} catch (Exception e) {
			return -1;
		}
	}

	public int getPageNumber() {
		return pageNumber;
	}

	private void setPageNumber(int pageNumber) {
		if (totalPages <= 1 || totalPages <= pageNumber) {
			this.pageNumber = totalPages;
		} else if (pageNumber <= 1) {
			this.pageNumber = 1;
		} else {
			this.pageNumber = pageNumber;
		}
	}

	public int getPageSize() {
		return pageSize;
	}

	private void setPageSize(int pageSize) {
		if (pageSize >= 1 && pageSize <= 3000) {
			this.pageSize = pageSize;
		} else {
			this.pageSize = 10;
		}
	}

	public int getTotalPages() {
		return totalPages;
	}

	private void setTotalPages() {
		if (totalElements <= 0) {
			totalPages = 0;
			return;
		}
		if (totalElements % pageSize == 0) {
			totalPages = totalElements / pageSize;
		} else {
			totalPages = totalElements / pageSize + 1;
		}
	}

	public int getTotalElements() {
		return totalElements;
	}

	private void setTotalElements() throws SQLException {
		String s = sql == null ? sqlCount : "SELECT COUNT(1) FROM ( " + sql + " ) t";
		totalElements = DBUtils.getQueryRunner().query(s, new ScalarHandler<Long>(1), params).intValue();
	}

	public List<T> getContent() {
		return content;
	}

	private void setContent() throws SQLException {
		int offset = pageNumber <= 1 ? 0 : (pageNumber - 1) * pageSize;
		String s = (sql == null ? sqlLimit : "SELECT * FROM ( " + sql + " ) t") + " LIMIT " + offset + ", " + pageSize;
		this.content = DBUtils.getQueryRunner().query(s, rsh, params);
	}

	public boolean hasContent() {
		return !content.isEmpty();
	}

	public int getContentSize() {
		return content.size();
	}

	public boolean hasPrevious() {
		return pageNumber > 1;
	}

	public boolean hasNext() {
		return (pageNumber + 1) <= totalPages;
	}

	public int getPrevious() {
		return hasPrevious() ? (pageNumber - 1) : totalPages > 0 ? 1 : 0;
	}

	public int getNext() {
		return hasNext() ? (pageNumber + 1) : totalPages;
	}

	public boolean isFirst() {
		return !hasPrevious();
	}

	public boolean isLast() {
		return !hasNext();
	}

	@Override
	public String toString() {
		return "Pageable [pageNumber=" + pageNumber + ", pageSize=" + pageSize + ", totalPages=" + totalPages + ", totalElements=" + totalElements + ", hasContent=" + hasContent() + ", contentSize=" + getContentSize() + ", hasPrevious=" + hasPrevious() + ", hasNext=" + hasNext() + ", previous=" + getPrevious() + ", next=" + getNext() + ", isFirst=" + isFirst() + ", isLast=" + isLast() + ", sql=" + sql + ", params=" + Arrays.toString(params) + "]";
	}

}
