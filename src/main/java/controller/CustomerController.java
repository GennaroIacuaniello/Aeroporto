package controller;

import model.Customer;

public class CustomerController {
    private Customer customer;

    public CustomerController() {}

    public void setCustomer(String username, char[] password) {
        customer = new Customer(username, password);
    }
}
