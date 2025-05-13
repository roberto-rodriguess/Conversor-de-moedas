import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

record ConversaoMoeda(String base_code, String target_code, double conversion_result) {}

public class Conversor {
    private static final String BRL = "BRL"; // real
    private static final String USD = "USD"; // dólar
    private static final String EUR = "EUR"; // euro
    private static final String ARS = "ARS"; // peso argentino

    private static final String API_KEY = System.getenv("API_KEY"); // obtém a chave via variável de ambiente

    private Conversor() {}

    public static ConversaoMoeda realParaEuro(double valorEmDolar) {
        return buscar(BRL, EUR, valorEmDolar);
    }

    public static ConversaoMoeda euroParaReal(double valorEmEuro) {
        return buscar(EUR, BRL, valorEmEuro);
    }

    public static ConversaoMoeda dolarParaReal(double valorEmDolar) {
        return buscar(USD, BRL, valorEmDolar);
    }

    public static ConversaoMoeda dolarParaEuro(double valorDolar) {
        return buscar(USD, EUR, valorDolar);
    }

    public static ConversaoMoeda euroParaDolar(double valorEuro) {
        return buscar(EUR, USD, valorEuro);
    }

    public static ConversaoMoeda realParaDolar(double valorEmReal) {
        return buscar(BRL, USD, valorEmReal);
    }

    public static ConversaoMoeda dolarParaPesoArgentino(double valorEmDolar) {
        return buscar(USD, ARS, valorEmDolar);
    }

    public static ConversaoMoeda pesoArgentinoParaDolar(double valorEmPesoArgentino) {
        return buscar(ARS, USD, valorEmPesoArgentino);
    }

    private static ConversaoMoeda buscar(String base_code, String target_code, double valor) {

        String url = String.format("https://v6.exchangerate-api.com/v6/%s/pair/%s/%s/%s",
                                    API_KEY, base_code, target_code, valor);

        try {
            HttpRequest request = HttpRequest
                    .newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response = HttpClient
                    .newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
            if (!json.get("result").getAsString().equals("success")) {
                System.out.printf("Erro na resposta da API: %s", json );
                return null;
            }

            return new Gson().fromJson(response.body(), ConversaoMoeda.class);

        } catch (IOException | InterruptedException e) {
            System.out.printf("Erro ao consultar a API: %s", e.getMessage());
            return null;
        }
    }
}
