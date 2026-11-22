package service;

import java.util.ArrayList;
import service.Payment;
import java.sql.ResultSet;
import java.util.HashMap;
import database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.Equipment;
import model.Customer;
import model.Booking;
import java.time.temporal.ChronoUnit;
import java.time.LocalDate;

public class RentalManager {

  
    private HashMap<Integer, Equipment> equipmentMap = new HashMap<>();

   
    private ArrayList<Customer> customers = new ArrayList<>();

   
    private ArrayList<Booking> bookings = new ArrayList<>();
    private ArrayList<Payment> payments = new ArrayList<>();

    public void loadEquipment() {

        String sql = "SELECT * FROM Equipment";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Equipment e = new Equipment(
                    rs.getInt("equipmentId"),
                    rs.getString("equipmentName"),
                    rs.getString("category"),
                    rs.getDouble("dailyCharge"),
                    rs.getInt("quantity"),
                    rs.getString("equipmentCondition")
                );

                equipmentMap.put(e.getEquipmentId(), e);
            }

            System.out.println("Equipment Loaded Successfully!");

        } catch (SQLException ex) {
            System.out.println("Equipment Loading Failed!");
            ex.printStackTrace();
        }
    }
    public void addEquipment(Equipment equipment) {

        String sql = "INSERT INTO Equipment VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, equipment.getEquipmentId());
            ps.setString(2, equipment.getEquipmentName());
            ps.setString(3, equipment.getCategory());
            ps.setDouble(4, equipment.getDailyCharge());
            ps.setInt(5, equipment.getQuantity());
            ps.setString(6, equipment.getCondition());

            ps.executeUpdate();

            equipmentMap.put(equipment.getEquipmentId(), equipment);

            System.out.println("Equipment Added Successfully!");

        } catch (SQLException e) {
            System.out.println("Equipment Save Failed!");
            e.printStackTrace();
        }
    }

 
    public void displayEquipment() {

        if (equipmentMap.isEmpty()) {
            System.out.println("No Equipment Available!");
            return;
        }

        for (Equipment e : equipmentMap.values()) {
            e.displayEquipment();
        }
    }


    public void searchEquipment(int id) {

        Equipment e = equipmentMap.get(id);

        if (e != null) {
            e.displayEquipment();
        } else {
            System.out.println("Equipment Not Found!");
        }
    }

    public void loadCustomers() {

        String sql = "SELECT * FROM Customer";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Customer c = new Customer(
                    rs.getInt("customerId"),
                    rs.getString("customerName"),
                    rs.getString("phone"),
                    rs.getString("email")
                );

                customers.add(c);
            }

            System.out.println("Customers Loaded Successfully!");

        } catch (SQLException ex) {
            System.out.println("Customer Loading Failed!");
            ex.printStackTrace();
        }
    }
    public void registerCustomer(Customer customer) {

        String sql = "INSERT INTO Customer VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, customer.getCustomerId());
            ps.setString(2, customer.getCustomerName());
            ps.setString(3, customer.getPhone());
            ps.setString(4, customer.getEmail());

            ps.executeUpdate();

            customers.add(customer);

            System.out.println("Customer Registered Successfully!");

        } catch (SQLException e) {
            System.out.println("Customer Save Failed!");
            e.printStackTrace();
        }
    }

  
    public void displayCustomers() {

        if (customers.isEmpty()) {
            System.out.println("No Customers Found!");
            return;
        }

        for (Customer c : customers) {
            c.displayCustomer();
        }
    }
 // Check Equipment Availability
    public boolean checkAvailability(int equipmentId,
            java.time.LocalDate startDate,
            java.time.LocalDate endDate) {

        Equipment equipment = equipmentMap.get(equipmentId);

        if (equipment == null) {
            return false;
        }

        int bookedQuantity = 0;

        for (Booking b : bookings) {

            if (b.getEquipmentId() == equipmentId &&
                b.getStatus().equals("ACTIVE")) {

                boolean overlap =
                    !startDate.isAfter(b.getReturnDate()) &&
                    !endDate.isBefore(b.getBookingDate());

                if (overlap) {
                    bookedQuantity++;
                }
            }
        }

        return bookedQuantity < equipment.getQuantity();
    }

    public void loadBookings() {

        String sql = "SELECT * FROM Booking";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Booking b = new Booking(
                    rs.getInt("bookingId"),
                    rs.getInt("customerId"),
                    rs.getInt("equipmentId"),
                    rs.getDate("bookingDate").toLocalDate(),
                    rs.getDate("returnDate").toLocalDate()
                );

                java.sql.Date actualDate = rs.getDate("actualReturnDate");

                if (actualDate != null) {
                    b.setActualReturnDate(actualDate.toLocalDate());
                }

                b.setStatus(rs.getString("status"));

                bookings.add(b);
            }

            System.out.println("Bookings Loaded Successfully!");

        } catch (SQLException ex) {
            System.out.println("Booking Loading Failed!");
            ex.printStackTrace();
        }
    }
    // Book Equipment
    public void bookEquipment(Booking booking) {

        if (!equipmentMap.containsKey(booking.getEquipmentId())) {
            System.out.println("Equipment Not Found!");
            return;
        }

        boolean customerExists = false;

        for (Customer c : customers) {
            if (c.getCustomerId() == booking.getCustomerId()) {
                customerExists = true;
                break;
            }
        }

        if (!customerExists) {
            System.out.println("Customer Not Found!");
            return;
        }

        if (!booking.getReturnDate().isAfter(booking.getBookingDate())) {
            System.out.println("Invalid Booking Dates!");
            return;
        }

        boolean available = checkAvailability(
            booking.getEquipmentId(),
            booking.getBookingDate(),
            booking.getReturnDate()
        );

        if (!available) {
            System.out.println("Equipment Not Available!");
            return;
        }

        String sql = "INSERT INTO Booking VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, booking.getBookingId());
            ps.setInt(2, booking.getCustomerId());
            ps.setInt(3, booking.getEquipmentId());
            ps.setDate(4, java.sql.Date.valueOf(booking.getBookingDate()));
            ps.setDate(5, java.sql.Date.valueOf(booking.getReturnDate()));
            ps.setDate(6, null);
            ps.setString(7, booking.getStatus());

            ps.executeUpdate();

            bookings.add(booking);

            System.out.println("Booking Successful!");

        } catch (SQLException e) {
            System.out.println("Booking Save Failed!");
            e.printStackTrace();
        }
    }


    // Display All Bookings
    public void displayBookings() {

        if (bookings.isEmpty()) {
            System.out.println("No Bookings Found!");
            return;
        }

        for (Booking b : bookings) {

            System.out.println("Booking ID: " + b.getBookingId());
            System.out.println("Customer ID: " + b.getCustomerId());
            System.out.println("Equipment ID: " + b.getEquipmentId());
            System.out.println("Booking Date: " + b.getBookingDate());
            System.out.println("Return Date: " + b.getReturnDate());
            System.out.println("Status: " + b.getStatus());

            System.out.println("-------------------------");
        }
    }
 // Return Equipment
    public void returnEquipment(int bookingId, LocalDate actualDate) {

        for (Booking b : bookings) {

            if (b.getBookingId() == bookingId) {

                if (!b.getStatus().equals("ACTIVE")) {
                    System.out.println("Equipment Already Returned!");
                    return;
                }

                if (actualDate.isBefore(b.getBookingDate())) {
                    System.out.println("Invalid Return Date!");
                    return;
                }

                Equipment e = equipmentMap.get(b.getEquipmentId());

                long rentalDays = ChronoUnit.DAYS.between(
                    b.getBookingDate(), actualDate
                ) + 1;

                long lateDays = 0;

                if (actualDate.isAfter(b.getReturnDate())) {

                    lateDays = ChronoUnit.DAYS.between(
                        b.getReturnDate(), actualDate
                    );
                }

                Payment payment = new Payment(
                    e.getDailyCharge(), rentalDays, lateDays
                );

                b.setActualReturnDate(actualDate);
                b.setStatus("RETURNED");
                String sql = "UPDATE Booking SET actualReturnDate = ?, status = ? WHERE bookingId = ?";

                try (Connection con = DBConnection.getConnection();
                     PreparedStatement ps = con.prepareStatement(sql)) {

                    ps.setDate(1, java.sql.Date.valueOf(actualDate));
                    ps.setString(2, "RETURNED");
                    ps.setInt(3, bookingId);

                    ps.executeUpdate();

                    System.out.println("Booking Updated Successfully!");

                } catch (SQLException ex) {
                    System.out.println("Booking Update Failed!");
                    ex.printStackTrace();
                }

                payment.displayBill();
                String paymentSql = "INSERT INTO Payment (bookingId, rentalAmount, latePenalty, totalAmount) VALUES (?, ?, ?, ?)";

                try (Connection con = DBConnection.getConnection();
                     PreparedStatement ps = con.prepareStatement(sql)) {

                    ps.setInt(1, bookingId);
                    ps.setDouble(2, payment.getRentalAmount());
                    ps.setDouble(3, payment.getLatePenalty());
                    ps.setDouble(4, payment.getTotalAmount());

                    ps.executeUpdate();

                    System.out.println("Payment Saved Successfully!");

                } catch (SQLException ex) {
                    System.out.println("Payment Save Failed!");
                    ex.printStackTrace();
                }
                payments.add(payment);

                System.out.println("Equipment Returned Successfully!");

                return;
            }
        }

        System.out.println("Booking Not Found!");
    }
    public ArrayList<Booking> getAllBookings() {
        return bookings;
    }

    public HashMap<Integer, Equipment> getAllEquipment() {
        return equipmentMap;
    }
    public ArrayList<Payment> getAllPayments() {
        return payments;
    }
}
