package model.dao;

import java.util.List;

import model.entities.Product;
import model.entities.ProductType;

public interface ProductDao {
	
	void insert(Product obj);
	void update(Product obj);
	void deleteById(Integer id);
	Product findById(Integer id);
	List<Product> findAll();
	List<Product> findByType(ProductType productType);

}
