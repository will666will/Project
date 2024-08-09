package ticket;

/**
 * The Ticket class represents a ticket in the ticket management system.
 * It includes the ticket's ID, row number, seat number, zone type, and price.
 *
 * @version 1.0
 */
public class Ticket {

    /**
     * The unique ID of the ticket.
     */
    private int ticketId;

    /**
     * The row number where the ticket is located.
     */
    private int rowNumber;

    /**
     * The seat number where the ticket is located.
     */
    private int seatNumber;

    /**
     * The type of zone where the ticket is located (e.g., VIP, SEATING, STANDING).
     */
    private String zoneType;

    /**
     * The price of the ticket.
     */
    private double price;

    /**
     * Constructs a new Ticket with the specified ID, row number, seat number, zone type, and price.
     *
     * @param ticketId   the unique ID of the ticket
     * @param rowNumber  the row number where the ticket is located
     * @param seatNumber the seat number where the ticket is located
     * @param zoneType   the type of zone where the ticket is located
     * @param price      the price of the ticket
     */
    public Ticket(int ticketId, int rowNumber, int seatNumber, String zoneType, double price) {
        this.ticketId = ticketId;
        this.rowNumber = rowNumber;
        this.seatNumber = seatNumber;
        this.zoneType = zoneType;
        this.price = price;
    }

    /**
     * Returns the unique ID of the ticket.
     *
     * @return the unique ID of the ticket
     */
    public int getTicketId() {
        return ticketId;
    }

    /**
     * Returns the row number where the ticket is located.
     *
     * @return the row number where the ticket is located
     */
    public int getRowNumber() {
        return rowNumber;
    }

    /**
     * Returns the seat number where the ticket is located.
     *
     * @return the seat number where the ticket is located
     */
    public int getSeatNumber() {
        return seatNumber;
    }

    /**
     * Returns the type of zone where the ticket is located.
     *
     * @return the type of zone where the ticket is located
     */
    public String getZoneType() {
        return zoneType;
    }

    /**
     * Returns the price of the ticket.
     *
     * @return the price of the ticket
     */
    public double getPrice() {
        return price;
    }
}
