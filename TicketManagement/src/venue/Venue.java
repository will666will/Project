package venue;

import ticket.Ticket;
import utils.Constants;

import java.util.List;

/**
 * The Venue class represents a venue where concerts are held.
 * It includes the number of rows in each zone (VIP, seating, standing) and the number of seats in each section (left, middle, right).
 * It also provides methods to get the total number of seats, print the seating layout, and check if a seat is booked.
 *
 * @version 1.0
 */
public class Venue {

    /**
     * The number of VIP rows.
     */
    private int vip;

    /**
     * The number of seating rows.
     */
    private int seating;

    /**
     * The number of standing rows.
     */
    private int standing;

    /**
     * The number of seats in the left section.
     */
    private int left;

    /**
     * The number of seats in the middle section.
     */
    private int middle;

    /**
     * The number of seats in the right section.
     */
    private int right;

    /**
     * Constructs a new Venue with the specified number of rows and seats in each section.
     *
     * @param vip     the number of VIP rows
     * @param seating the number of seating rows
     * @param standing the number of standing rows
     * @param left    the number of seats in the left section
     * @param middle  the number of seats in the middle section
     * @param right   the number of seats in the right section
     */
    public Venue(int vip, int seating, int standing, int left, int middle, int right) {
        this.vip = vip;
        this.seating = seating;
        this.standing = standing;
        this.left = left;
        this.middle = middle;
        this.right = right;
    }

    /**
     * Returns the total number of seats in the venue.
     *
     * @return the total number of seats in the venue
     */
    public int getTotalSeats() {
        return (vip + seating + standing) * (left + middle + right);
    }

    /**
     * Prints the seating layout of the venue, marking booked seats with [X].
     *
     * @param tickets the list of tickets to check for booked seats
     */
    public void print(List<Ticket> tickets) {
        for (int i = 0; i < vip; i++) {
            System.out.print("V" + (i + 1) + " ");
            for (int j = 0; j < left; j++) {
                if (booked(i, j, 0, tickets)) {
                    System.out.print("[X]");
                } else {
                    System.out.print("[" + (j + 1) + "]");
                }
            }
            System.out.print(" ");
            for (int j = left; j < left + middle; j++) {
                if (booked(i, j, 0, tickets)) {
                    System.out.print("[X]");
                } else {
                    System.out.print("[" + (j + 1) + "]");
                }
            }
            System.out.print(" ");
            for (int j = left + middle; j < left + middle + right; j++) {
                if (booked(i, j, 0, tickets)) {
                    System.out.print("[X]");
                } else {
                    System.out.print("[" + (j + 1) + "]");
                }
            }
            System.out.println(" V" + (i + 1));
        }
        System.out.print("\n");

        for (int i = 0; i < seating; i++) {
            System.out.print("S" + (i + 1) + " ");
            for (int j = 0; j < left; j++) {
                if (booked(i, j, 1, tickets)) {
                    System.out.print("[X]");
                } else {
                    System.out.print("[" + (j + 1) + "]");
                }
            }
            System.out.print(" ");
            for (int j = left; j < left + middle; j++) {
                if (booked(i, j, 1, tickets)) {
                    System.out.print("[X]");
                } else {
                    System.out.print("[" + (j + 1) + "]");
                }
            }
            System.out.print(" ");
            for (int j = left + middle; j < left + middle + right; j++) {
                if (booked(i, j, 1, tickets)) {
                    System.out.print("[X]");
                } else {
                    System.out.print("[" + (j + 1) + "]");
                }
            }
            System.out.println(" S" + (i + 1));
        }
        System.out.print("\n");

        for (int i = 0; i < standing; i++) {
            System.out.print("T" + (i + 1) + " ");
            for (int j = 0; j < left; j++) {
                if (booked(i, j, 2, tickets)) {
                    System.out.print("[X]");
                } else {
                    System.out.print("[" + (j + 1) + "]");
                }
            }
            System.out.print(" ");
            for (int j = left; j < left + middle; j++) {
                if (booked(i, j, 2, tickets)) {
                    System.out.print("[X]");
                } else {
                    System.out.print("[" + (j + 1) + "]");
                }
            }
            System.out.print(" ");
            for (int j = left + middle; j < left + middle + right; j++) {
                if (booked(i, j, 2, tickets)) {
                    System.out.print("[X]");
                } else {
                    System.out.print("[" + (j + 1) + "]");
                }
            }
            System.out.println(" T" + (i + 1));
        }
    }

    /**
     * Checks if a specific seat is booked.
     *
     * @param i the row index
     * @param j the seat index
     * @param type the type of the zone (0 for VIP, 1 for seating, 2 for standing)
     * @param tickets the list of tickets to check against
     * @return true if the seat is booked, false otherwise
     */
    private boolean booked(int i, int j, int type, List<Ticket> tickets) {
        for (Ticket ticket : tickets) {
            if ((type == 0 && ticket.getZoneType().equals(Constants.VIP)) || (type == 1 && ticket.getZoneType().equals(Constants.SEATING)) || (type == 2 && ticket.getZoneType().equals(Constants.STANDING))) {
                if (ticket.getRowNumber() == i + 1 && ticket.getSeatNumber() == j + 1)
                    return true;
            }
        }
        return false;
    }

    /**
     * Returns the number of VIP rows.
     *
     * @return the number of VIP rows
     */
    public int getVip() {
        return vip;
    }

    /**
     * Sets the number of VIP rows.
     *
     * @param vip the number of VIP rows
     */
    public void setVip(int vip) {
        this.vip = vip;
    }

    /**
     * Returns the number of seating rows.
     *
     * @return the number of seating rows
     */
    public int getSeating() {
        return seating;
    }

    /**
     * Sets the number of seating rows.
     *
     * @param seating the number of seating rows
     */
    public void setSeating(int seating) {
        this.seating = seating;
    }

    /**
     * Returns the number of seats in the left section.
     *
     * @return the number of seats in the left section
     */
    public int getLeft() {
        return left;
    }

    /**
     * Returns the number of seats in the middle section.
     *
     * @return the number of seats in the middle section
     */
    public int getMiddle() {
        return middle;
    }

    /**
     * Returns the number of seats in the right section.
     *
     * @return the number of seats in the right section
     */
    public int getRight() {
        return right;
    }
}
