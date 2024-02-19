package com.pharma.medicare.service;

import org.springframework.stereotype.Service;

import com.pharma.medicare.request.ProductSearchRequest;
import com.pharma.medicare.request.ProductStockRequest;
import com.pharma.medicare.response.ProductSearchResponse;

@Service
public interface StockService {

	ProductSearchResponse getAllProductStock(ProductSearchRequest productSearchRequest);

	String addProductStock(ProductStockRequest productStockRequest);

	String editProductStock(ProductStockRequest productStockRequest);

	String deleteProductStock(Long productId);

}
