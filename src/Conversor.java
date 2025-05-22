import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

record ConversaoMoeda(@SerializedName("base_code") String moedaBase,
                      @SerializedName("target_code") String moedaAlvo,
                      @SerializedName("conversion_result") double resultado) {
}

public class Conversor {
    private static final String BRL = "BRL"; // real
    private static final String USD = "USD"; // dólar
    private static final String EUR = "EUR"; // euro
    private static final String ARS = "ARS"; // peso argentino

    private static final String API_KEY = System.getenv("API_KEY"); // obtém a chave via variável de ambiente

    private Conversor() {}

    public static ConversaoMoeda realParaDolar(double valorEmReal) {
        return buscar(BRL, USD, valorEmReal);
    }

    public static ConversaoMoeda dolarParaReal(double valorEmDolar) {
        return buscar(USD, BRL, valorEmDolar);
    }

    public static ConversaoMoeda realParaEuro(double valorEmDolar) {
        return buscar(BRL, EUR, valorEmDolar);
    }

    public static ConversaoMoeda euroParaReal(double valorEmEuro) {
        return buscar(EUR, BRL, valorEmEuro);
    }

    public static ConversaoMoeda dolarParaEuro(double valorEmDolar) {
        return buscar(USD, EUR, valorEmDolar);
    }

    public static ConversaoMoeda euroParaDolar(double valorEmEuro) {
        return buscar(EUR, USD, valorEmEuro);
    }

    public static ConversaoMoeda pesoArgentinoParaDolar(double valorEmPesoArgentino) {
        return buscar(ARS, USD, valorEmPesoArgentino);
    }

    public static ConversaoMoeda dolarParaPesoArgentino(double valorEmDolar) {
        return buscar(USD, ARS, valorEmDolar);
    }

    private static ConversaoMoeda buscar(String codigoBase, String codigoAlvo, double valor) {

        String url = "https://v6.exchangerate-api.com/v6/%s/pair/%s/%s/%s"
                        .formatted(API_KEY, codigoBase, codigoAlvo, valor);

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
