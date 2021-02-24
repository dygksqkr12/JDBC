package product.controller;

import java.util.List;

import product.model.exception.ProductException;
import product.model.service.IOService;
import product.model.vo.IO;
import product.model.vo.Stock;

public class IOController {
	private IOService ioService = new IOService();

	public List<IO> selectAllIO() {
		List<IO> list = null;
		try {
			list = ioService.selectAllIO();
		} catch (ProductException e) {
		}
		return list;
	}

	public int insertIO(String productId, int num) {
		int result = 0;
		try {
			result = ioService.insertIO(productId, num);
		} catch (ProductException e) {
		}
		return result;
	}

	public int outIO(String productId, int num) {
		int result = 0;
		try {
			result = ioService.outIO(productId, num);
		} catch (ProductException e) {
		}
		return result;
	}


}
