package product.model.dao;

import java.io.FileReader;
import java.io.IOException;
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
	
	public ProductDao() {
		try {
			prop.load(new FileReader("resources/product-query.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

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
				String description = rset.getString("description");
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

	public Stock selectOne(Connection conn, String productId) {
		Stock stock = null;
		PreparedStatement pstmt = null;
		ResultSet rset =  null;
		
		String sql = prop.getProperty("selectOne");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, productId);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				stock = new Stock();
				stock.setProductId(rset.getString("product_id"));
				stock.setProductName(rset.getString("product_name"));
				stock.setPrice(rset.getInt("price"));
				stock.setDescription(rset.getString("description"));
				stock.setStock(rset.getInt("stock"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return stock;
	}

	public Stock selectName(Connection conn, String productName) {
		Stock stock = null;
		PreparedStatement pstmt =null;
		ResultSet rset = null;
		
		String query = prop.getProperty("insertName");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, productName);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				stock  = new Stock();
				stock.setProductId(rset.getString("product_id"));
				stock.setProductName(rset.getString("product_name"));
				stock.setPrice(rset.getInt("price"));
				stock.setDescription(rset.getString("description"));
				stock.setStock(rset.getInt("stock"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return stock;
	}

	public int insertProduct(Connection conn, Stock stock) {
		int s = 0;
		PreparedStatement pstmt = null;
		String query = prop.getProperty("insertProduct");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, stock.getProductId());
			pstmt.setString(2, stock.getProductName());
			pstmt.setInt(3, stock.getPrice());
			pstmt.setString(4, stock.getDescription());
			pstmt.setInt(5, stock.getStock());
			
			s = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return s;
	}

	public int updateProduct(Connection conn, Stock s) {
		int result = 0;
		PreparedStatement pstmt = null;
		
		String query = prop.getProperty("updateProduct");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, s.getProductName());
			pstmt.setInt(2, s.getPrice());
			pstmt.setString(3, s.getDescription());
			pstmt.setString(4, s.getProductId());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}

	public int deleteProduct(Connection conn, String productId) {
		int result = 0;
		PreparedStatement pstmt = null;
		
		String query = prop.getProperty("deleteProduct");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, productId);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}



}
