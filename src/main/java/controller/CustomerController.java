package controller;

import model.Customer;

public class CustomerController {
    private Customer customer;

    public CustomerController() {}

    public void setCustomer(String username, String hashedPassword) {
        customer = new Customer(username, hashedPassword);
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
