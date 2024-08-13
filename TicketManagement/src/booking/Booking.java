package booking;

import ticket.Ticket;

import java.util.List;

/**
 * The Booking class represents a booking made by a customer for a concert.
 * It includes the booking ID, customer ID, customer name, concert ID, total tickets, and a list of tickets.
 *
 * @version 1.0
 */
public class Booking {

    /**
     * The unique ID of the booking.
     */
    private String bookingId;

    /**
     * The ID of the customer who made the booking.
     */
    private String customerId;

    /**
     * The name of the customer who made the booking.
     */
    private String customerName;

    /**
     * The ID of the concert for which the booking was made.
     */
    private String concertId;

    /**
     * The total number of tickets booked.
     */
    private int totalTickets;

    /**
     * The list of tickets booked.
     */
    private List<Ticket> tickets;

    /**
     * Constructs a new Booking with the specified details.
     *
     * @param bookingId     the unique ID of the booking
     * @param customerId    the ID of the customer who made the booking
     * @param customerName  the name of the customer who made the booking
     * @param concertId     the ID of the concert for which the booking was made
     * @param totalTickets  the total number of tickets booked
     * @param tickets       the list of tickets booked
     */
    public Booking(String bookingId, String customerId, String customerName, String concertId, int totalTickets, List<Ticket> tickets) {
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.concertId = concertId;
        this.totalTickets = totalTickets;
        this.tickets = tickets;
    }

    /**
     * Returns the total price of all tickets booked.
     *
     * @return the total price of all tickets booked
     */
    public Double getTotalPrice() {
        Double sum = 0.0;
        for (Ticket ticket : tickets) {
            sum += ticket.getPrice();
        }
        return sum;
    }

    /**
     * Returns the unique ID of the booking.
     *
     * @return the unique ID of the booking
     */
    public String getBookingId() {
        return bookingId;
    }

    /**
     * Returns the ID of the customer who made the booking.
     *
     * @return the ID of the customer who made the booking
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * Returns the name of the customer who made the booking.
     *
     * @return the name of the customer who made the booking
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Returns the ID of the concert for which the booking was made.
     *
     * @return the ID of the concert for which the booking was made
     */
    public String getConcertId() {
        return concertId;
    }

    /**
     * Returns the total number of tickets booked.
     *
     * @return the total number of tickets booked
     */
    public int getTotalTickets() {
        return totalTickets;
    }

    /**
     * Returns the list of tickets booked.
     *
     * @return the list of tickets booked
     */
    public List<Ticket> getTickets() {
        return tickets;
    }
}
