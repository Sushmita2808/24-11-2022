package com.spring.core.jdbc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.spring.core.jdbc.model.Category;
import com.spring.core.jdbc.model.Product;

public class ProductDAOImpl implements ProductDAO{
	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	@Override
	public void save(Product product) {

		//select save operation
				String query = " insert products(id, name, price, unitsInStock , discontinued ) values (?, ?, ?,?,?)";
				
				JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
				
				Object[] values = new Object[] {
						product.getId(),
						product.getName(),
						product.getPrice(),
						product.getUnitsInStock() ,
						product.isDiscontinued()
					
				};
				
				int out = jdbcTemplate.update(query, values);
				

				if(out != 0)
					System.out.println("Product saved with id = " + product.getId());
				else
					System.out.println("Product save failed with id =" + product.getId());
				
		
	}

	@Override
	public Product getById(int id) {
		String query = "select id, name, price,unitsInStock , discontinued from products where id = ?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
					
		//using RowMapper anonymous class, we can create a separate RowMapper for reuse
		@SuppressWarnings("deprecation")
		Product prd = jdbcTemplate.queryForObject(query, 
				new Object[]{id}, new RowMapper<Product>(){

			@Override
			public Product mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				Product p1 = new Product();
				p1.setId(rs.getInt("id"));
				p1.setName(rs.getString("name"));
				p1.setPrice(rs.getDouble("price"));
				p1.setUnitsInStock(rs.getInt("unitsInStock"));
				p1.setDiscontinued(rs.getBoolean("discontinued"));
				return p1;
			}});
		
		return prd;
	}

	@Override
	public void update(Product product) {
		//update operation
		String query = "update products set name=?, price=?,unitsInStock =?, discontinued=? where id=?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		Object[] args = new Object[] {
				product.getName(),
				product.getPrice(),
				product.getUnitsInStock() ,
				product.isDiscontinued(),
				product.getId()
				
		};
		
		int out = jdbcTemplate.update(query, args);
		
		if(out !=0){
			System.out.println("Product data updated with id="+product.getId());
		}else System.out.println("No product data is found with id="+product.getId());
		
	}
		

	@Override
	public void deleteById(int id) {
		//delete operation
		String query = "delete from products where id=?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		int out = jdbcTemplate.update(query, id);
		
		if(out !=0){
			System.out.println("Product data deleted with id="+id);
		}
		else 
			System.out.println("No product data is found with id="+id);
		
	}

	@Override
	public List<Product> getAll() {
		//selecting multiple products
		String query = "select * from products";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		List<Product> prdList = new ArrayList<Product>();

		List<Map<String,Object>> prdRows = jdbcTemplate.queryForList(query);
		
		for(Map<String,Object> prdRow : prdRows){
			Product prd = new Product();
			
			prd.setId(Integer.parseInt(String.valueOf(prdRow.get("id"))));
			prd.setName(String.valueOf(prdRow.get("name")));
			prd.setPrice(Double.parseDouble(String.valueOf(prdRow.get("price"))));
			prd.setUnitsInStock(Integer.parseInt(String.valueOf(prdRow.get("unitsInStock"))));
			prd.setDiscontinued(Boolean.parseBoolean(String.valueOf(prdRow.get("discontinued"))));
			prdList.add(prd);
		}
		return prdList;
	}

	@Override
	public Product getByName(String name) {
		//display by name
		String query = "select * from products where name = ?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
					
		//using RowMapper anonymous class, we can create a separate RowMapper for reuse
		@SuppressWarnings("deprecation")
		Product prd = jdbcTemplate.queryForObject(query, 
				new Object[]{name}, new RowMapper<Product>(){

			@Override
			public Product mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				Product p1 = new Product();
				p1.setId(rs.getInt("id"));
				p1.setName(rs.getString("name"));
				p1.setPrice(rs.getDouble("price"));
				p1.setUnitsInStock(rs.getInt("unitsInStock"));
				p1.setDiscontinued(rs.getBoolean("discontinued"));
				return p1;
			}});
		
		return prd;
	}
	@Override
	public List<Product> getByNames(String substring) {
		//selecting multiple products
				String query = "select * from products where name like 'Toy%'";
				
				JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
				
				List<Product> prdList = new ArrayList<Product>();

				List<Map<String,Object>> prdRows = jdbcTemplate.queryForList(query);
				
				for(Map<String,Object> prdRow : prdRows){
					Product prd = new Product();
					
					prd.setId(Integer.parseInt(String.valueOf(prdRow.get("id"))));
					prd.setName(String.valueOf(prdRow.get("name")));
					prd.setPrice(Double.parseDouble(String.valueOf(prdRow.get("price"))));
					prd.setUnitsInStock(Integer.parseInt(String.valueOf(prdRow.get("unitsInStock"))));
					prd.setDiscontinued(Boolean.parseBoolean(String.valueOf(prdRow.get("discontinued"))));
					prdList.add(prd);
		}
				return prdList;
		}
	@Override
	public List<Product> getByBetweenPrice(double iPrice, double oPrice) {
		//price operation
		String query = "select * from products where price between 300.00 and 999.8";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		List<Product> prdList = new ArrayList<Product>();

		List<Map<String,Object>> prdRows = jdbcTemplate.queryForList(query);
		
		for(Map<String,Object> prdRow : prdRows){
			Product prd = new Product();
			
			prd.setId(Integer.parseInt(String.valueOf(prdRow.get("id"))));
			prd.setName(String.valueOf(prdRow.get("name")));
			prd.setPrice(Double.parseDouble(String.valueOf(prdRow.get("price"))));
			prd.setUnitsInStock(Integer.parseInt(String.valueOf(prdRow.get("unitsInStock"))));
			prd.setDiscontinued(Boolean.parseBoolean(String.valueOf(prdRow.get("discontinued"))));
			prdList.add(prd);
		}
		return prdList;
	}
	@Override
	public List<Product> getDiscontinuedProducts() {
		//Discontinued operation
				String query = "select * from products where discontinued = true";
				
				JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
				
				List<Product> prdList = new ArrayList<Product>();

				List<Map<String,Object>> prdRows = jdbcTemplate.queryForList(query);
				
				for(Map<String,Object> prdRow : prdRows){
					Product prd = new Product();
					
					prd.setId(Integer.parseInt(String.valueOf(prdRow.get("id"))));
					prd.setName(String.valueOf(prdRow.get("name")));
					prd.setPrice(Double.parseDouble(String.valueOf(prdRow.get("price"))));
					prd.setUnitsInStock(Integer.parseInt(String.valueOf(prdRow.get("unitsInStock"))));
					prd.setDiscontinued(Boolean.parseBoolean(String.valueOf(prdRow.get("discontinued"))));
					prdList.add(prd);
				}
				return prdList;
			}

}
