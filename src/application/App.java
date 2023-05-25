package application;

import java.util.Date;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.ProductDao;
import model.entities.Product;
import model.entities.ProductType;

/**
 * @author Osailton
 * 
 * You must uncomment the code of the test you want to execute
 *
 */
public class App {
	
	public static void main(String[] args) {
		
		ProductDao productDao = DaoFactory.createProductDao();
		
//		System.out.println(" -- Testing ProductDao: insert -- ");
//		Product prodA = new Product(null, "Alcohol", new Date(), 22.82, new ProductType(2, null));
//		productDao.insert(prodA);
//		System.out.println(prodA);
//		System.out.println();
		
		System.out.println(" -- Testing ProductDao: deleteById -- ");
		productDao.deleteById(5);
		System.out.println();
		
		System.out.println(" -- Testing ProductDao: findById -- ");
		Product prodB = productDao.findById(1);
		System.out.println(prodB);
		System.out.println();
		
		System.out.println(" -- Testing ProductDao: findByType -- ");
		ProductType type = new ProductType(1, null);
		List<Product> listProdA = productDao.findByType(type);
		for (Product i : listProdA) {
			System.out.println(i);
		}
		System.out.println();
		
		System.out.println(" -- Testing ProductDao: findAll -- ");
		List<Product> listProdB = productDao.findAll();
		for (Product i : listProdB) {
			System.out.println(i);
		}
		System.out.println();
		
		System.out.println(" -- Testing ProductDao: updateById -- ");
		Product prodC = productDao.findById(4);
		prodC.setBasePrice(28.60);
		productDao.update(prodC);
		System.out.println(prodC);
		System.out.println();

	}

}
