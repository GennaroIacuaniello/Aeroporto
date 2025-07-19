package controller;

import model.Customer;

public class CustomerController {
    private int loggedCustomerId;
    private Customer loggedCustomer;

    public CustomerController() {}

    public void setCustomer(String username, String hashedPassword, int id) {
        loggedCustomer = new Customer(username, hashedPassword);
        loggedCustomerId = id;
    }

    public void setLoggedCustomer(Customer loggedCustomer) {
        this.loggedCustomer = loggedCustomer;
    }

    public Customer getLoggedUser() {
        return loggedCustomer;
    }

    public int getLoggedCustomerId() {
        return loggedCustomerId;
    }
}
