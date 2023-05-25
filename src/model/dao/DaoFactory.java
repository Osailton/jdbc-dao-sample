package model.dao;

import connection.DBConnection;
import model.dao.impl.ProductDaoJDBC;

// This class is a sample of the Factory pattern
public class DaoFactory {
	
	public static ProductDao createProductDao() {
		return new ProductDaoJDBC(DBConnection.getConnection());
	}

}
