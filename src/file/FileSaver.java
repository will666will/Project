package file;

import user.Customer;
import booking.Booking;
import concert.Concert;
import ticket.Ticket;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * The FileSaver class is responsible for saving the data of customers, concerts, and bookings to their respective files.
 * It extends the FileOperator class to utilize the common file path and data list functionalities.
 *
 * @version 1.0
 */
public class FileSaver extends FileOperator {

    /**
     * Constructs a FileSaver with the specified file paths and data lists.
     *
     * @param customerFilePath the file path for customer data
     * @param customers the list of customers to be saved
     * @param concertFilePath the file path for concert data
     * @param concerts the list of concerts to be saved
     * @param bookingFilePath the file path for booking data
     * @param bookings the list of bookings to be saved
     */
    public FileSaver(String customerFilePath, List<Customer> customers, String concertFilePath, List<Concert> concerts, String bookingFilePath, List<Booking> bookings) {
        super(customerFilePath, customers, concertFilePath, concerts, bookingFilePath, bookings);
    }

    /**
     * Saves the customer, concert, and booking data to their respective files.
     *
     * @throws IOException if an I/O error occurs during saving
     */
    public void save() throws IOException {
        saveConcerts();
        saveBookings();
        saveCustomers();
    }

    /**
     * Saves the customer data to the customer file.
     *
     * @throws IOException if an I/O error occurs during saving
     */
    private void saveCustomers() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(customerFilePath))) {
            for (Customer customer : customers) {
                writer.write(customer.getCustomerId() + "," +
                        customer.getCustomerName() + "," +
                        customer.getPassword() + "\n");
            }
        }
    }

    /**
     * Saves the concert data to the concert file.
     *
     * @throws IOException if an I/O error occurs during saving
     */
    private void saveConcerts() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(concertFilePath))) {
            for (Concert concert : concerts) {
                writer.write(concert.getConcertId() + "," +
                        concert.getDate() + "," +
                        concert.getTiming() + "," +
                        concert.getArtistName() + "," +
                        concert.getVenueName() + "," +
                        "STANDING:" + concert.getStandingPrice()[0] + ":" + concert.getStandingPrice()[1] + ":" + concert.getStandingPrice()[2] + "," +
                        "SEATING:" + concert.getSeatingPrice()[0] + ":" + concert.getSeatingPrice()[1] + ":" + concert.getSeatingPrice()[2] + "," +
                        "VIP:" + concert.getVipPrice()[0] + ":" + concert.getVipPrice()[1] + ":" + concert.getVipPrice()[2] + "\n");
            }
        }
    }

    /**
     * Saves the booking data to the booking file.
     *
     * @throws IOException if an I/O error occurs during saving
     */
    private void saveBookings() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(bookingFilePath))) {
            for (Booking booking : bookings) {
                String bookingInfo = booking.getBookingId() + "," +
                        booking.getCustomerId() + "," +
                        booking.getCustomerName() + "," +
                        booking.getConcertId() + "," +
                        booking.getTotalTickets();

                for (Ticket ticket : booking.getTickets()) {
                    bookingInfo += "," + ticket.getTicketId() + "," +
                            ticket.getRowNumber() + "," +
                            ticket.getSeatNumber() + "," +
                            ticket.getZoneType() + "," +
                            ticket.getPrice();
                }

                writer.write(bookingInfo + "\n");
            }
        }
    }
}
