package model.dao;

import java.util.List;

import model.entities.ProductType;

public interface ProductTypeDao {

	void insert(ProductType obj);
	void update(ProductType obj);
	void deleteById(Integer id);
	ProductType findById(Integer id);
	List<ProductType> findAll();
	
}
