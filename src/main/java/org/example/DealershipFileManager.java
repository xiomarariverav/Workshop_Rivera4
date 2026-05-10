package org.example;

import java.io.*;

public class DealershipFileManager {
    public static final String filePath = "src/main/resources/Dealership";

    public static Dealership getDealership() {

        Dealership dealership = null;

        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            // First line is dealership info
            String firstLine = bufferedReader.readLine();
            if (firstLine != null) {
                String[] info = firstLine.split("\\|");
                dealership = new Dealership(info[0], info[1], info[2]);
            }

            // Remaining lines are vehicles
            String input;


            while ((input = bufferedReader.readLine()) != null) {

                //If this line is empty, skip it
                if (input.trim().isEmpty()) {
                    continue;
                }

                String[] csvRow = input.split("\\|");

                //if row doesn’t have exactly 8 values, report it and ignore it
                if (csvRow.length != 8) {
                    System.out.println("Invalid row format, expected 8 columns: " + input);
                    continue;
                }

                Integer vin = Integer.parseInt(csvRow[0]);
                Integer year = Integer.parseInt(csvRow[1]);
                String make = csvRow[2];
                String model = csvRow[3];
                String vehicleType = csvRow[4];
                String color = csvRow[5];
                Integer odometer = Integer.parseInt(csvRow[6]);
                double price = Double.parseDouble(csvRow[7]);

                Vehicle vehicle = new Vehicle(vin, year, make, model, vehicleType, color, odometer, price);
                dealership.addVehicle(vehicle);
            }
            bufferedReader.close();

        } catch (IOException ex) {
            System.out.println("There was a problem with the file.");
        }
        return dealership;
    }
    public static void saveDealership(Dealership dealership) {
        try {
            FileWriter fileWriter = new FileWriter(filePath);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // Write dealership header line
            bufferedWriter.write(dealership.getName() + "|" +
                    dealership.getAddress() + "|" +
                    dealership.getPhone());
            bufferedWriter.newLine();

            // Write each vehicle
            for (Vehicle v : dealership.getAllVehicles()) {
                bufferedWriter.write(
                        v.getVin() + "|" +
                                v.getYear() + "|" +
                                v.getMake() + "|" +
                                v.getModel() + "|" +
                                v.getVehicleType() + "|" +
                                v.getColor() + "|" +
                                v.getOdometer() + "|" +
                                v.getPrice()
                );
                bufferedWriter.newLine();
            }

            bufferedWriter.close();

        } catch (IOException ex) {
            System.out.println("There was a problem saving the file.");
        }
    }
}