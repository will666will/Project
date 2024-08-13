Ticket Management System
Overview
The Ticket Management System is designed to manage concert bookings, including customer registrations, concert management, and ticket bookings. The system is capable of operating in two modes: customer mode and admin mode.

Compilation
Navigate to the root directory where the TicketManagementEngine.java file is located.

Compile the program using the following command:
javac TicketManagementEngine.java

Running the Program
The system can be run in two modes: Customer Mode and Admin Mode.

Customer Mode
In this mode, customers can register, view concerts, book tickets, and view their booking details.
java TicketManagementEngine --customer [username] [password] [customer_file_path] [concert_file_path] [booking_file_path] [venue_file_paths...]

username: (Optional) The customer's username.
password: (Optional) The customer's password.
customer_file_path: The file path for customer data.
concert_file_path: The file path for concert data.
booking_file_path: The file path for booking data.
venue_file_paths: (Optional) One or more file paths for venue data.

Example:
java TicketManagementEngine --customer customers.txt concerts.txt bookings.txt venues.txt

Admin Mode
In this mode, the admin can manage concerts, update ticket prices, view all bookings, and check the total payments received for a concert.
java TicketManagementEngine --admin [customer_file_path] [concert_file_path] [booking_file_path] [venue_file_paths...]
customer_file_path: The file path for customer data.
concert_file_path: The file path for concert data.
booking_file_path: The file path for booking data.
venue_file_paths: (Optional) One or more file paths for venue data.

Example:
java TicketManagementEngine --admin customers.txt concerts.txt bookings.txt venues.txt

Additional Notes
The system will prompt the user for additional information if not all parameters are provided.
The system automatically saves any changes made during the session when exiting either the customer or admin mode.