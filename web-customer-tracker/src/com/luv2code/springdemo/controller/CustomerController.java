package com.luv2code.springdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.luv2code.springdemo.entity.Customer;
import com.luv2code.springdemo.service.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController {

	// need to inject the customer service
	@Autowired
	private CustomerService customerService;

	// @RequestMapping("/list")
	@GetMapping("/list")
	public String listCustomer(Model theModel) {

		// get the customers from service
		List<Customer> customers = customerService.getCustomers();

		// add the customer to the model
		theModel.addAttribute("customers", customers);

		return "list-customers";
	}

	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {

		// create model attribute to bind form data
		Customer customer = new Customer();

		// add the customer to the model
		theModel.addAttribute("customer", customer);

		return "customer-form";
	}

	@PostMapping("/saveCustomer")
	public String saveCustomer(@ModelAttribute("customer") Customer customer) {

		// save the customer using service
		customerService.saveCustomer(customer);

		return "redirect:/customer/list";
	}

	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("customerId") int id, Model theModel) {

		// get the customer from the service
		Customer customer = customerService.getCustomer(id);

		// set the customer as model attribute to pre-populate the form
		theModel.addAttribute("customer", customer);

		// send over to form
		return "customer-form";
	}

	@GetMapping("/delete/{customerId}")
	public String deleteCustomer(@PathVariable("customerId") int id) {

		// delete the customer
		customerService.deleteCustomer(id);

		return "redirect:/customer/list";
	}

	@PostMapping("/search")
	public String searchCustomer(@RequestParam("searchName") String name, Model theModel) {

		List<Customer> customers = customerService.searchCustomerByName(name);

		theModel.addAttribute("customers", customers);

		return "list-customers";
	}
}
