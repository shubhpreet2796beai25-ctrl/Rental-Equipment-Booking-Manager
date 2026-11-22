package service;

public class Payment {

    private double rentalAmount;
    private double latePenalty;
    private double totalAmount;

    public Payment(double dailyCharge, long rentalDays,
                   long lateDays) {

        rentalAmount = dailyCharge * rentalDays;

        latePenalty = lateDays * 200;

        totalAmount = rentalAmount + latePenalty;
        
    }
    public double getRentalAmount() {
        return rentalAmount;
    }

    public double getLatePenalty() {
        return latePenalty;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void displayBill() {

        System.out.println("\n========== RENTAL BILL ==========");

        System.out.println("Rental Amount: Rs. " + rentalAmount);

        System.out.println("Late Penalty: Rs. " + latePenalty);

        System.out.println("Total Amount: Rs. " + totalAmount);

        System.out.println("=================================");
    }
}
