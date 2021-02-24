package product.model.service;

import java.sql.Connection;
import java.util.List;

import product.model.dao.IODao;
import product.model.vo.IO;
import product.model.vo.Stock;

import static common.JDBCTemplate.*;

public class IOService {
	private IODao ioDao = new IODao();

	public List<IO> selectAllIO() {
		Connection  conn = getConnection();
		List<IO> list = ioDao.selectAllIO(conn);
		close(conn);
		return list;
	}

	public int insertIO(String productId, int num) {
		Connection conn = getConnection();
		int result = ioDao.insertIO(conn, productId, num);
		close(conn);
		return result;
	}

	public int outIO(String productId, int num) {
		Connection conn = getConnection();
		int result = ioDao.outIO(conn, productId, num);
		close(conn);
		return result;
	}


}
