import com.google.gson.Gson;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true; // Bandera para controlar el ciclo

        while (continuar) { // Ciclo que mantiene el programa en ejecución
            // Mostrar el menú al usuario
            System.out.println("Bienvenid@ al Conversor de monedas :D");
            System.out.println("1. Dolares ==> Pesos Argentinos");
            System.out.println("2. Peso Argentino ==> Dolares");
            System.out.println("3. Dolar ==> Real Brasileño");
            System.out.println("4. Real Brasileño ==> Dolares");
            System.out.println("5. Dolar ==> Peso Colombiano");
            System.out.println("6. Peso Colombiano ==> Dolares");
            System.out.println("7. Salir");
            System.out.print("Ingrese el número de la divisa: ");

            int opcion = scanner.nextInt();
            String divisa = "";
            int convertir = 0;

            switch (opcion) {
                case 1:
                    divisa = "USD";
                    convertir = 1;
                    break;
                case 2:
                    divisa = "ARS";
                    convertir = 2;
                    break;
                case 3:
                    divisa = "USD";
                    convertir = 3;
                    break;
                case 4:
                    divisa = "BRL";
                    convertir = 4;
                    break;
                case 5:
                    divisa = "USD";
                    convertir = 5;
                    break;
                case 6:
                    divisa = "COP";
                    convertir = 6;
                    break;
                case 7:
                    System.out.println("Saliendo...");
                    continuar = false; // Cambia la bandera para salir del ciclo
                    break;
                default:
                    System.out.println("Opción inválida. Intenta de nuevo.");
                    continue; // Continúa al siguiente ciclo si la opción no es válida
            }

            if (!continuar) {
                break; // Si el usuario eligió salir, termina el ciclo.
            }

            // Llamar a la API para obtener las tasas de cambio
            URI API_URL = URI.create("https://v6.exchangerate-api.com/v6/427061b07078ff2c82de57db/latest/" + divisa);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(API_URL).build();

            try {
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                Moneda moneda = new Gson().fromJson(response.body(), Moneda.class);

                // Proceso de conversión según la opción seleccionada
                Map<String, Double> tasasDeCambio = moneda.conversion_rates;
                if (convertir == 1) {
                    Double valorEnPesosArgentinos = tasasDeCambio.get("ARS");
                    System.out.print("Ingrese la cantidad de " + divisa + ": ");
                    double cantidadDeDolares = scanner.nextDouble();
                    double valorFinal = cantidadDeDolares * valorEnPesosArgentinos;
                    System.out.println();
                    System.out.printf("%.2f %s son = %.2f Pesos Argentinos\n", cantidadDeDolares, divisa, valorFinal);
                    System.out.println();
                } else if (convertir == 2) {
                    Double valorEnDolares = tasasDeCambio.get("USD");
                    System.out.print("Ingrese la cantidad de " + divisa + ": ");
                    double cantidadDePesosArgentinos = scanner.nextDouble();
                    double valorFinal = cantidadDePesosArgentinos * valorEnDolares;
                    System.out.println();
                    System.out.printf("%.2f %s son = %.2f Dólares\n", cantidadDePesosArgentinos, divisa, valorFinal);
                    System.out.println();
                } else if (convertir == 3) {
                    Double valorEnRealBrasileño = tasasDeCambio.get("BRL");
                    System.out.print("Ingrese la cantidad de " + divisa + ": ");
                    double cantidadDeDolares = scanner.nextDouble();
                    double valorFinal = cantidadDeDolares * valorEnRealBrasileño;
                    System.out.println();
                    System.out.printf("%.2f %s son = %.2f Reales Brasileños\n", cantidadDeDolares, divisa, valorFinal);
                    System.out.println();
                } else if (convertir == 4) {
                    Double valorEnDolares = tasasDeCambio.get("USD");
                    System.out.print("Ingrese la cantidad de " + divisa + ": ");
                    double cantidadDeRealBrasileños = scanner.nextDouble();
                    double valorFinal = cantidadDeRealBrasileños * valorEnDolares;
                    System.out.println();
                    System.out.printf("%.2f %s son = %.2f Dólares\n", cantidadDeRealBrasileños, divisa, valorFinal);
                    System.out.println();
                } else if (convertir == 5) {
                    Double valorEnPesoColombiano = tasasDeCambio.get("COP");
                    System.out.print("Ingrese la cantidad de " + divisa + ": ");
                    double cantidadDeDolares = scanner.nextDouble();
                    double valorFinal = cantidadDeDolares * valorEnPesoColombiano;
                    System.out.println();
                    System.out.printf("%.2f %s son = %.2f Pesos Colombianos\n", cantidadDeDolares, divisa, valorFinal);
                    System.out.println();
                } else if (convertir == 6) {
                    Double valorEnDolares = tasasDeCambio.get("USD");
                    System.out.print("Ingrese la cantidad de " + divisa + ": ");
                    double cantidadDePesoColombiano = scanner.nextDouble();
                    double valorFinal = cantidadDePesoColombiano * valorEnDolares;
                    System.out.println();
                    System.out.printf("%.2f %s son = %.2f Dólares\n", cantidadDePesoColombiano, divisa, valorFinal);
                    System.out.println();
                } else {
                    System.out.println();
                    System.out.println("Opción inválida para convertir de " + divisa);
                    System.out.println();
                }
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
                System.out.println("Finalizando app");
            }
        }

        System.out.println("Gracias por usar el conversor de monedas.");
    }

    // Definición de la clase Moneda
    public record Moneda(String base_code, Map<String, Double> conversion_rates) {}
}
