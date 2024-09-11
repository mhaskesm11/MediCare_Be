package com.pharma.medicare.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pharma.medicare.domain.ProductStock;
import com.pharma.medicare.request.ProductSearchRequest;
import com.pharma.medicare.request.ProductStockRequest;
import com.pharma.medicare.response.ProductSearchResponse;

@Service
public interface StockService {

	ProductSearchResponse getAllProductStock(ProductSearchRequest productSearchRequest);

	String addProductStock(List<ProductStockRequest> productStockRequest,String userName);

	String editProductStock(ProductStockRequest productStockRequest,String userName);

	String deleteProductStock(Long productId);

	List<ProductStock> getAllProductDetails();

}
