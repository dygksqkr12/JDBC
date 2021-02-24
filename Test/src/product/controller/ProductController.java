package product.controller;

import java.util.List;

import product.model.exception.ProductException;
import product.model.service.ProductService;
import product.model.vo.Stock;

public class ProductController {
	private ProductService productService = new ProductService();

	public List<Stock> selectAll() {
		List<Stock> list = null;
		try {
			list = productService.selectAll();
		} catch (ProductException e) {
			
		}
		return list;
	}

	public Stock selectOne(String productId) {
		Stock stock = null;
		try {
			stock = productService.selectOne(productId);
		} catch (ProductException e) {
		}
		return stock;
	}

	public Stock selectName(String productName) {
		Stock stock = null;
		try {
			stock = productService.selectName(productName);
		} catch (ProductException e) {
		}
		return stock;
	}

	public int insertProduct(Stock stock) {
		int s = 0;
		try {
			s = productService.insertProduct(stock);
		} catch (ProductException e) {
		}
		return s;
	}

	public int updateProduct(Stock s) {
		int result = 0;
		try {
			result = productService.updateProduct(s);
		} catch (ProductException e) {
		}
		return result;
	}

	public int deleteProduct(String productId) {
		int result = 0;
		try {
			result = productService.deleteProduct(productId);
		} catch (ProductException  e) {
		}
		return result;
	}


}
