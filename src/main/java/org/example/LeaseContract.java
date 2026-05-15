package org.example;

import java.math.BigDecimal;

public class LeaseContract extends Contract {

    public LeaseContract(String contractDate, String customerName, String customerEmail, Vehicle vehicleSold) {
        super(contractDate, customerName, customerEmail, vehicleSold);
    }

    //expected ending value is 50% of the vehicle price
    public BigDecimal getExpectedEndingValue() {
        return getVehicleSold().getPrice()
                .multiply(BigDecimal.valueOf(0.50));
    }
//lease fee is 7% of the vehicle price.
    public BigDecimal getLeaseFee() {
        return getVehicleSold().getPrice()
                .multiply(BigDecimal.valueOf(0.07));
    }

    @Override
    public BigDecimal getTotalPrice() {
        return null;
    }

    @Override
    public BigDecimal getMonthlyPayment() {
        return null;
    }
}
