package com.smart.dto;



public class PageResponseDto<T> {
	
	private int recordCount;
	T response;
	
	public PageResponseDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PageResponseDto(int recordCount, T response) {
		super();
		this.recordCount = recordCount;
		this.response = response;
	}
	public int getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}
	public T getResponse() {
		return response;
	}
	public void setResponse(T response) {
		this.response = response;
	}
	
	
	
}
