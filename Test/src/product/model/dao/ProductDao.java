package product.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import product.model.exception.ProductException;
import product.model.vo.Stock;

import static common.JDBCTemplate.*;

public class ProductDao {
	
	private Properties prop = new Properties();

	public List<Stock> selectAll(Connection conn) {
		PreparedStatement pstmt =null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectAll");
		List<Stock> list = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			list = new ArrayList<Stock>();
			while (rset.next()) {
				String productId = rset.getString("product_id");
				String productName = rset.getString("product_name");
				int price = rset.getInt("price");
				String description = rset.getString("dsecription");
				int stock = rset.getInt("stock");
				Stock pstock = new Stock(productId, productName, price, description, stock);
				list.add(pstock);
			}
		} catch (SQLException e) {
			throw new ProductException("회원 전체 조회", e);
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	}


}
