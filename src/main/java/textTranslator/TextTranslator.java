package textTranslator;

import java.io.IOException;

import com.google.gson.*;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TextTranslator {
    // Chave de assinatura da API do Translator
    private static String key = "f87c8b3a2ba84bc38ce6b51320c7d15c";
    
    // Localização, também conhecida como região.
    // Necessário se você estiver usando um recurso multi-serviço ou regional (não global). Pode ser encontrado no portal Azure na página de Chaves e Endpoint.
    private static String location = "eastus2";

    // Instancia o cliente OkHttpClient.
    OkHttpClient client = new OkHttpClient();

    // Esta função realiza uma requisição POST.
    public String Post() throws IOException {
        // Define o tipo de mídia como JSON.
        MediaType mediaType = MediaType.parse("application/json");
        @SuppressWarnings("deprecation")
        // Cria o corpo da requisição com o texto a ser traduzido.
        RequestBody body = RequestBody.create(mediaType, 
                "[{\"Text\": \"I would really like to drive your car around the block a few times!\"}]");
        // Cria a requisição com os cabeçalhos necessários.
        Request request = new Request.Builder()
                // URL da API de tradução com parâmetros de versão e idiomas.
                .url("https://api.cognitive.microsofttranslator.com/translate?api-version=3.0&from=en&to=pt&to=fr&to=es")
                .post(body)
                // Cabeçalho com a chave de assinatura da API.
                .addHeader("Ocp-Apim-Subscription-Key", key)
                // Cabeçalho com a localização, necessário para recursos regionais.
                .addHeader("Ocp-Apim-Subscription-Region", location) 
                // Cabeçalho que define o tipo de conteúdo como JSON.
                .addHeader("Content-type", "application/json")
                .build();
        // Executa a requisição e obtém a resposta.
        Response response = client.newCall(request).execute();
        // Retorna o corpo da resposta como uma string.
        return response.body().string();
    }

    // Esta função formata o texto JSON da resposta de forma legível.
    public static String prettify(String json_text) {
        JsonParser parser = new JsonParser();
        // Analisa o texto JSON.
        JsonElement json = parser.parse(json_text);
        // Cria um objeto Gson com formatação bonita.
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        // Retorna o JSON formatado.
        return gson.toJson(json);
    }
    
    public static void main(String[] args) {
        try {
            // Cria uma instância da classe TextTranslator.
            TextTranslator translateRequest = new TextTranslator();
            // Chama a função Post para obter a resposta da tradução.
            String response = translateRequest.Post();
            // Exibe a resposta formatada.
            System.out.println(prettify(response));
        } catch (Exception e) {
            // Exibe qualquer exceção que ocorrer.
            System.out.println(e);
        }
    }
}
