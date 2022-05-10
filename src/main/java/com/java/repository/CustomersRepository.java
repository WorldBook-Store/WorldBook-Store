package com.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.java.entity.Customer;

@Repository
public interface CustomersRepository extends JpaRepository<Customer, String>{
	
	@Query(value = "select * from customers where customers.customerId = ?", nativeQuery = true)
    public Customer findCustomersLogin(String customerId);
	
	@Query(value = "SELECT * FROM customers c WHERE c.customerId = :id", nativeQuery = true)
	public Customer getCustomerByID(@Param("id") String id);
	
	@Query(value = "UPDATE customers c SET c.email = :email, c.fullname = :fullname  WHERE c.customerId = :customerId", nativeQuery = true)
	public void updateCustomer(@Param("email") String email, @Param("fullname") String fullname, @Param("customerId") String customerId);

}
