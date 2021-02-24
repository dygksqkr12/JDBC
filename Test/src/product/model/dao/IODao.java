package product.model.dao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import product.model.exception.ProductException;
import product.model.vo.IO;
import product.model.vo.Stock;

import static common.JDBCTemplate.*;

public class IODao {
	private Properties prop = new Properties();
	
	public IODao() {
		try {
			prop.load(new FileReader("resources/product-query.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public List<IO> selectAllIO(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectAllIO");
		List<IO> list = null;
		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			list = new ArrayList<IO>();
			while (rset.next()) {
				int ioNo = rset.getInt("io_no");
				String productId = rset.getString("product_id");
				Date ioDate = rset.getDate("iodate");
				int amount = rset.getInt("amount");
				String status = rset.getString("status");
				IO IOStock = new IO(ioNo, productId, ioDate, amount, status);
				list.add(IOStock);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return list;
	}

	public int insertIO(Connection conn, String productId, int num) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertIO");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, productId);
			pstmt.setInt(2, num);
			pstmt.setString(3, "I");
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new ProductException("SQL예외발생 in Dao",e);
		} finally {
			close(pstmt);
		}
		return result;
	}

	public int outIO(Connection conn, String productId, int num) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertIO");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, productId);
			pstmt.setInt(2, num);
			pstmt.setString(3, "O");
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new ProductException("SQL예외발생 in Dao",e);
		} finally {
			close(pstmt);
		}
		return result;
	}


	

}
