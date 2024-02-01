package com.pharma.medicare.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pharma.medicare.domain.ProductStock;
import com.pharma.medicare.request.ProductStockRequest;

@Service
public interface StockService {

	List<ProductStock> getAllProductStock();

	String addProductStock(ProductStockRequest productStockRequest);

	String editProductStock(ProductStockRequest productStockRequest);

	String deleteProductStock(String productName);

}
