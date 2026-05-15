package org.example;

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


    @Override
    public double getTotalPrice() {
        return 0;
    }

    @Override
    public double getMonthlyPayment() {
        return 0;
    }
}
