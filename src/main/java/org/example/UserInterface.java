package org.example;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class UserInterface {
    static Scanner scanner = new Scanner(System.in);
    static Dealership dealership;

    private static void init() {
        dealership = DealershipFileManager.getDealership();
        if (dealership == null) {
            System.out.println("Failed to load dealership. Check that the file exists at: " + DealershipFileManager.filePath);
            System.exit(1);
        }
    }

    public static void display() {
        init();

        int command = 0;
        while (command != 99) {
            System.out.println("\n===== " + dealership.getName() + " =====");
            System.out.println("1 - Find vehicles within a price range");
            System.out.println("2 - Find vehicles by make/model");
            System.out.println("3 - Find vehicles by year range");
            System.out.println("4 - Find vehicles by color");
            System.out.println("5 - Find vehicles by mileage range");
            System.out.println("6 - Find vehicles by type");
            System.out.println("7 - List ALL vehicles");
            System.out.println("8 - Add a vehicle");
            System.out.println("9 - Remove a vehicle");
            System.out.println("10 - Sell a vehicle");
            System.out.println("11 - Lease a vehicle");
            System.out.println("99 - Quit");
            System.out.print("Enter command: ");

            command = Integer.parseInt(scanner.nextLine().trim());

            switch (command) {
                case 1:
                    processGetByPriceRequest();
                    break;

                case 2:
                    processGetByMakeModelRequest();
                    break;

                case 3:
                    processGetByYearRequest();
                    break;

                case 4:
                    processGetByColorRequest();
                    break;

                case 5:
                    processGetByMileageRequest();
                    break;

                case 6:
                    processByVehicleTypeRequest();
                    break;

                case 7:
                    processGetAllVehicleRequest();
                    break;

                case 8:
                    processAddVehicleRequest();
                    break;

                case 9:
                    processRemoveVehicleRequest();
                    break;

                case 10:
                    processSaleRequest();
                    break;

                case 11:
                    processLeaseRequest();
                    break;

                case 99:
                    System.out.println("Goodbye!");
                    break;

                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }

    public static void processLeaseRequest() {
        System.out.print("Enter VIN of vehicle to lease: ");
        int vin = Integer.parseInt(scanner.nextLine().trim());

        Vehicle vehicle = null;

        for (Vehicle v : dealership.getAllVehicles()) {
            if (v.getVin() == vin) {
                vehicle = v;
                break;
            }
        }

        if (vehicle == null) {
            System.out.println("Vehicle with VIN " + vin + " not found.");
            return;
        }

        System.out.print("Enter contract date YYYYMMDD: ");
        String date = scanner.nextLine().trim();

        System.out.print("Enter customer name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter customer email: ");
        String email = scanner.nextLine().trim();

        LeaseContract leaseContract = new LeaseContract(date, name, email, vehicle);

        ContractFileManager contractFileManager = new ContractFileManager();
        contractFileManager.saveContract(leaseContract);

        dealership.removeVehicle(vin);
        DealershipFileManager.saveDealership(dealership);

        System.out.println("Lease contract saved and vehicle removed from inventory.");
    }

    public static void processSaleRequest() {
        System.out.print("Enter VIN of vehicle to sell: ");
        int vin = Integer.parseInt(scanner.nextLine().trim());

        Vehicle vehicle = null;

        for (Vehicle v : dealership.getAllVehicles()) {
            if (v.getVin() == vin) {
                vehicle = v;
                break;
            }
        }

        if (vehicle == null) {
            System.out.println("Vehicle with VIN " + vin + " not found.");
            return;
        }

        System.out.print("Enter contract date YYYYMMDD: ");
        String date = scanner.nextLine().trim();

        System.out.print("Enter customer name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter customer email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Finance? YES or NO: ");
        String financeAnswer = scanner.nextLine().trim();

        boolean finance = financeAnswer.equalsIgnoreCase("YES");

        SalesContract salesContract = new SalesContract(date, name, email, vehicle, finance);

        ContractFileManager contractFileManager = new ContractFileManager();
        contractFileManager.saveContract(salesContract);

        dealership.removeVehicle(vin);
        DealershipFileManager.saveDealership(dealership);

        System.out.println("Sale contract saved and vehicle removed from inventory.");
    }


    private static void displayVehicles(List<Vehicle> vehicles) {
        if (vehicles == null || vehicles.isEmpty()) {
            System.out.println("No vehicles found.");
            return;
        }
        System.out.println("\nVIN    | Year | Make       | Model      | Type   | Color  | Mileage | Price");
        System.out.println("-------|------|------------|------------|--------|--------|---------|----------");
        for (Vehicle v : vehicles) {
            System.out.printf("%-7d| %-5d| %-11s| %-11s| %-7s| %-7s| %-8d| $%.2f%n",
                    v.getVin(), v.getYear(), v.getMake(), v.getModel(),
                    v.getVehicleType(), v.getColor(), v.getOdometer(), v.getPrice());
        }
    }

    public static void processGetByPriceRequest() {
        System.out.print("Enter minimum price: ");
        BigDecimal min = new BigDecimal(scanner.nextLine().trim());

        System.out.print("Enter maximum price: ");
        BigDecimal max = new BigDecimal(scanner.nextLine().trim());

        displayVehicles(dealership.getVehiclesByPrice(min, max));
    }

    public static void processGetByMakeModelRequest() {
        System.out.print("Enter make: ");
        String make = scanner.nextLine().trim();
        System.out.print("Enter model: ");
        String model = scanner.nextLine().trim();
        displayVehicles(dealership.getVehiclesByMakeModel(make, model));
    }

    public static void processGetByYearRequest() {
        System.out.print("Enter minimum year: ");
        int min = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Enter maximum year: ");
        int max = Integer.parseInt(scanner.nextLine().trim());
        displayVehicles(dealership.getVehiclesByYear(min, max));
    }

    public static void processGetByColorRequest() {
        System.out.print("Enter color: ");
        String color = scanner.nextLine().trim();
        displayVehicles(dealership.getVehiclesByColor(color));
    }

    public static void processGetByMileageRequest() {
        System.out.print("Enter minimum mileage: ");
        int min = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Enter maximum mileage: ");
        int max = Integer.parseInt(scanner.nextLine().trim());
        displayVehicles(dealership.getVehiclesByMileage(min, max));
    }

    public static void processByVehicleTypeRequest() {
        System.out.print("Enter vehicle type (Sedan, Truck, SUV, Van): ");
        String type = scanner.nextLine().trim();
        displayVehicles(dealership.getVehiclesByType(type));
    }

    public static void processGetAllVehicleRequest() {
        displayVehicles(dealership.getAllVehicles());
    }

    public static void processAddVehicleRequest() {
        System.out.print("Enter VIN: ");
        int vin = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Enter year: ");
        int year = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Enter make: ");
        String make = scanner.nextLine().trim();

        System.out.print("Enter model: ");
        String model = scanner.nextLine().trim();

        System.out.print("Enter vehicle type: ");
        String type = scanner.nextLine().trim();

        System.out.print("Enter color: ");
        String color = scanner.nextLine().trim();

        System.out.print("Enter odometer reading: ");
        int odometer = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Enter price: ");
        BigDecimal price = new BigDecimal(scanner.nextLine().trim());

        Vehicle vehicle = new Vehicle(vin, year, make, model, type, color, odometer, price);
        dealership.addVehicle(vehicle);

        DealershipFileManager.saveDealership(dealership);
        System.out.println("Vehicle added successfully!");
    }

    public static void processRemoveVehicleRequest() {
        System.out.print("Enter VIN of vehicle to remove: ");
        int vin = Integer.parseInt(scanner.nextLine().trim());

        if (dealership.removeVehicle(vin)) {
            DealershipFileManager.saveDealership(dealership);
            System.out.println("Vehicle removed successfully!");
        } else {
            System.out.println("Vehicle with VIN " + vin + " not found.");
        }
    }
}