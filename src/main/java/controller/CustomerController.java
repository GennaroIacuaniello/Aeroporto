package controller;

import model.Customer;

public class CustomerController {
    private Integer loggedCustomerId;
    private Customer loggedCustomer;

    public CustomerController() {}

    public void setLoggedCustomer(String username, String hashedPassword, int id) {
        loggedCustomer = new Customer(username, hashedPassword);
        loggedCustomerId = id;
    }

    public void setLoggedCustomer(Customer customer, Integer id) {
        loggedCustomer = customer;
        loggedCustomerId = id;
    }

    public Customer getLoggedCustomer() {
        return loggedCustomer;
    }

    public Integer getLoggedCustomerId() {
        return loggedCustomerId;
    }

}
