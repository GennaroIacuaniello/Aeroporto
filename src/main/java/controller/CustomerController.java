package controller;

import model.Customer;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Customer controller.
 */
public class CustomerController {
    private Integer loggedCustomerId;
    private Customer loggedCustomer;
    private ArrayList<Customer> searchBookingResultCustomers;
    private ArrayList<Integer> searchBookingResultCustomersIds;

    /**
     * Sets logged customer.
     *
     * @param username       the username
     * @param mail           the mail
     * @param hashedPassword the hashed password
     * @param id             the id
     */
    public void setLoggedCustomer(String username, String mail, String hashedPassword, Integer id) {
        loggedCustomer = new Customer(username, mail, hashedPassword);
        loggedCustomerId = id;
    }

    /**
     * Sets logged customer.
     *
     * @param customer the customer
     * @param id       the id
     */
    public void setLoggedCustomer(Customer customer, Integer id) {
        loggedCustomer = customer;
        loggedCustomerId = id;
    }

    /**
     * Gets logged customer.
     *
     * @return the logged customer
     */
    public Customer getLoggedCustomer() {
        return loggedCustomer;
    }

    /**
     * Gets logged customer id.
     *
     * @return the logged customer id
     */
    public Integer getLoggedCustomerId() {
        return loggedCustomerId;
    }

    /**
     * Gets search booking result customers.
     *
     * @return the search booking result customers
     */
    public List<Customer> getSearchBookingResultCustomers() {
        return searchBookingResultCustomers;
    }

    /**
     * Sets search booking result customers.
     *
     * @param searchBookingResultCustomers the search booking result customers
     */
    public void setSearchBookingResultCustomers(List<Customer> searchBookingResultCustomers) {
        this.searchBookingResultCustomers = (ArrayList<Customer>) searchBookingResultCustomers;
    }

    /**
     * Gets search booking result customers ids.
     *
     * @return the search booking result customers ids
     */
    public List<Integer> getSearchBookingResultCustomersIds() {
        return searchBookingResultCustomersIds;
    }

    /**
     * Sets search booking result customers ids.
     *
     * @param searchBookingResultCustomersIds the search booking result customers ids
     */
    public void setSearchBookingResultCustomersIds(List<Integer> searchBookingResultCustomersIds) {
        this.searchBookingResultCustomersIds = (ArrayList<Integer>) searchBookingResultCustomersIds;
    }

    /**
     * Get search booking result customer by id customer.
     *
     * @param id the id
     * @return the customer
     */
    public Customer getSearchBookingResultCustomerById (Integer id){

        for(int i = 0; i < searchBookingResultCustomers.size(); i++){

            if(searchBookingResultCustomersIds.get(i).equals(id)){
                return searchBookingResultCustomers.get(i);
            }

        }
        return null;
    }
}
