import java.util.Scanner;

class TicketBookingSystem {
    private boolean[] seats;

    public TicketBookingSystem(int totalSeats) {
        seats = new boolean[totalSeats];
    }

    // Synchronized method to prevent double booking
    public synchronized String bookSeat(int seatNumber, String user, boolean isVIP) {
        if (seatNumber < 1 || seatNumber > seats.length) {
            return "Invalid seat number!";
        }
        if (seats[seatNumber - 1]) {
            return user + ": Seat " + seatNumber + " is already booked!";
        }
        
        seats[seatNumber - 1] = true;
        return user + " (" + (isVIP ? "VIP" : "Regular") + ") booked seat " + seatNumber;
    }

    // Display available seats
    public synchronized void displaySeats() {
        System.out.println("\nCurrent Seat Status:");
        for (int i = 0; i < seats.length; i++) {
            System.out.println("Seat " + (i + 1) + ": " + (seats[i] ? "Booked" : "Available"));
        }
    }
}

// Thread class for users booking seats
class BookingThread extends Thread {
    private TicketBookingSystem system;
    private int seatNumber;
    private String user;
    private boolean isVIP;

    public BookingThread(TicketBookingSystem system, int seatNumber, String user, boolean isVIP) {
        this.system = system;
        this.seatNumber = seatNumber;
        this.user = user;
        this.isVIP = isVIP;
        setPriority(isVIP ? Thread.MAX_PRIORITY : Thread.NORM_PRIORITY); // VIP gets high priority
    }

    @Override
    public void run() {
        System.out.println(system.bookSeat(seatNumber, user, isVIP));
    }
}

// Main class with switch-case menu
public class TicketBookingApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter number of seats available: ");
        int totalSeats = scanner.nextInt();

        TicketBookingSystem system = new TicketBookingSystem(totalSeats);

        while (true) {
            System.out.println("\n===== Ticket Booking System =====");
            System.out.println("1. Book a Seat");
            System.out.println("2. Display Seat Status");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            
            switch (choice) {
                case 1: // Book a seat
                    System.out.print("Enter your name: ");
                    String user = scanner.next();
                    System.out.print("Enter seat number to book: ");
                    int seatNumber = scanner.nextInt();
                    System.out.print("Are you a VIP? (true/false): ");
                    boolean isVIP = scanner.nextBoolean();
                    
                    BookingThread booking = new BookingThread(system, seatNumber, user, isVIP);
                    booking.start();
                    
                    try {
                        booking.join(); // Ensure booking completes before moving on
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;

                case 2: // Display seats
                    system.displaySeats();
                    break;

                case 3: // Exit
                    System.out.println("Exiting Ticket Booking System. Goodbye!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice! Please select a valid option.");
            }
        }
    }
}
