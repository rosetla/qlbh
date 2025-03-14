package model;

public class MonthlyRevenue {
    private int year;
    private int month;
    private int totalQuantity;
    private long totalRevenue;

    public MonthlyRevenue(int year, int month, int totalQuantity, long totalRevenue) {
        this.year = year;
        this.month = month;
        this.totalQuantity = totalQuantity;
        this.totalRevenue = totalRevenue;
    }

    // Getters và Setters
    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public long getTotalRevenue() {
        return totalRevenue;
    }
}
