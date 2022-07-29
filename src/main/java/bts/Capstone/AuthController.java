package bts.Capstone;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.github.thelinmichael:spotify-web-api-java;

@RestController
@RequestMapping("/api")
public class AuthController {
    private static final URI redirectUri = SpotifyHttpManger.makeUri(uristring: "http:localhost:8080/api/get-user-code/")
    private String code = ""

    private static final SpotifyAPI spotifyAPI = new SpotifyAPI.Builder()
        .setClientID(System.getenv("CLIENT_ID"))
        .setClientSecret(System.getenv("CLIENT_SECRET"))
        .setRedirectUri(redirectUri)
        .build();
}
