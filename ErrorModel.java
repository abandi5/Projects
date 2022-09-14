package com.example.demo.exception;


public class ErrorModel {
	private String userMessage;
	private String errorCode;
	private String moreInfo;
	
	public ErrorModel() {
		
	}
	/**
	 * @return the userMessage
	 */
	public String getUserMessage() {
		return userMessage;
	}
	/**
	 * @param userMessage the userMessage to set
	 */
	public void setUserMessage(String userMessage) {
		this.userMessage = userMessage;
	}
	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}
	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	/**
	 * @return the moreInfo
	 */
	public String getMoreInfo() {
		return moreInfo;
	}
	/**
	 * @param moreInfo the moreInfo to set
	 */
	public void setMoreInfo(String moreInfo) {
		this.moreInfo = moreInfo;
	}
	public ErrorModel(String userMessage, String errorCode, String moreInfo) {
		this.userMessage = userMessage;
		this.errorCode = errorCode;
		this.moreInfo = moreInfo;
	}
	

}
