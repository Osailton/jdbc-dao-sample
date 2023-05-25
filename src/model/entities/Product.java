package model.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Product implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String name;
	private Date entryDate;
	private Double basePrice;
	private ProductType productType;
	
	public Product() {
		
	}

	public Product(Integer id, String name, Date entryDate, Double basePrice, ProductType productType) {
		this.id = id;
		this.name = name;
		this.entryDate = entryDate;
		this.basePrice = basePrice;
		this.productType = productType;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public Double getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(Double basePrice) {
		this.basePrice = basePrice;
	}

	public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + "]";
	}
}
