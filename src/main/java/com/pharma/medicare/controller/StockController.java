package com.pharma.medicare.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pharma.medicare.constant.ServiceConstants;
import com.pharma.medicare.request.ProductSearchRequest;
import com.pharma.medicare.request.ProductStockRequest;
import com.pharma.medicare.response.ProductSearchResponse;
import com.pharma.medicare.service.StockService;
import com.pharma.medicare.utility.CommonUtil;

@RestController
@CrossOrigin
@RequestMapping("api/v1/stock")
public class StockController extends BaseController {

	private Logger LOGGER = LoggerFactory.getLogger(StockController.class);

	@Autowired
	StockService stockService;

	@PostMapping("all")
	public ProductSearchResponse getAllProductStock(@RequestBody ProductSearchRequest productSearchRequest) {
		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString("api/v1/stock/all")));
		
		ProductSearchResponse response = null;
		try {
			response = stockService.getAllProductStock(productSearchRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info(String.format(ServiceConstants.RESPONSE, CommonUtil.getString(response)));
		return response;
	}

	// add stock to existing stock
	@PostMapping("addstock")
	public String addProductStock(@RequestBody List<ProductStockRequest> productStockRequest, HttpServletRequest reqest) {
		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString("api/v1/stock/addstock")));
		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString(productStockRequest)));
		String response = "";
		try {
			String userName=getUserNameFromHeader(reqest);
			response = stockService.addProductStock(productStockRequest,userName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info(String.format(ServiceConstants.RESPONSE, CommonUtil.getString(response)));
		return response;

	}

	// edit in stock
	@PutMapping("editstock")
	public String editProductStock(@RequestBody ProductStockRequest productStockRequest,HttpServletRequest request) {
		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString("api/v1/stock/editstock")));
		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString(productStockRequest)));
		String response = null;
		try {
			String userName=getUserNameFromHeader(request);
			response = stockService.editProductStock(productStockRequest,userName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info(String.format(ServiceConstants.RESPONSE, CommonUtil.getString(response)));
		return response;
	}

	// delete product from stock
	@DeleteMapping("deletestock{productId}")
	public String deleteProductStock(@PathVariable Long productId) {
		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString("api/v1/stock/deletestock")));
		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString(productId)));
		String response = null;
		try {
			response = stockService.deleteProductStock(productId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info(String.format(ServiceConstants.RESPONSE, CommonUtil.getString(response)));
		return response;
	}
	
	

}
