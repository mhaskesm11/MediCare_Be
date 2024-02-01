package com.pharma.medicare.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pharma.medicare.constant.ServiceConstants;
import com.pharma.medicare.domain.ProductStock;
import com.pharma.medicare.request.ProductStockRequest;
import com.pharma.medicare.service.StockService;
import com.pharma.medicare.utility.CommonUtil;

@RestController
@CrossOrigin
@RequestMapping("api/v1/stock")
public class StockController {

	private Logger LOGGER = LoggerFactory.getLogger(StockController.class);

	@Autowired
	StockService stockService;

	@GetMapping("all")
	public List<ProductStock> getAllProductStock() {
		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString("api/v1/stock/all")));
		// LOGGER.info(String.format(ServiceConstants.REQUEST_URL,
		// CommonUtil.getString(userRequest)));
		List<ProductStock> response = null;
		try {
			response = stockService.getAllProductStock();
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info(String.format(ServiceConstants.RESPONSE, CommonUtil.getString(response)));
		return response;
	}

	// add stock to existing stock
	@PostMapping("addstock")
	public String addProductStock(@RequestBody ProductStockRequest productStockRequest) {
		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString("api/v1/stock/addstock")));
		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString(productStockRequest)));
		String response = null;
		try {
			response = stockService.addProductStock(productStockRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info(String.format(ServiceConstants.RESPONSE, CommonUtil.getString(response)));
		return response;

	}

	// edit in stock
	@PutMapping("editstock")
	public String editProductStock(@RequestBody ProductStockRequest productStockRequest) {
		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString("api/v1/stock/editstock")));
		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString(productStockRequest)));
		String response = null;
		try {
			response = stockService.editProductStock(productStockRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info(String.format(ServiceConstants.RESPONSE, CommonUtil.getString(response)));
		return response;
	}

	// delete product from stock
	@DeleteMapping("deletestock{productName}")
	public String deleteProductStock(@PathVariable String productName) {
		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString("api/v1/stock/deletestock")));
		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString(productName)));
		String response = null;
		try {
			response = stockService.deleteProductStock(productName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info(String.format(ServiceConstants.RESPONSE, CommonUtil.getString(response)));
		return response;
	}

}
