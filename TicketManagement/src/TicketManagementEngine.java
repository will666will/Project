import booking.Booking;
import com.apple.eawt.Application;
import concert.Concert;
import user.Customer;
import exception.IncorrectPasswordException;
import exception.InvalidFormatException;
import exception.InvalidLineException;
import exception.NotFoundException;
import file.FileSaver;
import ticket.Ticket;
import venue.Venue;
import utils.Constants;

import java.io.*;
import java.util.*;
/**
 * The TicketManagementEngine class is responsible for managing the core functionality
 * of the ticket management system, including handling user authentication and managing
 * bookings, concerts, customers, and venues.
 *
 * @version 1.0
 */
public class TicketManagementEngine {



    /**
     * The username of the currently authenticated user.
     */
    private String userName;

    /**
     * The password of the currently authenticated user.
     */
    private String password;

    /**
     * The name of the currently authenticated customer.
     */
    private String customerName;

    /**
     * The file path for customer data.
     */
    private String customerFilePath;

    /**
     * The file path for concert data.
     */
    private String concertFilePath;

    /**
     * The file path for booking data.
     */
    private String bookingFilePath;

    /**
     * The file paths for venue data.
     */
    private List<String> venueFilePaths;

    /**
     * The list of bookings in the system.
     */
    private ArrayList<Booking> bookings;

    /**
     * The list of concerts in the system.
     */
    private ArrayList<Concert> concerts;

    /**
     * The list of customers in the system.
     */
    private ArrayList<Customer> customers;

    /**
     * The map of venue names to Venue objects.
     */
    private Map<String, Venue> venues;

    /**
     * The main method serves as the entry point for the application.
     *
     * @param args the command-line arguments
     * @throws IOException if an I/O error occurs
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // for(int i = 0; i < args.length; i ++ ) {
        //     System.out.print(args[i] + " ");
        // }
        // System.out.println();

        if (args.length == 0 || (!args[0].equals(Constants.CUSTOMER_ARGUMENT) && !args[0].equals(Constants.ADMIN_ARGUMENT))) {
            System.out.println("Invalid user mode. Terminating program now.");
            return;
        }

        TicketManagementEngine tme = new TicketManagementEngine();
        tme.venueFilePaths = new ArrayList<>();
        tme.bookings = new ArrayList<>();
        tme.concerts = new ArrayList<>();
        tme.customers = new ArrayList<>();
        tme.venues = new HashMap<>();
        tme.run(args);
    }

    /**
     * Runs the Ticket Management Engine with the provided arguments.
     *
     * @param args the command-line arguments
     * @throws IOException if an I/O error occurs
     */
    private void run(String[] args) throws IOException {
        if (args[0].equals(Constants.CUSTOMER_ARGUMENT)) {
            handleCustomerMode(args);
        } else if (args[0].equals(Constants.ADMIN_ARGUMENT)) {
            handleAdminMode(args);
        }
    }

    /**
     * Handles the customer mode, processing customer-related operations.
     *
     * @param args the command-line arguments
     * @throws IOException if an I/O error occurs
     */
    private void handleCustomerMode(String[] args) throws IOException {
        userName = "";
        password = "";

        // Start parsing from index 1
        int index = 1;
        if (args.length > 1 && !args[1].startsWith(Constants.ASSETS)) {
            userName = args[1];
            if (args.length > 2 && !args[2].startsWith(Constants.ASSETS)) {
                password = args[2];
                index = 3; // File paths start from index 3
            } else {
                index = 2; // File paths start from index 2
            }
        }

        if (args.length > index) {
            customerFilePath = args[index++];
            concertFilePath = args[index++];
            bookingFilePath = args[index++];
        } else {
            return;
        }

        // Collect all possible venue file paths
        while (index < args.length) {
            venueFilePaths.add(args[index]);
            index ++;
        }

        venueFilePaths.add(Constants.DEFAULT_VENUE_FILE_PATH);
        if(!loadData())  return;
        if (userName.equals("")) {
            System.out.print("Enter your name: ");
            userName = Constants.KEYBOARD.nextLine();
            System.out.print("Enter your password: ");
            password = Constants.KEYBOARD.nextLine();
            String newId = String.valueOf(Integer.parseInt(customers.get(customers.size() - 1).getCustomerId()) + 1);
            customers.add(new Customer(newId, userName, password));
            customerName = userName;
        }
        System.out.printf("Welcome %s to Ticket Management System\n", customerName);
        displayMessage();
        boolean flag = true;
        while (flag) {
            System.out.println(Constants.SELECT_CONCERT);
            viewConcerts();
            System.out.print(Constants.INPUT);
            int cmd = Constants.KEYBOARD.nextInt();
            if (cmd == 0)   {
                System.out.print("Exiting customer mode\n");
                FileSaver fileSaver = new FileSaver(customerFilePath, customers, concertFilePath, concerts, bookingFilePath, bookings);
                fileSaver.save();
                flag = false;
            }
            else {
                processConcert(concerts.get(cmd - 1));
            }

        }
    }

    /**
     * Displays the list of concerts available for booking.
     */
    private void viewConcerts() {
        System.out.println("---------------------------------------------------------------------------------------------------------------------------");
        System.out.println("#    Date           Artist Name    Timing         Venue Name                    Total Seats    Seats Booked   Seats Left     ");
        System.out.println("---------------------------------------------------------------------------------------------------------------------------");
        for (Concert concert : concerts) {
            String date = concert.getDate();
            String artist = concert.getArtistName();
            String timing = concert.getTiming();
            String venueName = concert.getVenueName();
            String key = venueName.toLowerCase();
            int totalSeats = 0;
            if (venues.containsKey(Constants.DEFAULT)) {
                totalSeats = venues.get(Constants.DEFAULT).getTotalSeats();
            }
            if (venues.containsKey(key)) {
                totalSeats = venues.get(key).getTotalSeats();
            }
            int seatBooked = 0;
            for (Booking booking : bookings) {
                if (booking.getConcertId().equals(concert.getConcertId())) {
                    seatBooked += booking.getTotalTickets();
                }
            }
            int seatLeft = totalSeats - seatBooked;
            System.out.printf("%-5s%-15s%-15s%-15s%-30s%-15s%-15s%-15s%n", concert.getConcertId(), date, artist, timing, venueName, totalSeats, seatBooked, seatLeft);
        }
        System.out.println("---------------------------------------------------------------------------------------------------------------------------");

    }

    /**
     * Updates the ticket price for a specific concert.
     */
    private void updateTicketPrice() {
        System.out.println(Constants.SELECT_CONCERT);
        viewConcerts();
        System.out.print(Constants.INPUT);
        int cmd = Constants.KEYBOARD.nextInt();
        if (cmd == 0)   return;
        Concert concert = concerts.get(cmd - 1);
        displayPrice(concert);
        Constants.KEYBOARD.nextLine();
        System.out.println("Enter the zone : VIP, SEATING, STANDING: ");
        String zone = Constants.KEYBOARD.nextLine();
        System.out.print("Left zone price: ");
        Double left = Constants.KEYBOARD.nextDouble();
        System.out.print("Centre zone price: ");
        Double middle = Constants.KEYBOARD.nextDouble();
        System.out.print("Right zone price: ");
        Double right = Constants.KEYBOARD.nextDouble();
        concert.setSeatPrice(zone, left, middle, right);

    }

    /**
     * Views all bookings for a selected concert.
     */
    private void viewBookings() {
        System.out.println(Constants.SELECT_CONCERT);
        viewConcerts();
        System.out.print(Constants.INPUT);
        int cmd = Constants.KEYBOARD.nextInt();
        if (cmd == 0)   return;
        Concert concert = concerts.get(cmd - 1);
        List<Booking> bookingList = new ArrayList<>();
        for(Booking booking : bookings) {
            if (booking.getConcertId().equals(concert.getConcertId())) {
                bookingList.add(booking);
            }
        }
        viewBookings(bookingList, concert);
    }

    /**
     * Displays the total payment received for a selected concert.
     */
    private void viewTotalPayment() {
        System.out.println(Constants.SELECT_CONCERT);
        viewConcerts();
        System.out.print(Constants.INPUT);
        int cmd = Constants.KEYBOARD.nextInt();
        if (cmd == 0)   return;
        Concert concert = concerts.get(cmd - 1);
        Double totalPrice = 0.0;
        for (Booking booking : bookings) {
            if (booking.getConcertId().equals(concert.getConcertId())) {
                totalPrice += booking.getTotalPrice();
            }
        }
        System.out.printf("Total Price for this concert is AUD %.1f\n", totalPrice);
    }

    /**
     * Processes a selected concert for booking and viewing details.
     *
     * @param concert the concert to process
     */
    private void processConcert(Concert concert) {
        boolean flag = true;
        while (flag) {
            System.out.println("Select an option to get started!");
            System.out.println("Press 1 to look at the ticket costs");
            System.out.println("Press 2 to view seats layout");
            System.out.println("Press 3 to book seats");
            System.out.println("Press 4 to view booking details");
            System.out.println("Press 5 to exit");
            System.out.print(Constants.INPUT);
            int cmd = Constants.KEYBOARD.nextInt();
            switch (cmd) {
                case Constants.CONCERT_DISPLAY_PRICE:
                    displayPrice(concert);
                    break;
                case Constants.CONCERT_DISPLAY_LAYOUT:
                    displayLayout(concert);
                    break;
                case Constants.CONCERT_BOOK_SEAT:
                    bookSeat(concert);
                    break;
                case Constants.CONCERT_VIEW_BOOKING_DETAILS:
                    viewBookingDetails(concert);
                    break;
                case Constants.CONCERT_EXIT:
                    System.out.println("Exiting this concert");
                    flag = false;
                    break;
                default:
                    System.out.println(Constants.INVALID_INPUT);
            }
        }

    }

    /**
     * Displays the price of tickets for different zones of a concert.
     *
     * @param concert the concert for which to display ticket prices
     */
    private void displayPrice(Concert concert) {
        Double[] seatingPrice = concert.getSeatingPrice();
        Double[] standingPrice = concert.getStandingPrice();
        Double[] vipPrice = concert.getVipPrice();
        System.out.printf("---------- %8s ----------\n", Constants.STANDING);
        System.out.println(Constants.LEFT_SEATS + standingPrice[0]);
        System.out.println(Constants.CENTER_SEATS + standingPrice[1]);
        System.out.println(Constants.RIGHT_SEATS + standingPrice[2]);
        System.out.println("------------------------------");
        System.out.printf("---------- %8s ----------\n", Constants.SEATING);
        System.out.println(Constants.LEFT_SEATS + seatingPrice[0]);
        System.out.println(Constants.CENTER_SEATS + seatingPrice[1]);
        System.out.println(Constants.RIGHT_SEATS + seatingPrice[2]);
        System.out.println("------------------------------");
        System.out.printf("---------- %8s ----------\n", Constants.VIP);

        System.out.println(Constants.LEFT_SEATS + vipPrice[0]);
        System.out.println(Constants.CENTER_SEATS + vipPrice[1]);
        System.out.println(Constants.RIGHT_SEATS + vipPrice[2]);
        System.out.println("------------------------------");
    }

    /**
     * Displays the seating layout of a concert.
     *
     * @param concert the concert for which to display the seating layout
     */
    private void displayLayout(Concert concert) {
        List<Ticket> tickets = new ArrayList<>();
        for(Booking booking : bookings) {
            if (booking.getConcertId().equals(concert.getConcertId())) {
                tickets.addAll(booking.getTickets());
            }
        }
        Venue venue = venues.get(Constants.DEFAULT);
        if (venues.containsKey(concert.getVenueName().toLowerCase())) {
            venue = venues.get(concert.getVenueName().toLowerCase());
        }
        venue.print(tickets);
    }

    /**
     * Books seats for a selected concert.
     *
     * @param concert the concert for which to book seats
     */
    private void bookSeat(Concert concert) {
        Venue venue = venues.get(Constants.DEFAULT);
        if (venues.containsKey(concert.getVenueName().toLowerCase())) {
            venue = venues.get(concert.getVenueName().toLowerCase());
        }

        displayLayout(concert);
        Constants.KEYBOARD.nextLine();
        System.out.print("Enter the aisle number: ");
        String aisle = Constants.KEYBOARD.nextLine();
        System.out.print("Enter the seat number: ");
        int seatNumber = Constants.KEYBOARD.nextInt();
        System.out.print("Enter the number of seats to be booked: ");
        int number = Constants.KEYBOARD.nextInt();
        int bookingId = 0;
        for (Booking booking : bookings) {
            if (booking.getCustomerId().equals(userName) && booking.getConcertId().equals(concert.getConcertId())){
                bookingId = Integer.parseInt(booking.getBookingId());
            }
        }
        bookingId ++ ;
        List<Ticket> tickets = new ArrayList<>();
        for (int i = 0; i < number; i ++ ) {
            int ticketId = i + 1;
            int rowNumber = Integer.parseInt(aisle.substring(1));
            int seat = seatNumber + i;
            String zoneType = "";
            Double price = 0.0;
            if (aisle.charAt(0) == Constants.V) {
                zoneType = Constants.VIP;
                price = concert.getSeatPrice(0, seat, venue);
            } else if (aisle.charAt(0) == Constants.S) {
                zoneType = Constants.SEATING;
                price = concert.getSeatPrice(1, seat, venue);
            } else {
                zoneType = Constants.STANDING;
                price = concert.getSeatPrice(2, seat, venue);
            }
            Ticket ticket = new Ticket(ticketId, rowNumber, seat, zoneType, price);
            tickets.add(ticket);
        }
        Booking booking = new Booking(String.valueOf(bookingId), userName, customerName, concert.getConcertId(), number, tickets);
        bookings.add(booking);

    }

    /**
     * Views booking details for a specific concert.
     *
     * @param concert the concert for which to view booking details
     */
    private void viewBookingDetails(Concert concert) {
        List<Booking> bookingList = new ArrayList<>();
        for (Booking booking : bookings) {
            if (booking.getCustomerId().equals(userName) && booking.getConcertId().equals(concert.getConcertId())) {
                bookingList.add(booking);
            }
        }
        viewBookings(bookingList, concert);
    }

    /**
     * Views a list of bookings for a specific concert.
     *
     * @param bookingList the list of bookings to view
     * @param concert the concert associated with the bookings
     */
    private void viewBookings(List<Booking> bookingList, Concert concert) {
        if (bookingList.isEmpty()) {
            System.out.println("No Bookings found for this concert\n");
        } else {
            System.out.println("Bookings");
            System.out.println("---------------------------------------------------------------------------------------------------------------------------");
            System.out.println("Id   Concert Date   Artist Name    Timing    Venue Name     Seats Booked   Total Price");
            System.out.println("---------------------------------------------------------------------------------------------------------------------------");
            for (int i = 0; i < bookingList.size(); i ++ ) {
                Booking booking = bookingList.get(i);
                System.out.printf("%-5s%-15s%-15s%-10s%-15s%-15s%-10s%n", booking.getBookingId(), concert.getDate(), concert.getArtistName(), concert.getTiming(), concert.getVenueName(), booking.getTickets().size(), booking.getTotalPrice());
            }
            System.out.println("---------------------------------------------------------------------------------------------------------------------------");
            System.out.println();
            System.out.println("Ticket Info");
            for (int i = 0; i < bookingList.size(); i ++ ) {
                List<Ticket> tickets = bookingList.get(i).getTickets();
                System.out.printf("############### Booking Id: %d ####################\n", Integer.parseInt(bookingList.get(i).getBookingId()));
                System.out.println("Id   Aisle Number   Seat Number    Seat Type Price     ");
                System.out.println("##################################################");
                for (int j = 0; j < tickets.size(); j ++ ) {
                    Ticket ticket = tickets.get(j);
                    System.out.printf("%-5s%-15s%-15s%-10s%-10s%n", j + 1, ticket.getRowNumber(), ticket.getSeatNumber(), ticket.getZoneType(), ticket.getPrice());
                }
                System.out.println("##################################################\n");
            }
            System.out.println();
        }
    }

    /**
     * Handles the admin mode, processing admin-related operations.
     *
     * @param args the command-line arguments
     * @throws IOException if an I/O error occurs
     */
    private void handleAdminMode(String[] args) throws IOException {
        // Extract file paths from command line arguments
        userName = "";
        password = "";
        customerFilePath = args[1];
        concertFilePath = args[2];
        bookingFilePath = args[3];
        venueFilePaths = new ArrayList<>();

        // Start from the fifth argument, as first four are mandatory for admin mode
        for (int i = 4; i < args.length; i++) {
            venueFilePaths.add(args[i]);
        }
        venueFilePaths.add(Constants.DEFAULT_VENUE_FILE_PATH);

        // Load data from these files
        if(!loadData())  return;

        // Show welcome message for admin
        System.out.println("Welcome to Ticket Management System Admin Mode.");
        displayMessage();

        boolean flag = true;
        while (flag) {
            System.out.println("Select an option to get started!");
            System.out.println("Press 1 to view all the concert details");
            System.out.println("Press 2 to update the ticket costs");
            System.out.println("Press 3 to view booking details");
            System.out.println("Press 4 to view total payment received for a concert");
            System.out.println("Press 5 to exit");
            System.out.print(Constants.INPUT);
            int cmd = Constants.KEYBOARD.nextInt();
            switch (cmd) {
                case Constants.ADMIN_VIEW_CONCERTS:
                    viewConcerts();
                    break;
                case Constants.ADMIN_UPDATE_TICKET_PRICE:
                    updateTicketPrice();
                    break;
                case Constants.ADMIN_VIEW_BOOKINGS:
                    viewBookings();
                    break;
                case Constants.ADMIN_VIEW_TOTAL_PAYMENT:
                    viewTotalPayment();
                    break;
                case Constants.ADMIN_EXIT:
                    System.out.println("Exiting admin mode");
                    FileSaver fileSaver = new FileSaver(customerFilePath, customers, concertFilePath, concerts, bookingFilePath, bookings);
                    fileSaver.save();
                    flag = false;
                    break;
                default:
                    System.out.println(Constants.INVALID_INPUT);
            }
        }
    }

    /**
     * Loads all necessary data for the application.
     * @return true if the file is valid
     */
    private boolean loadData() {
        if (!loadCustomerData())    return false;
        loadConcertData();
        loadBookingData();

        if (!loadVenueData()) {
            return false;
        }
        return true;
    }

    /**
     * Loads customer data from a file.
     * @return true if the customer file is valid
     */
    private boolean loadCustomerData() {
        boolean valid = true;
        // Attempt to open and read the customer file
        try (BufferedReader reader = new BufferedReader(new FileReader(customerFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    validateAndAddCustomer(line);
                } catch (InvalidLineException | InvalidFormatException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println(customerFilePath + " (No such file or directory)");
            valid = false;
        }
        if (!userName.equals("") && valid) {
            try {
                authenticateCustomer();
            } catch (IncorrectPasswordException | NotFoundException e) {
                System.out.println(e.getMessage());
                valid = false; // Exit the program after displaying the error message
            }
        }
        return valid;

    }

    /**
     * Validates and adds a customer to the system.
     *
     * @param line the line from the customer file to validate and add
     * @throws InvalidLineException if the line format is invalid
     * @throws InvalidFormatException if the data format is invalid
     */
    private void validateAndAddCustomer(String line) throws InvalidLineException, InvalidFormatException {
        String[] parts = line.split(",");
        if (parts.length != 3) {
            throw new InvalidLineException("Invalid Customer Files. Skipping this line.");
        }

        String customerId = parts[0].trim();
        String customerName = parts[1].trim();
        String password = parts[2].trim();

        if (!customerId.matches("\\d+")) {
            throw new InvalidFormatException("Customer Id is in incorrect format. Skipping this line.");
        }

        Customer customer = new Customer(customerId, customerName, password);
        customers.add(customer);
    }

    /**
     * Authenticates the customer using provided username and password.
     *
     * @throws IncorrectPasswordException if the password is incorrect
     * @throws NotFoundException if the customer is not found
     */
    private void authenticateCustomer() throws IncorrectPasswordException, NotFoundException {
        for (Customer customer : customers) {
            if (customer.getCustomerId().equals(userName)) {
                if (!customer.getPassword().equals(password)) {
                    throw new IncorrectPasswordException("Incorrect Password. Terminating Program");
                } else {
                    customerName = customer.getCustomerName();
                    return;
                }
            }
        }
        throw new NotFoundException("Customer does not exist. Terminating Program");
    }

    /**
     * Loads concert data from a file.
     */
    private void loadConcertData() {
        // Check if concert file path is not null or empty
        if (concertFilePath == null || concertFilePath.isEmpty()) {
            System.out.println("Concert file path is not specified.");
            return;
        }

        // Attempt to open and read the concert file
        try (BufferedReader reader = new BufferedReader(new FileReader(concertFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    validateAndAddConcert(line);
                } catch (InvalidLineException | InvalidFormatException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            //ystem.out.println(concertFilePath + " (No such file or directory)");
            //ystem.exit(0);
        }
    }

    /**
     * Validates and adds a concert to the system.
     *
     * @param line the line from the concert file to validate and add
     * @throws InvalidLineException if the line format is invalid
     * @throws InvalidFormatException if the data format is invalid
     */
    private void validateAndAddConcert(String line) throws InvalidLineException, InvalidFormatException {
        String[] parts = line.split(",");
        if (parts.length != 8) {
            throw new InvalidLineException("Invalid Concert Files. Skipping this line.");
        }

        String concertId = parts[0].trim();
        String date = parts[1].trim();
        String timing = parts[2].trim();
        String artistName = parts[3].trim();
        String venueName = parts[4].trim();
        String zonePricing = parts[5].trim() + ":" + parts[6].trim() + ":" + parts[7].trim();

        if (!concertId.matches("\\d+")) {
            throw new InvalidFormatException("Concert Id is in incorrect format. Skipping this line.");
        }
        if (!timing.matches("\\d{4}")) {
            throw new InvalidFormatException("Timing is in incorrect format. Skipping this line.");
        }

        Concert concert = new Concert(concertId, date, timing, artistName, venueName, zonePricing);
        concerts.add(concert);
    }

    /**
     * Loads booking data from a file.
     */
    private void loadBookingData() {

        try (BufferedReader reader = new BufferedReader(new FileReader(bookingFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    parseAndAddBooking(line);
                } catch (InvalidLineException | InvalidFormatException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (IOException e) {
        }
    }

    /**
     * Parses and adds a booking to the system.
     *
     * @param line the line from the booking file to parse and add
     * @throws InvalidLineException if the line format is invalid
     * @throws InvalidFormatException if the data format is invalid
     */
    private void parseAndAddBooking(String line) throws InvalidLineException, InvalidFormatException {
        String[] parts = line.split(",");
        int minFixedDataPoints = 5; // Booking ID, Customer ID, Customer Name, Concert ID, Total Tickets
        if (parts.length < minFixedDataPoints || (parts.length - minFixedDataPoints) % 5 != 0) {
            throw new InvalidLineException("Invalid booking Files. Skipping this line.");
        }

        String bookingId = parts[0].trim();
        String customerId = parts[1].trim();
        String customerName = parts[2].trim();
        String concertId = parts[3].trim();
        int totalTickets;

        try {

            totalTickets = Integer.parseInt(parts[4].trim());
            if (Integer.parseInt(parts[4].trim()) == 0) {
                throw new InvalidFormatException("Incorrect Number of Tickets. Skipping this line.");
            }
        } catch (NumberFormatException e) {
            throw new InvalidFormatException("Incorrect Number of Tickets. Skipping this line.");
        }

        List<Ticket> tickets = new ArrayList<>();
        for (int i = 0; i < totalTickets; i++) {
            int baseIndex = minFixedDataPoints + i * 5;
            int ticketId = Integer.parseInt(parts[baseIndex].trim());
            int rowNumber = Integer.parseInt(parts[baseIndex + 1].trim());
            int seatNumber = Integer.parseInt(parts[baseIndex + 2].trim());
            String zoneType = parts[baseIndex + 3].trim();
            double price = Double.parseDouble(parts[baseIndex + 4].trim());

            if (!zoneType.matches("VIP|SEATING|STANDING")) {
                throw new InvalidFormatException("Invalid Zone Type. Skipping this line.");
            }

            tickets.add(new Ticket(ticketId, rowNumber, seatNumber, zoneType, price));
        }

        Booking booking = new Booking(bookingId, customerId, customerName, concertId, totalTickets, tickets);

        bookings.add(booking);
    }


    /**
     * Loads venue data from specified files.
     * @return true if the venue file is valid
     */
    private boolean loadVenueData() {
        boolean valid = true;
        for (String filePath : venueFilePaths) {
            String venueKey = extractVenueKey(filePath);
            boolean flag = true;
            for(int i = 0; i < concerts.size(); i ++ ) {
                if(concerts.get(i).getVenueName().toLowerCase().equals(venueKey)) {
                    flag = false;
                }
            }
            if(flag && !venueKey.equals(Constants.DEFAULT))    continue;
            try {
                Venue venue = readVenueFile(filePath);
                venues.put(venueKey, venue);
            } catch (FileNotFoundException e) {
            } catch (IOException e) {
                System.out.println("Error reading file: " + e.getMessage());
                valid =  false;
            } catch (InvalidFormatException e) {
                System.out.println(e.getMessage());
            }
        }
        return valid;
    }

    /**
     * Extracts the venue key from a file path.
     *
     * @param filePath the file path to extract the venue key from
     * @return the extracted venue key
     */
    private String extractVenueKey(String filePath) {
        File file = new File(filePath);
        String fileName = file.getName();
        return fileName.substring(6, fileName.lastIndexOf('.'));
    }

    /**
     * Reads venue data from a file.
     *
     * @param filePath the file path to read the venue data from
     * @return the venue object created from the file data
     * @throws IOException if an I/O error occurs
     * @throws InvalidFormatException if the data format is invalid
     */
    private Venue readVenueFile(String filePath) throws IOException, InvalidFormatException {
        int vipRows = 0, seatingRows = 0, standingRows = 0;
        int left = 0, middle = 0, right = 0;
        boolean flag = true;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (line.trim().length() == 0)  continue;
                // Calculate seats for each section
                if (flag) {
                    left = parts[1].replaceAll("[\\[\\]]", "").length();
                    middle = parts[2].replaceAll("[\\[\\]]", "").length();
                    right = parts[3].replaceAll("[\\[\\]]", "").length();
                    flag = false;
                }

                // Update row counts and seat counts based on zone type
                switch (parts[0].charAt(0)) {
                    case Constants.V:
                        vipRows++;
                        break;
                    case Constants.S:
                        seatingRows++;
                        break;
                    case Constants.T:
                        standingRows++;
                        break;
                    default:
                        System.out.println("Invalid Zone Type. Skipping this line.");
                        continue;
                }
            }
        }

        return new Venue(vipRows, seatingRows, standingRows, left, middle, right);
    }


    /**
     * Displays a welcome message for the user.
     */
    public void displayMessage() {
        System.out.print("\n" +
                " ________  ___ _____ \n" +
                "|_   _|  \\/  |/  ___|\n" +
                "  | | | .  . |\\ `--. \n" +
                "  | | | |\\/| | `--. \\\n" +
                "  | | | |  | |/\\__/ /\n" +
                "  \\_/ \\_|  |_/\\____/ \n" +
                "                    \n" +
                "                    \n");
    }
}
