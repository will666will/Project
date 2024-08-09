package user;

/**
 * The Customer class represents a customer in the ticket management system.
 * It includes the customer's ID, name, and password.
 *
 * @version 1.0
 */
public class Customer {

    /**
     * The unique ID of the customer.
     */
    private String customerId;

    /**
     * The name of the customer.
     */
    private String customerName;

    /**
     * The password of the customer.
     */
    private String password;

    /**
     * Constructs a new Customer with the specified ID, name, and password.
     *
     * @param customerId   the unique ID of the customer
     * @param customerName the name of the customer
     * @param password     the password of the customer
     */
    public Customer(String customerId, String customerName, String password) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.password = password;
    }

    /**
     * Returns the unique ID of the customer.
     *
     * @return the unique ID of the customer
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * Returns the name of the customer.
     *
     * @return the name of the customer
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Returns the password of the customer.
     *
     * @return the password of the customer
     */
    public String getPassword() {
        return password;
    }
}
