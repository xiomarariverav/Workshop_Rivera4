package org.example;

import java.math.BigDecimal;

public class SalesContract extends Contract{

    private boolean finance;
    public SalesContract(String contractDate, String customerName, String customerEmail, Vehicle vehicleSold, boolean finance) {
        super(contractDate, customerName, customerEmail, vehicleSold);
        this.finance = finance;
    }
    public boolean isFinance() {
        return finance;
    }

    public void setFinance(boolean finance) {
        this.finance = finance;
    }

    //Sales tax is 5% of vehicle price
    public double getSalesTaxAmount() {
        return getVehicleSold().getPrice() * 0.05;
    }

    //Recording fee is always $100
    public double getRecordingFee() {
        return 100.00;
    }

    //$295 if vehicle price is under $10,000
    //$495 for all others
    public double getProcessingFee() {
        if (getVehicleSold().getPrice() < 10000) {
            return 295.00;
        } else {
            return 495.00;
        }
    }
    //vehicle price + sales tax + recording fee + processing fee
    @Override
    public double getTotalPrice() {
        return getVehicleSold().getPrice() + getSalesTaxAmount() + getRecordingFee() + getProcessingFee();
    }

    //If they said no return 0
    //If they said yes, then check vehicle price: If price is $10,000 or more: 4.25% for 48 months or If price is under $10,000: 5.25% for 24 months
    @Override
    public double getMonthlyPayment() {
        if (!finance) {
            return 0;
        }

        if (getVehicleSold().getPrice() >= 10000) {
            return (getTotalPrice() * 1.0425) / 48;
        } else {
            return (getTotalPrice() * 1.0525) / 24;
        }
    }
}
