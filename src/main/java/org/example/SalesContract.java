package org.example;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
    public BigDecimal getSalesTaxAmount() {
        return getVehicleSold().getPrice()
                .multiply(BigDecimal.valueOf(0.05));
    }

    //Recording fee is always $100
    public BigDecimal getRecordingFee() {
        return BigDecimal.valueOf(100.00);
    }

    //$295 if vehicle price is under $10,000
    //$495 for all others
    public BigDecimal getProcessingFee() {
        if (getVehicleSold().getPrice() .compareTo(BigDecimal.valueOf(10000)) < 0) {

            return BigDecimal.valueOf(295.00);
        } else {
            return BigDecimal.valueOf(495.00);
        }
    }
    //vehicle price + sales tax + recording fee + processing fee
    @Override
    public BigDecimal getTotalPrice() {
        return getVehicleSold().getPrice().add(getSalesTaxAmount()).add(getRecordingFee()).add(getProcessingFee());
    }

    //If they said no return 0
    //If they said yes, then check vehicle price: If price is $10,000 or more: 4.25% for 48 months or If price is under $10,000: 5.25% for 24 months
    @Override
    public BigDecimal getMonthlyPayment() {
        if (!finance) {
            return BigDecimal.ZERO;
        }

        if (getVehicleSold().getPrice() .compareTo(BigDecimal.valueOf(10000)) >= 0) {

            return getTotalPrice()
                    .multiply(BigDecimal.valueOf(1.0425)).divide(BigDecimal.valueOf(48), 2, RoundingMode.HALF_UP);

        } else {

            return getTotalPrice().multiply(BigDecimal.valueOf(1.0525)).divide(BigDecimal.valueOf(24), 2, RoundingMode.HALF_UP);
        }
    }
}
