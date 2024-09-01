package com.pharma.medicare.response;

import java.util.List;

public class CustomerAllDetailsResponse {

	private String isPresent;
	private List<PdfFileDataResponse> customerPdfFileList;

	public String getIsPresent() {
		return isPresent;
	}

	public void setIsPresent(String isPresent) {
		this.isPresent = isPresent;
	}

	public List<PdfFileDataResponse> getCustomerPdfFileList() {
		return customerPdfFileList;
	}

	public void setCustomerPdfFileList(List<PdfFileDataResponse> customerPdfFileList) {
		this.customerPdfFileList = customerPdfFileList;
	}

	@Override
	public String toString() {
		return "CustomerAllDetailsResponse [isPresent=" + isPresent + ", customerPdfFileList=" + customerPdfFileList
				+ "]";
	}

}
