package com.pharma.medicare.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pharma.medicare.constant.ServiceConstants;
import com.pharma.medicare.domain.ProductStock;
import com.pharma.medicare.repository.ProductStockRepository;
import com.pharma.medicare.request.ProductStockRequest;
import com.pharma.medicare.service.StockService;

@Service
public class StockServiceimpl implements StockService {

	private Logger LOGGER = LoggerFactory.getLogger(StockServiceimpl.class);

	@Autowired
	ProductStockRepository productStockRepository;

	@Override
	public List<ProductStock> getAllProductStock() {
		LOGGER.info("Entry :: StockServiceimpl :: getAllProductStock():");
		List<ProductStock> allProductStocks = productStockRepository.getAllProductStocks();
		LOGGER.info("Exit :: StockServiceimpl :: getAllProductStock():" + allProductStocks);
		return allProductStocks;
	}

	@Override
	public String addProductStock(ProductStockRequest productStockRequest) {
		LOGGER.info("Entry :: StockServiceimpl :: addProductStock():" + productStockRequest);
		ProductStock stock = new ProductStock();

		Optional<ProductStock> optional = productStockRepository.getExistingStock(productStockRequest.getProductName());
		if (optional.isPresent()) {
			stock = optional.get();
			stock.setQuantity(stock.getQuantity() + productStockRequest.getQuantity());
			productStockRepository.save(stock);
			LOGGER.info("Exit :: StockServiceimpl :: addProductStock():" + ServiceConstants.STOCK_UPDATED);
			return ServiceConstants.STOCK_UPDATED;
		} else {
			BeanUtils.copyProperties(productStockRequest, stock);
			productStockRepository.save(stock);
			LOGGER.info("Exit :: StockServiceimpl :: addProductStock():" + ServiceConstants.STOCK_ADDED);
			return ServiceConstants.STOCK_ADDED;
		}
	}

	@Override
	public String editProductStock(ProductStockRequest productStockRequest) {
		LOGGER.info("Entry :: StockServiceimpl :: editProductStock():" + productStockRequest);
		Optional<ProductStock> optional = productStockRepository
				.findByProductName(productStockRequest.getProductName());

		ProductStock stock = optional.get();
		BeanUtils.copyProperties(productStockRequest, stock);
		productStockRepository.save(stock);
		LOGGER.info("Exit :: StockServiceimpl :: editProductStock():" + ServiceConstants.STOCK_EDITED);
		return ServiceConstants.STOCK_EDITED;

	}

	@Override
	public String deleteProductStock(Long productId) {
		LOGGER.info("Entry :: StockServiceimpl :: deleteProductStock():" + productId);
		Optional<ProductStock> optional = productStockRepository.findByProductId(productId);
		String response = null;
		if (optional.isPresent()) {
			ProductStock stock = optional.get();
			productStockRepository.deleteById(stock.getProductId());
			response = ServiceConstants.STOCK_DELETED;
		} else {
			response = ServiceConstants.SOME_THING_WENT_WRONG;
		}

		LOGGER.info("Exit :: StockServiceimpl :: deleteProductStock():" + response);
		return response;

	}
}
