package bts.Capstone;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import io.github.cdimascio.dotenv.Dotenv;
import java.net.URI;


import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;


@RestController
public class AuthController {
    @GetMapping("/api")
	public String index() {
        Dotenv dotenv = Dotenv.configure().load();
        String clientID = dotenv.get("CLIENT_ID");
        String clientSecret = dotenv.get("CLIENT_SECRET");

        URI redirecturi = SpotifyHttpManager.makeUri("http:localhost:8080/api/get-user-code");
        String code = "";

        SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(clientID)
            .setClientSecret(clientSecret)
            .setRedirectUri(redirecturi)
            .build();
        

        ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials()
            .build();

        try {
            ClientCredentials clientCredentials = clientCredentialsRequest.execute();

            spotifyApi.setAccessToken(clientCredentials.getAccessToken());

            return "This worked";
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            return "This did not work lol";
        }


	}

    // public static String spotifyLogin() {
    //     final SpotifyApi spotifyApi = index();
    //     AuthorizationCodeUriRequest auth = spotifyApi.authorizationCodeUri()
    //         .scope("user-read-private, user-read-email, user-top-read")
    //         .show_dialog(true)
    //         .build();

    //     URI uri = auth.execute();
    //     return uri.toString();
    // }
    
    // public static void main(String[] args) {
    //     spotifyLogin();
    // }
}
