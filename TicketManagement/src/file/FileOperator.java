package file;

import booking.Booking;
import concert.Concert;
import user.Customer;

import java.util.List;

/**
 * The FileOperator class is an abstract base class that provides common functionality
 * for handling file operations related to customers, concerts, and bookings.
 * It stores file paths and lists of data for these entities.
 *
 * @version 1.0
 */
public abstract class FileOperator {

    /**
     * The file path for customer data.
     */
    protected String customerFilePath;

    /**
     * The list of customers.
     */
    protected List<Customer> customers;

    /**
     * The file path for concert data.
     */
    protected String concertFilePath;

    /**
     * The list of concerts.
     */
    protected List<Concert> concerts;

    /**
     * The file path for booking data.
     */
    protected String bookingFilePath;

    /**
     * The list of bookings.
     */
    protected List<Booking> bookings;

    /**
     * Constructs a FileOperator with the specified file paths and data lists.
     *
     * @param customerFilePath the file path for customer data
     * @param customers the list of customers
     * @param concertFilePath the file path for concert data
     * @param concerts the list of concerts
     * @param bookingFilePath the file path for booking data
     * @param bookings the list of bookings
     */
    public FileOperator(String customerFilePath, List<Customer> customers, String concertFilePath, List<Concert> concerts, String bookingFilePath, List<Booking> bookings) {
        this.customerFilePath = customerFilePath;
        this.customers = customers;
        this.concertFilePath = concertFilePath;
        this.concerts = concerts;
        this.bookingFilePath = bookingFilePath;
        this.bookings = bookings;
    }
}
