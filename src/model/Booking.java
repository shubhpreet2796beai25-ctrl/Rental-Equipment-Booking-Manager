package model;

import java.time.LocalDate;

public class Booking {

    private int bookingId;
    private int customerId;
    private int equipmentId;

    private LocalDate bookingDate;
    private LocalDate returnDate;
    private LocalDate actualReturnDate;

    private String status;

    public Booking(int bookingId, int customerId,
            int equipmentId, LocalDate bookingDate,
            LocalDate returnDate) {

        this.bookingId = bookingId;
        this.customerId = customerId;
        this.equipmentId = equipmentId;
        this.bookingDate = bookingDate;
        this.returnDate = returnDate;

        this.status = "ACTIVE";
    }

    public int getBookingId() {
        return bookingId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getEquipmentId() {
        return equipmentId;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public LocalDate getActualReturnDate() {
        return actualReturnDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setActualReturnDate(LocalDate date) {
        this.actualReturnDate = date;
    }
}
