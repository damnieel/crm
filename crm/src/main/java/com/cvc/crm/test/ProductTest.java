package com.cvc.crm.test;

import java.sql.SQLException;

import org.junit.Test;

import com.cvc.crm.model.Product;
import com.cvc.crm.service.ProductService;
import com.cvc.crm.util.Util;



public class ProductTest {
	private ProductService productService = new ProductService();
	
	@Test
	public void testInsert() throws SQLException {
		for(int i =0 ;i<30;i++){
		Product p = new Product();
			p.setId(String.valueOf(Util.id()));
			p.setName("白色雕花实木沙发");
			p.setCategory("实木沙发");
			p.setStyle("简约现代");
			p.setMaterial("实木");
			p.setColor("白色");
			p.setModel("DM2018101601");
			p.setImage("/image/20131215");
			p.setPrice(3200.00);
			p.setStatus("1");
			p.setDesc("价格实惠");;
			p.setCreated(Util.datetime());
			p.setUpdated(Util.datetime());
			System.out.println(p);
			int result = productService.save(p);
		    System.out.println(result);
		}
	}
	@Test
	public void testDelete() throws SQLException {
		int result = productService.delete("153965635893800000");
		System.out.println(result);
	}
	@Test
	public void testUpdate() {
		
	}
	@Test
	public void testSelect() {
		
	}
}
