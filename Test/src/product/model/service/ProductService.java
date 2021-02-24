package product.model.service;

import java.sql.Connection;
import java.util.List;

import product.model.dao.ProductDao;
import product.model.vo.Stock;

import static common.JDBCTemplate.*;

public class ProductService {
	private ProductDao productDao = new ProductDao();

	public List<Stock> selectAll() {
		Connection conn = getConnection();
		List<Stock> list = productDao.selectAll(conn);
		close(conn);
		
		return list;
	}

	public Stock selectOne(String productId) {
		Connection conn = getConnection();
		Stock stock = productDao.selectOne(conn, productId);
		close(conn);
		return stock;
	}

	public Stock selectName(String productName) {
		Connection conn = getConnection();
		Stock stock = productDao.selectName(conn, productName);
		close(conn);
		return stock;
	}

	public int insertProduct(Stock stock) {
		Connection conn = getConnection();
		int s = productDao.insertProduct(conn, stock);
		close(conn);
		return s;
	}

	public int updateProduct(Stock s) {
		Connection conn = getConnection();
		int result = productDao.updateProduct(conn, s);
		close(conn);
		return result;
	}

	public int deleteProduct(String productId) {
		Connection conn = getConnection();
		int result = productDao.deleteProduct(conn, productId);
		close(conn);
		return result;
	}


}
