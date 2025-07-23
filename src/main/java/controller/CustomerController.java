package controller;

import model.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerController {
    private Integer loggedCustomerId;
    private Customer loggedCustomer;
    private ArrayList<Customer> searchBookingResultCustomers;
    private ArrayList<Integer> searchBookingResultCustomersIds;

    public void setLoggedCustomer(String username, String mail, String hashedPassword, Integer id) {
        loggedCustomer = new Customer(username, mail, hashedPassword);
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

    public List<Customer> getSearchBookingResultCustomers() {
        return searchBookingResultCustomers;
    }

    public void setSearchBookingResultCustomers(List<Customer> searchBookingResultCustomers) {
        this.searchBookingResultCustomers = (ArrayList<Customer>) searchBookingResultCustomers;
    }

    public List<Integer> getSearchBookingResultCustomersIds() {
        return searchBookingResultCustomersIds;
    }

    public void setSearchBookingResultCustomersIds(List<Integer> searchBookingResultCustomersIds) {
        this.searchBookingResultCustomersIds = (ArrayList<Integer>) searchBookingResultCustomersIds;
    }

    public Customer getSearchBookingResultCustomerById (Integer id){

        for(int i = 0; i < searchBookingResultCustomers.size(); i++){

            if(searchBookingResultCustomersIds.get(i).equals(id)){
                return searchBookingResultCustomers.get(i);
            }

        }
        return null;
    }
}
