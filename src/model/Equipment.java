package model;

public class Equipment {

    private int equipmentId;
    private String equipmentName;
    private String category;
    private double dailyCharge;
    private int quantity;
    private String condition;

    public Equipment(int equipmentId, String equipmentName,
            String category, double dailyCharge,
            int quantity, String condition) {

        this.equipmentId = equipmentId;
        this.equipmentName = equipmentName;
        this.category = category;
        this.dailyCharge = dailyCharge;
        this.quantity = quantity;
        this.condition = condition;
    }

    public int getEquipmentId() {
        return equipmentId;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public String getCategory() {
        return category;
    }

    public double getDailyCharge() {
        return dailyCharge;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getCondition() {
        return condition;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void displayEquipment() {

        System.out.println("Equipment ID: " + equipmentId);
        System.out.println("Name: " + equipmentName);
        System.out.println("Category: " + category);
        System.out.println("Daily Charge: Rs. " + dailyCharge);
        System.out.println("Quantity: " + quantity);
        System.out.println("Condition: " + condition);
    }
}
