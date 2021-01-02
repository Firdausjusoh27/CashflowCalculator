package com.example.cashflowapp;

public class PlayerInfoRecord {
    private String profession;
    private String dream;
    private String auditName;
    private int salary;
    private int cashOnHand;

    public String getProfession() { return profession; }

    public void setProfession(String profession) { this.profession = profession; }

    public String getDream() { return dream; }

    public void setDream(String dream) { this.dream = dream; }

    public String getAuditName() { return auditName; }

    public void setAuditName(String auditName) { this.auditName = auditName; }

    public int getSalary() { return salary; }

    public void setSalary(int salary) { this.salary = salary; }

    public int getCashOnHand() { return cashOnHand; }

    public void setCashOnHand(int cashOnHand) { this.cashOnHand = cashOnHand; }

//    @Override
//    public String toString() {
//        return UserContract.User.COLUMN_PHONE + ":" + this.phone + "," +
//                UserContract.User.COLUMN_NAME + ":" +
//                this.name + "," + UserContract.User.COLUMN_EMAIL + ":" + this.email;
//    }
}