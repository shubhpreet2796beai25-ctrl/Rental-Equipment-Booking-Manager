package reports;

import java.time.LocalDate;
import java.util.stream.Collectors;
import service.Payment;
import model.Booking;
import model.Equipment;
import service.RentalManager;

public class ReportManager {

    private RentalManager manager;

    public ReportManager(RentalManager manager) {
        this.manager = manager;
    }

 
    public void activeRentalsReport() {

        System.out.println("\n===== ACTIVE RENTALS =====");

        manager.getAllBookings().stream()
        .filter(b -> b.getStatus().equals("ACTIVE"))
        .forEach(b -> {

            System.out.println("Booking ID: " + b.getBookingId());
            System.out.println("Customer ID: " + b.getCustomerId());
            System.out.println("Equipment ID: " + b.getEquipmentId());
            System.out.println("Return Date: " + b.getReturnDate());

            System.out.println("----------------------");
        });
    }

    public void overdueRentalsReport() {

        System.out.println("\n===== OVERDUE RENTALS =====");

        manager.getAllBookings().stream()
        .filter(b -> b.getStatus().equals("ACTIVE"))
        .filter(b -> b.getReturnDate().isBefore(LocalDate.now()))
        .forEach(b -> {

            System.out.println("Booking ID: " + b.getBookingId());
            System.out.println("Equipment ID: " + b.getEquipmentId());
            System.out.println("Return Date: " + b.getReturnDate());

            System.out.println("OVERDUE!");

            System.out.println("----------------------");
        });
    }
 // Equipment Utilization Report
    public void equipmentUtilizationReport() {

        System.out.println("\n===== EQUIPMENT UTILIZATION REPORT =====");

        manager.getAllEquipment().forEach((id, equipment) -> {

            long totalBookings = manager.getAllBookings()
                    .stream()
                    .filter(b -> b.getEquipmentId() == id)
                    .count();

            System.out.println("Equipment ID: " + id);

            System.out.println("Equipment Name: "
                    + equipment.getEquipmentName());

            System.out.println("Total Times Rented: " + totalBookings);

            System.out.println("--------------------------------");
        });
    }
 // Revenue Report
    public void revenueReport() {

        double totalRevenue = 0;
        double totalPenalty = 0;

        System.out.println("\n===== REVENUE REPORT =====");

        for (Payment p : manager.getAllPayments()) {

            totalRevenue += p.getTotalAmount();
            totalPenalty += p.getLatePenalty();
        }

        System.out.println("Completed Payments: "
                + manager.getAllPayments().size());

        System.out.println("Total Late Penalty: Rs. "
                + totalPenalty);

        System.out.println("Total Revenue: Rs. "
                + totalRevenue);

        System.out.println("==========================");
    }
}
