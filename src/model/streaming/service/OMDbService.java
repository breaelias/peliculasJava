package model.streaming.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONObject;

import model.streaming.content.Movie;

public class OMDbService {
    
	// Reemplazá con tu API Key obtenida en https://www.omdbapi.com/apikey.aspx
 private static final String API_KEY = "TU_API_KEY"; 

    public static Movie buscarPelicula(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) return null;

        try {
            // Armar la URL de consulta (encodear espacios con '+')
            String url = "https://www.omdbapi.com/?t=" + titulo.trim().replace(" ", "+") + "&apikey=" + API_KEY + "&plot=full";

         // Crear cliente y solicitud
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

         // Enviar solicitud
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

         // Procesar respuesta JSON
            JSONObject json = new JSONObject(response.body());

            // verificamos si se encontro la pelicula
            if (json.has("Response") && json.getString("Response").equals("True")) {
                Movie movie = new Movie();
                movie.setTitle(json.getString("Title"));
                
                // parseamos el año por si trae algo mas
                String yearStr = json.getString("Year");
                try {
                    movie.setYear(Integer.parseInt(yearStr.substring(0, 4)));
                } catch (Exception e) { movie.setYear(0); }

                movie.setSynopsis(json.getString("Plot"));
                
                if (json.has("Poster") && !json.getString("Poster").equals("N/A")) {
                    movie.setPoster(json.getString("Poster"));
                }
                
                return movie;
            } 
        } catch (Exception e) {
            System.err.println("Error consultando OMDb: " + e.getMessage());
        }
        return null; // Retorna null si no se encontró o hubo error
    }
}