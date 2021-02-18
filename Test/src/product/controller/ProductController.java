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

}
