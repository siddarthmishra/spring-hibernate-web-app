package com.luv2code.springdemo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.luv2code.springdemo.entity.Customer;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

	// need to inject the session factory
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Customer> getCustomers() {

		// get the current hibernate session
		Session session = sessionFactory.getCurrentSession();

		// create query ... enhancement sort by last name
		Query<Customer> query = session.createQuery("from Customer order by lastName", Customer.class);

		// execute query and get the result list
		List<Customer> customers = query.getResultList();

		// return the result
		return customers;
	}

	@Override
	public void saveCustomer(Customer customer) {

		// get the current hibernate session
		Session session = sessionFactory.getCurrentSession();

		// save/update the customer
		session.saveOrUpdate(customer);
	}

	@Override
	public Customer getCustomer(int id) {
		// get the current hibernate session
		Session session = sessionFactory.getCurrentSession();

		// get the customer and return it
		return session.get(Customer.class, id);
	}

	@Override
	public void deleteCustomer(int id) {
		// get the current hibernate session
		Session session = sessionFactory.getCurrentSession();

		// delete customer object with primary key
		Query query = session.createQuery("delete from Customer where id = :customerId");
		query.setParameter("customerId", id);

		query.executeUpdate();

	}

	@Override
	public List<Customer> searchCustomerByName(String name) {

		List<Customer> customers = null;

		// get the current hibernate session
		Session session = sessionFactory.getCurrentSession();
		
		Query<Customer> query = null;

		if (name != null && name.trim().length() > 0) {
			String stringQuery = "FROM Customer WHERE LOWER(firstName) LIKE :searchName OR LOWER(lastName) LIKE :searchName";
			query = session.createQuery(stringQuery, Customer.class);
			query.setParameter("searchName", "%" + name.toLowerCase() + "%");
		} else {
			query = session.createQuery("FROM Customer", Customer.class);
		}

		customers = query.getResultList();

		return customers;
	}

}
