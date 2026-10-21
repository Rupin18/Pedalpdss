import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * CampusCycle - simple Java console demonstration.
 *
 * This Java file is included as the Java component of the project submission.
 * The separately hosted HTML/CSS/JavaScript website does not call this file.
 * Compile: javac Main.java
 * Run:     java Main
 */
public class Main {
    private static final DateTimeFormatter FORMAT =
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    static class Bicycle {
        final int id;
        final String name;
        final double hourlyRate;
        boolean rented;
        Rental currentRental;

        Bicycle(int id, String name, double hourlyRate) {
            this.id = id;
            this.name = name;
            this.hourlyRate = hourlyRate;
        }
    }

    static class Rental {
        final String studentName;
        final String armietPin;
        final String phone;
        final String purpose;
        final Bicycle bicycle;
        final LocalDateTime rentTime;
        LocalDateTime returnTime;
        long durationMinutes;
        double totalCost;
        String status = "Rented";

        Rental(String studentName, String armietPin, String phone, String purpose,
               Bicycle bicycle, LocalDateTime rentTime) {
            this.studentName = studentName;
            this.armietPin = armietPin;
            this.phone = phone;
            this.purpose = purpose;
            this.bicycle = bicycle;
            this.rentTime = rentTime;
        }
    }

    private final List<Bicycle> bicycles = new ArrayList<>();
    private final List<Rental> history = new ArrayList<>();

    public Main() {
        bicycles.add(new Bicycle(201, "Campus City", 15));
        bicycles.add(new Bicycle(202, "Trailblazer MTB", 25));
        bicycles.add(new Bicycle(203, "Swift Roadster", 25));
        bicycles.add(new Bicycle(204, "Everyday Hybrid", 20));
        bicycles.add(new Bicycle(205, "Comfort Cruiser", 18));
        bicycles.add(new Bicycle(206, "Eco E-Cycle", 40));
        bicycles.add(new Bicycle(207, "Metro Commuter", 18));
        bicycles.add(new Bicycle(208, "Summit Explorer", 28));
        bicycles.add(new Bicycle(209, "Velocity Sprint", 30));
        bicycles.add(new Bicycle(210, "Campus Flex", 22));
        bicycles.add(new Bicycle(211, "EasyGlide", 18));
        bicycles.add(new Bicycle(212, "Volt Campus", 45));
        bicycles.add(new Bicycle(213, "Greenway City", 16));
        bicycles.add(new Bicycle(214, "Ridge Runner", 27));
        bicycles.add(new Bicycle(215, "Roadline Pro", 32));
        bicycles.add(new Bicycle(216, "Daily Duo", 21));
        bicycles.add(new Bicycle(217, "Leisure Loop", 17));
        bicycles.add(new Bicycle(218, "E-Motion", 42));
        bicycles.add(new Bicycle(219, "Trail Scout", 26));
        bicycles.add(new Bicycle(220, "Urban Lite", 19));
    }

    private void showBicycles() {
        System.out.println("\nCAMPUSCYCLE — BICYCLES");
        System.out.println("-----------------------------------------------");
        for (Bicycle bike : bicycles) {
            System.out.printf("%d | %-15s | Rs. %.2f/hour | %s%n",
                    bike.id, bike.name, bike.hourlyRate,
                    bike.rented ? "RENTED" : "AVAILABLE");
        }
    }

    private Bicycle findBicycle(int id) {
        for (Bicycle bike : bicycles) {
            if (bike.id == id) return bike;
        }
        return null;
    }

    private void rentBicycle(java.util.Scanner input) {
        System.out.print("Student name: ");
        String name = input.nextLine().trim();
        System.out.print("ARMIET PIN: ");
        String armietPin = input.nextLine().trim();
        System.out.print("Phone number: ");
        String phone = input.nextLine().trim();
        if (name.isEmpty() || armietPin.isEmpty() || !phone.matches("[0-9+() -]{8,18}")) {
            System.out.println("Enter a name, ARMIET PIN, and valid phone number.");
            return;
        }

        System.out.print("Purpose (Regular Student / College Visit / Admission Enquiry / Campus Tour / Other): ");
        String purpose = input.nextLine().trim();
        showBicycles();
        System.out.print("Enter bicycle ID: ");
        if (!input.hasNextInt()) {
            input.nextLine();
            System.out.println("Bicycle ID must be a number.");
            return;
        }
        int id = input.nextInt();
        input.nextLine();

        Bicycle bike = findBicycle(id);
        if (bike == null) {
            System.out.println("Bicycle not found.");
            return;
        }
        if (bike.rented) {
            System.out.println("That bicycle is already rented.");
            return;
        }

        Rental rental = new Rental(name, armietPin, phone, purpose, bike, LocalDateTime.now());
        history.add(rental);
        bike.rented = true;
        bike.currentRental = rental;
        System.out.println("\nRental recorded successfully.");
        System.out.println("Student: " + name + " | ARMIET PIN: " + armietPin + " | Phone: " + phone);
        System.out.println("Bicycle: " + bike.name);
        System.out.println("Hourly rate: Rs. " + bike.hourlyRate);
        System.out.println("Rent time: " + FORMAT.format(rental.rentTime));
    }

    private void returnBicycle(java.util.Scanner input) {
        System.out.print("Student name: ");
        String name = input.nextLine().trim();
        System.out.print("ARMIET PIN: ");
        String armietPin = input.nextLine().trim();
        System.out.print("Phone number: ");
        String phone = input.nextLine().trim();
        showBicycles();
        System.out.print("Enter bicycle ID to return: ");
        if (!input.hasNextInt()) {
            input.nextLine();
            System.out.println("Bicycle ID must be a number.");
            return;
        }
        int id = input.nextInt();
        input.nextLine();

        Bicycle bike = findBicycle(id);
        if (bike == null || !bike.rented || bike.currentRental == null) {
            System.out.println("No active rental was found for that bicycle.");
            return;
        }
        Rental rental = bike.currentRental;
        if (!rental.armietPin.equalsIgnoreCase(armietPin)
                || !rental.phone.replaceAll("\\D", "").equals(phone.replaceAll("\\D", ""))
                || !rental.studentName.equalsIgnoreCase(name)) {
            System.out.println("Student name, ARMIET PIN, or phone number does not match the rental.");
            return;
        }

        rental.returnTime = LocalDateTime.now();
        rental.durationMinutes = Duration.between(rental.rentTime, rental.returnTime).toMinutes();
        long billableHours = Math.max(1, (rental.durationMinutes + 59) / 60);
        rental.totalCost = billableHours * bike.hourlyRate;
        rental.status = "Returned";
        bike.rented = false;
        bike.currentRental = null;

        System.out.println("\nRETURN RECEIPT");
        System.out.println("-----------------------------------------------");
        System.out.println("Student: " + rental.studentName + " (ARMIET PIN: " + rental.armietPin + ")");
        System.out.println("Phone: " + rental.phone);
        System.out.println("Bicycle: " + bike.name + " (#" + bike.id + ")");
        System.out.println("Purpose: " + rental.purpose);
        System.out.println("Rent time: " + FORMAT.format(rental.rentTime));
        System.out.println("Return time: " + FORMAT.format(rental.returnTime));
        System.out.println("Duration: " + rental.durationMinutes + " minute(s)");
        System.out.printf("Total demo cost: Rs. %.2f%n", rental.totalCost);
        System.out.println("Payment is not processed by this console demo.");
    }

    private void showHistory() {
        System.out.println("\nRENTAL HISTORY");
        if (history.isEmpty()) {
            System.out.println("No rental records yet.");
            return;
        }
        for (Rental rental : history) {
            System.out.printf("%s | PIN %s | Phone %s | %s (#%d) | %s | Rent: %s",
                    rental.studentName, rental.armietPin, rental.phone, rental.bicycle.name,
                    rental.bicycle.id, rental.status, FORMAT.format(rental.rentTime));
            if (rental.returnTime != null) {
                System.out.printf(" | Return: %s | Cost: Rs. %.2f",
                        FORMAT.format(rental.returnTime), rental.totalCost);
            }
            System.out.println();
        }
    }

    private void showSummary() {
        int rented = 0;
        for (Bicycle bike : bicycles) if (bike.rented) rented++;
        System.out.println("\nADMIN OVERVIEW");
        System.out.println("Total bicycles: " + bicycles.size());
        System.out.println("Available bicycles: " + (bicycles.size() - rented));
        System.out.println("Rented bicycles: " + rented);
        System.out.println("Total rental records: " + history.size());
    }

    private void run() {
        try (java.util.Scanner input = new java.util.Scanner(System.in)) {
            boolean running = true;
            while (running) {
                System.out.println("\n========== CAMPUSCYCLE ==========");
                System.out.println("1. View bicycles");
                System.out.println("2. Rent a bicycle");
                System.out.println("3. Return a bicycle");
                System.out.println("4. Rental history");
                System.out.println("5. Admin overview");
                System.out.println("0. Exit");
                System.out.print("Choose an option: ");

                if (!input.hasNextInt()) {
                    input.nextLine();
                    System.out.println("Please enter a menu number.");
                    continue;
                }
                int choice = input.nextInt();
                input.nextLine();

                switch (choice) {
                    case 1: showBicycles(); break;
                    case 2: rentBicycle(input); break;
                    case 3: returnBicycle(input); break;
                    case 4: showHistory(); break;
                    case 5: showSummary(); break;
                    case 0: running = false; break;
                    default: System.out.println("Choose one of the listed options.");
                }
            }
        }
        System.out.println("CampusCycle closed.");
    }

    public static void main(String[] args) {
        new Main().run();
    }
}
