package controller;

import model.Customer;

public class CustomerController {
    private Customer customer;

    public CustomerController() {}

    public void setCustomer(String username, String password) {
        customer = new Customer(username, password);
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
