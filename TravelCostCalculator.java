// EG/2020/3947 GUNATHILAKE A.P.B.

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class TravelCostCalculator {
    static Map<String, Double> hotelRateMap = new HashMap<>();
    static Map<String, Double> currencyRateMap = new HashMap<>();
    static Map<String, Double> flightCostMap = new HashMap<>();

    static void HotelRates(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String index; 
        while ((index = reader.readLine()) != null) {
            String[] inputsList = index.split(",");
            hotelRateMap.put(inputsList[0].toUpperCase(), Double.parseDouble(inputsList[1]));
        }
    }

    static void ExchangeRates(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String index;
        while ((index = reader.readLine()) != null) {
            String[] inputsList = index.split(",");
            currencyRateMap.put(inputsList[0].toUpperCase(), Double.parseDouble(inputsList[1]));
        }
    }

    static void FlightCosts(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String index;
        while ((index = reader.readLine()) != null) {
            String[] inputsList = index.split(",");
            flightCostMap.put(inputsList[0].toUpperCase(), Double.parseDouble(inputsList[1]));
        }
    }

    public static void main(String[] args) {
        try {
            HotelRates("data/hotel_rates.csv");
            ExchangeRates("data/exchange_rates.csv");
            FlightCosts("data/flight_costs.csv");

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Enter your destination: ");
            String destination = reader.readLine().toUpperCase();

            double flightCost = flightCostMap.getOrDefault(destination, 0.0);
            double hotelCost = hotelRateMap.getOrDefault(destination, 0.0);

            System.out.print("Enter your stay duration in days: ");
            int stayDuration = Integer.parseInt(reader.readLine());
            hotelCost *= stayDuration;

            double totalCostUsd = flightCost + hotelCost;

            System.out.printf("Flight cost: USD %.2f\n", flightCost);
            System.out.printf("Hotel cost (%d days): USD %.2f\n", stayDuration, hotelCost);
            System.out.printf("Total: USD %.2f\n", totalCostUsd);

            String[] availableCurrencies = currencyRateMap.keySet().toArray(new String[0]);
            System.out.print("Select your currency for final price estimation(" + String.join(", ", availableCurrencies) + "): ");
            String selectedCurrency = reader.readLine();

            double finalPriceLocalCurrency = totalCostUsd * currencyRateMap.get(selectedCurrency);

            System.out.printf("Total in %s: %.2f\n", selectedCurrency, finalPriceLocalCurrency);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
