package com.conversor.main;

import com.conversor.models.convertionAPI;
import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        int option = 0;
        String baseCode;
        String targetCode;

        while (option != 7) {
            System.out.println("""
                    ******************************************************************
                    Sea bienvenido/a al Conversor de Moneda =)
                    
                    1) Dólar =>> Peso argentino
                    2) Peso argentino =>> Dólar
                    3) Dólar =>> Real brasileño
                    4) Real brasileño =>> Dólar
                    5) Dólar =>> Peso colombiano
                    6) Peso colombiano =>> Dólar
                    7) Salir
                    Elija una opción válida:
                    ******************************************************************
                    """);
            option = input.nextInt();

            switch (option) {
                case 1 -> {
                    baseCode = "USD";
                    targetCode = "ARS";
                }
                case 2 -> {
                    baseCode = "ARS";
                    targetCode = "USD";
                }
                case 3 -> {
                    baseCode = "USD";
                    targetCode = "BRL";
                }
                case 4 -> {
                    baseCode = "BRL";
                    targetCode = "USD";
                }
                case 5 -> {
                    baseCode = "USD";
                    targetCode = "COP";
                }
                case 6 -> {
                    baseCode = "COP";
                    targetCode = "USD";
                }
                case 7 -> {
                    continue;
                }

                default -> {
                    System.out.println("Opción no válida.\n");
                    continue;
                }
            }

            String APIKEY = System.getenv("API_KEY");
            if (APIKEY == null) {
                System.out.println("API_KEY no definida");
            }

            String BASE_URL = System.getenv("BASE_URL");

            System.out.println("Introduce la cantidad: ");
            int amount = input.nextInt();

            String url = BASE_URL +
                    APIKEY +
                    "/pair/" +
                    baseCode +
                    "/" + targetCode +
                    "/" + amount;
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            try {
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                String json = response.body();

                Gson gson = new Gson();
                convertionAPI currentCovertion = gson.fromJson(json, convertionAPI.class);
//                System.out.println(currentCovertion);

                System.out.println(amount + " " + baseCode + " equivale a " + currentCovertion.conversion_result() + " " +targetCode + "\n");

            } catch (Exception e) {
                System.out.println("Algo falló :(");
            }
        }
    }
}
