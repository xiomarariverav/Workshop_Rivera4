package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ContractFileManager {

    private static final String filePath2 = "src/main/resources/contracts.csv";

    // This method saves ANY type of contract (SalesContract or LeaseContract)
    public void saveContract(Contract contract) {

        try {
            // Without true it would overwrite the file
            FileWriter fileWriter = new FileWriter(filePath2, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // Grab the vehicle from the contract
            Vehicle vehicle = contract.getVehicleSold();

            String line = "";

            //If it is a SalesContract, treat it like one
            if (contract instanceof SalesContract sale) {

                String financeOption;

                // Converts true or false into YES or NO for the file
                if (sale.isFinance()) {
                    financeOption = "YES";
                } else {
                    financeOption = "NO";
                }

//SALE|20210928|Dana Wyatt|dana@texas.com|10112|1993|Ford|Explorer|SUV|Red|525123|995.00|49.75|100.00|295.00|1439.75|NO|0.00
                line = "SALE" + "|" +
                        sale.getContractDate() + "|" +
                        sale.getCustomerName() + "|" +
                        sale.getCustomerEmail() + "|" +
                        vehicle.getVin() + "|" +
                        vehicle.getYear() + "|" +
                        vehicle.getMake() + "|" +
                        vehicle.getModel() + "|" +
                        vehicle.getVehicleType() + "|" +
                        vehicle.getColor() + "|" +
                        vehicle.getOdometer() + "|" +
                        vehicle.getPrice() + "|" +
                        sale.getSalesTaxAmount() + "|" +
                        sale.getRecordingFee() + "|" +
                        sale.getProcessingFee() + "|" +
                        sale.getTotalPrice() + "|" +
                        financeOption + "|" +
                        sale.getMonthlyPayment();

//If it is a LeaseContract, treat it like one
            } else if (contract instanceof LeaseContract lease) {

//LEASE|20210928|Zachary Westly|zach@texas.com|37846|2021|Chevrolet|Silverado|truck|Black|2750|31995.00|15997.50|2239.65| 18237.15| 540.72
                line = "LEASE" + "|" +
                        lease.getContractDate() + "|" +
                        lease.getCustomerName() + "|" +
                        lease.getCustomerEmail() + "|" +
                        vehicle.getVin() + "|" +
                        vehicle.getYear() + "|" +
                        vehicle.getMake() + "|" +
                        vehicle.getModel() + "|" +
                        vehicle.getVehicleType() + "|" +
                        vehicle.getColor() + "|" +
                        vehicle.getOdometer() + "|" +
                        vehicle.getPrice() + "|" +
                        lease.getExpectedEndingValue() + "|" +
                        lease.getLeaseFee() + "|" +
                        lease.getTotalPrice() + "|" +
                        lease.getMonthlyPayment();
            }

            bufferedWriter.write(line);
            bufferedWriter.newLine();

            bufferedWriter.close();

        } catch (IOException e) {
            System.out.println("Error saving contract: " + e.getMessage());
        }
    }
}