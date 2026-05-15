package org.example;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
//Total price = expected ending value + lease fee
    @Override
    public BigDecimal getTotalPrice() {
        return getExpectedEndingValue().add(getLeaseFee());
    }

//Monthly payment = total price divided by 36 months
    @Override
    public BigDecimal getMonthlyPayment() {
        return getTotalPrice()
                .divide(BigDecimal.valueOf(36), 2, RoundingMode.HALF_UP);
    }
}
