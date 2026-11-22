package main;

import java.time.LocalDate;
import java.util.Scanner;

import model.Equipment;
import model.Customer;
import model.Booking;
import service.RentalManager;
import reports.ReportManager;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        RentalManager manager = new RentalManager();
        manager.loadEquipment();
        manager.loadCustomers();
        manager.loadBookings();
        ReportManager reports = new ReportManager(manager);

        int choice;

        do {

            System.out.println("\n===== RENTAL EQUIPMENT MANAGER =====");
            System.out.println("1. Add Equipment");
            System.out.println("2. View Equipment");
            System.out.println("3. Search Equipment");
            System.out.println("4. Register Customer");
            System.out.println("5. View Customers");
            System.out.println("6. Book Equipment");
            System.out.println("7. View Bookings");
            System.out.println("8. View Bookings");
            System.out.println("9. Return Equipment");
            System.out.println("10. Active Rentals Report");
            System.out.println("11. Overdue Rentals Report");
            System.out.println("12. Equipment Utilization Report");
            System.out.println("13. Revenue Report");
            System.out.println("14. Exit");
            System.out.print("Enter Choice: ");
            choice = sc.nextInt();

            switch (choice) {

            case 1:

                System.out.print("Enter Equipment ID: ");
                int id = sc.nextInt();

                sc.nextLine();

                System.out.print("Enter Equipment Name: ");
                String name = sc.nextLine();

                System.out.print("Enter Category: ");
                String category = sc.nextLine();

                System.out.print("Enter Daily Charge: ");
                double charge = sc.nextDouble();

                System.out.print("Enter Quantity: ");
                int quantity = sc.nextInt();

                sc.nextLine();

                System.out.print("Enter Condition: ");
                String condition = sc.nextLine();

                Equipment e = new Equipment(
                    id, name, category, charge, quantity, condition
                );

                manager.addEquipment(e);
                break;

            case 2:

                manager.displayEquipment();
                break;

            case 3:

                System.out.print("Enter Equipment ID: ");
                manager.searchEquipment(sc.nextInt());
                break;

            case 4:

                System.out.print("Enter Customer ID: ");
                int cid = sc.nextInt();

                sc.nextLine();

                System.out.print("Enter Customer Name: ");
                String cname = sc.nextLine();

                System.out.print("Enter Phone: ");
                String phone = sc.nextLine();

                System.out.print("Enter Email: ");
                String email = sc.nextLine();

                Customer c = new Customer(
                    cid, cname, phone, email
                );

                manager.registerCustomer(c);
                break;

            case 5:

                manager.displayCustomers();
                break;

            case 6:

                System.out.print("Enter Booking ID: ");
                int bid = sc.nextInt();

                System.out.print("Enter Customer ID: ");
                int customerId = sc.nextInt();

                System.out.print("Enter Equipment ID: ");
                int equipmentId = sc.nextInt();

                System.out.print("Enter Booking Date (YYYY-MM-DD): ");
                LocalDate start = LocalDate.parse(sc.next());

                System.out.print("Enter Return Date (YYYY-MM-DD): ");
                LocalDate end = LocalDate.parse(sc.next());

                Booking b = new Booking(
                    bid, customerId, equipmentId, start, end
                );

                manager.bookEquipment(b);
                break;

            case 7:

                manager.displayBookings();
                break;
            case 9:

                System.out.print("Enter Booking ID: ");
                int returnBookingId = sc.nextInt();

                System.out.print("Enter Actual Return Date (YYYY-MM-DD): ");
                LocalDate actualDate = LocalDate.parse(sc.next());

                manager.returnEquipment(returnBookingId, actualDate);

                break;
            case 11:
                reports.activeRentalsReport();
                break;

            case 12:
                reports.overdueRentalsReport();
                break;
            case 13:
            	reports.revenueReport();
                break;   
            case 14:

                System.out.println("Thank You!");
                break;

            default:

                System.out.println("Invalid Choice!");
            }

        } while (choice != 14);

        sc.close();
    }
}
