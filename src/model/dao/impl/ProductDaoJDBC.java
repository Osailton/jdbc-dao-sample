package model.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import connection.DBConnection;
import exceptions.DBException;
import model.dao.ProductDao;
import model.entities.Product;
import model.entities.ProductType;

public class ProductDaoJDBC implements ProductDao {
	
	private Connection conn;
	
	public ProductDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Product prod) {
		PreparedStatement pSt = null;
		
		try {
			pSt = conn.prepareStatement(
						"INSERT INTO product "
						+ "(name, base_price, entry_date, product_type) "
						+ "VALUES "
						+ "(?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			
			pSt.setString(1, prod.getName());
			pSt.setDouble(2, prod.getBasePrice());
			pSt.setDate(3, new Date(prod.getEntryDate().getTime()));
			pSt.setInt(4, prod.getProductType().getId());
			
			int rows = pSt.executeUpdate();
			
			if (rows > 0) {
				ResultSet rs = pSt.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					prod.setId(id);
				}
				DBConnection.closeResultSet(rs);
			}
			else {
				throw new DBException("Unexpected error! The insert could not be done!");
			}
			
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		}
		finally {
			DBConnection.closeStatement(pSt);
		}
	}

	@Override
	public void update(Product prod) {
		PreparedStatement pSt = null;
		
		try {
			pSt = conn.prepareStatement(
						"UPDATE product "
						+ "SET name = ?, base_price = ?, product_type = ? "
						+ "WHERE id = ?",
					Statement.RETURN_GENERATED_KEYS);
			
			pSt.setString(1, prod.getName());
			pSt.setDouble(2, prod.getBasePrice());
			pSt.setInt(3, prod.getProductType().getId());
			pSt.setInt(4, prod.getId());
			
			pSt.executeUpdate();
			
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		}
		finally {
			DBConnection.closeStatement(pSt);
		}
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement pSt = null;
		
		try {
			pSt = conn.prepareStatement(
						"DELETE FROM product "
						+ "WHERE id = ?"
					);
			
			pSt.setInt(1, id);
			
			pSt.executeUpdate();
			
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		}
		finally {
			DBConnection.closeStatement(pSt);
		}
	}

	@Override
	public Product findById(Integer id) {
		PreparedStatement pSt = null;
		ResultSet rs = null;
		
		try {
			
			pSt = conn.prepareStatement(
					"SELECT product.*, product_type.name as Type "
					+ "FROM product INNER JOIN product_type "
					+ "ON product.product_type = product_type.id "
					+ "WHERE product.id = ?"
				);
			
			pSt.setInt(1, id);
			rs = pSt.executeQuery();
			
			if (rs.next()) {
				ProductType type = productTypeToObj(rs);
				Product prod = productToObj(rs, type);
				return prod;
			}
			
			return null;
			
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		}
		finally {
			DBConnection.closeStatement(pSt);
			DBConnection.closeResultSet(rs);
		}
	}

	@Override
	public List<Product> findByType(ProductType productType) {
		PreparedStatement pSt = null;
		ResultSet rs = null;
		
		try {
			
			pSt = conn.prepareStatement(
					"SELECT product.*, product_type.name as Type "
					+ "FROM product INNER JOIN product_type "
					+ "ON product.product_type = product_type.id "
					+ "WHERE product_type = ? "
					+ "ORDER BY name"
				);
			
			pSt.setInt(1, productType.getId());
			rs = pSt.executeQuery();
			
			List<Product> results = new ArrayList<>();
			Map<Integer, ProductType> mapTypes = new HashMap<>();
			while (rs.next()) {
				
				ProductType type = mapTypes.get(rs.getInt("product_type"));
				
				if (type == null) {
					type = productTypeToObj(rs);
					mapTypes.put(rs.getInt("product_type"), type);
				}
				
				Product prod = productToObj(rs, type);
				results.add(prod);
			}
			
			return results;
			
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		}
		finally {
			DBConnection.closeStatement(pSt);
			DBConnection.closeResultSet(rs);
		}
	}

	@Override
	public List<Product> findAll() {
		PreparedStatement pSt = null;
		ResultSet rs = null;
		
		try {
			
			pSt = conn.prepareStatement(
					"SELECT product.*, product_type.name as Type "
					+ "FROM product INNER JOIN product_type "
					+ "ON product.product_type = product_type.id "
					+ "ORDER BY name"
				);
			
			rs = pSt.executeQuery();
			
			List<Product> results = new ArrayList<>();
			Map<Integer, ProductType> mapTypes = new HashMap<>();
			while (rs.next()) {
				
				ProductType type = mapTypes.get(rs.getInt("product_type"));
				
				if (type == null) {
					type = productTypeToObj(rs);
					mapTypes.put(rs.getInt("product_type"), type);
				}
				
				Product prod = productToObj(rs, type);
				results.add(prod);
			}
			
			return results;
			
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		}
		finally {
			DBConnection.closeStatement(pSt);
			DBConnection.closeResultSet(rs);
		}
	}
	
	private Product productToObj(ResultSet rs, ProductType type) throws SQLException {
					
		Product prod = new Product();
		prod.setId(rs.getInt("id"));
		prod.setName(rs.getString("name"));
		prod.setEntryDate(rs.getDate("entry_date"));
		prod.setBasePrice(rs.getDouble("base_price"));
		prod.setProductType(type);
			
		return prod;
		
	}
	
	private ProductType productTypeToObj(ResultSet rs) throws SQLException {
		
		ProductType type = new ProductType();
		type.setId(rs.getInt("product_type"));
		type.setName(rs.getString("Type"));
		
		return type;
		
	}
}
