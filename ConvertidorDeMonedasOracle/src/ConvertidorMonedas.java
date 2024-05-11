import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class ConvertidorMonedas {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("*** Eija la opción deseada ***");
            System.out.println("1 - Peso Mexicano =>> Dólar Estadounidense");
            System.out.println("2 - Dólar Estadounidense =>> Peso Mexicano");
            System.out.println("3 - Peso Mexicano =>> Dólar Canadiense");
            System.out.println("4 - Dólar Canadiense =>> Peso Mexicano");
            System.out.println("5 - Yen Japonés =>> Dólar Estadounidense");
            System.out.println("6 - Dólar Estadounidense=>> Yen Japonés");
            System.out.println("7 - Peso Colombiano =>> Peso Argentino");
            System.out.println("8 - Peso Argentino =>> Peso Colombiano");
            System.out.println("9 - Salir");
            System.out.print("Opción: ");

            int opcion = scanner.nextInt();
            if (opcion == 9) {
                System.out.println("¡Gracias por usar nuestro conversor!");
                break;
            }

            switch (opcion) {
                case 1:
                    convertirMoneda("MXN", "USD", scanner);
                    break;
                case 2:
                    convertirMoneda("USD", "MXN", scanner);
                    break;
                case 3:
                    convertirMoneda("MXN", "CAD", scanner);
                    break;
                case 4:
                    convertirMoneda("CAD", "MXN", scanner);
                    break;
                case 5:
                    convertirMoneda("JPY", "USD", scanner);
                    break;
                case 6:
                    convertirMoneda("USD", "JPY", scanner);
                    break;
                case 7:
                    convertirMoneda("COP", "ARS", scanner);
                    break;
                case 8:
                    convertirMoneda("ARS", "COP", scanner);
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
        scanner.close();
    }

    public static void convertirMoneda(String from, String to, Scanner scanner) {
        System.out.print("Ingrese la cantidad de " + from + " que desea convertir a " + to + ": ");
        double cantidad = scanner.nextDouble();

        try {
            URL url = new URL("https://v6.exchangerate-api.com/v6/7658e82a903bc8e781b1c4dd/latest/" + from);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            int status = con.getResponseCode();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            con.disconnect();

            String response = content.toString();
            int startIndex = response.indexOf("\"" + to + "\":");
            if (startIndex != -1) {
                startIndex += to.length() + 3;
                int endIndex = response.indexOf(",", startIndex);
                String rateString = response.substring(startIndex, endIndex);
                double rate = Double.parseDouble(rateString);
                double resultado = cantidad * rate;
                System.out.println(cantidad + " " + from + " = " + resultado + " " + to);
            } else {
                System.out.println("Lo sentimos no encontramos el valor de conversión para " + to);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

