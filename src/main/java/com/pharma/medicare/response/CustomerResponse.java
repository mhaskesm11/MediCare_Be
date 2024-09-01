package com.pharma.medicare.response;

import java.util.List;

public class CustomerResponse {
	
	private long count;
	private long limit;
	private long page;
	private List<CustomerSearchDto> resultObject;
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	public long getLimit() {
		return limit;
	}
	public void setLimit(long limit) {
		this.limit = limit;
	}
	public long getPage() {
		return page;
	}
	public void setPage(long page) {
		this.page = page;
	}
	public List<CustomerSearchDto> getResultObject() {
		return resultObject;
	}
	public void setResultObject(List<CustomerSearchDto> resultObject) {
		this.resultObject = resultObject;
	}
	@Override
	public String toString() {
		return "CustomerResponse [count=" + count + ", limit=" + limit + ", page=" + page + ", resultObject="
				+ resultObject + "]";
	}
	

}
