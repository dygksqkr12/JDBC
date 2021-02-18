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

}
