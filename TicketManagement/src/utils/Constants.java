package utils;

import java.util.Scanner;

/**
 * The Constants class holds all the constant values used across the Ticket Management System.
 * These constants include command-line arguments, file paths, keyboard input, and various static values for the system.
 *
 * @version 1.0
 */
public class Constants {

    /**
     * The prompt symbol for user input.
     */
    public final static String INPUT = "> ";

    /**
     * The default file path for the venue data.
     */
    public final static String DEFAULT_VENUE_FILE_PATH = "assets/venue_default.txt";

    /**
     * The scanner object for keyboard input.
     */
    public static final Scanner KEYBOARD = new Scanner(System.in);

    /**
     * The command-line argument for customer mode.
     */
    public static final String CUSTOMER_ARGUMENT = "--customer";

    /**
     * The command-line argument for admin mode.
     */
    public static final String ADMIN_ARGUMENT = "--admin";

    /**
     * The directory path for assets.
     */
    public static final String ASSETS = "assets";

    /**
     * The default identifier for the system.
     */
    public static final String DEFAULT = "default";

    /**
     * The message prompting the user to select a concert or exit.
     */
    public static final String SELECT_CONCERT = "Select a concert or 0 to exit";

    /**
     * The identifier for VIP seating.
     */
    public static final String VIP = "VIP";

    /**
     * The identifier for seating zones.
     */
    public static final String SEATING = "SEATING";

    /**
     * The identifier for standing zones.
     */
    public static final String STANDING = "STANDING";

    /**
     * The character representing VIP zone.
     */
    public static final char V = 'V';

    /**
     * The character representing seating zone.
     */
    public static final char S = 'S';

    /**
     * The character representing standing zone.
     */
    public static final char T = 'T';

    /**
     * The command for viewing concerts in admin mode.
     */
    public static final int ADMIN_VIEW_CONCERTS = 1;

    /**
     * The command for updating ticket prices in admin mode.
     */
    public static final int ADMIN_UPDATE_TICKET_PRICE = 2;

    /**
     * The command for viewing bookings in admin mode.
     */
    public static final int ADMIN_VIEW_BOOKINGS = 3;

    /**
     * The command for viewing total payments received for a concert in admin mode.
     */
    public static final int ADMIN_VIEW_TOTAL_PAYMENT = 4;

    /**
     * The command for exiting admin mode.
     */
    public static final int ADMIN_EXIT = 5;

    /**
     * The command for displaying ticket prices in concert mode.
     */
    public static final int CONCERT_DISPLAY_PRICE = 1;

    /**
     * The command for displaying the seating layout in concert mode.
     */
    public static final int CONCERT_DISPLAY_LAYOUT = 2;

    /**
     * The command for booking seats in concert mode.
     */
    public static final int CONCERT_BOOK_SEAT = 3;

    /**
     * The command for viewing booking details in concert mode.
     */
    public static final int CONCERT_VIEW_BOOKING_DETAILS = 4;

    /**
     * The command for exiting concert mode.
     */
    public static final int CONCERT_EXIT = 5;

    /**
     * The message displayed for invalid input.
     */
    public static final String INVALID_INPUT = "Invalid Input";

    /**
     * The message displaying the number of left seats.
     */
    public static final String LEFT_SEATS = "Left Seats:   ";

    /**
     * The message displaying the number of center seats.
     */
    public static final String CENTER_SEATS = "Center Seats: ";

    /**
     * The message displaying the number of right seats.
     */
    public static final String RIGHT_SEATS = "Right Seats:  ";
}
