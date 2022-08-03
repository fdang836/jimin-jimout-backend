package bts.Capstone;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import se.michaelthelin.spotify.SpotifyApi;
// import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import io.github.cdimascio.dotenv.Dotenv;
// import java.net.URI;

import org.apache.hc.core5.http.ParseException;
import java.io.IOException;

import java.util.ArrayList;

import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import se.michaelthelin.spotify.model_objects.specification.AudioFeatures;
import se.michaelthelin.spotify.requests.data.tracks.GetAudioFeaturesForTrackRequest;

import se.michaelthelin.spotify.model_objects.specification.Playlist;
import se.michaelthelin.spotify.requests.data.playlists.GetPlaylistRequest;

import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.playlists.GetPlaylistsItemsRequest;

import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.model_objects.specification.Episode;

@RestController
public class AuthController {
    @GetMapping("/api")
	public String index() {
        Dotenv dotenv = Dotenv.configure().load();
        String clientID = dotenv.get("CLIENT_ID");
        String clientSecret = dotenv.get("CLIENT_SECRET");

        // URI redirecturi = SpotifyHttpManager.makeUri("http:localhost:8080/api/get-user-code");
        String code = "";

        SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(clientID)
            .setClientSecret(clientSecret)
            .build();
        

        ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials()
            .build();


        try {
            ClientCredentials clientCredentials = clientCredentialsRequest.execute();
            spotifyApi.setAccessToken(clientCredentials.getAccessToken());
            System.out.println("Token " + clientCredentials.getAccessToken());
            System.out.println("the api call worked");

            // GetAudioFeaturesForTrackRequest getAudioFeaturesForTrackRequest = spotifyApi
            //     .getAudioFeaturesForTrack("6fqaMyg066xlukvUJWdM2T")
            //     .build();
            // AudioFeatures audioFeatures = getAudioFeaturesForTrackRequest.execute();
            // System.out.println(audioFeatures);


            
            // GetPlaylistRequest getPlaylistRequest = spotifyApi.getPlaylist("22CFflpj6LBfzI3g5ykVvR")
            //     .build();
            // Playlist playlist = getPlaylistRequest.execute();
            // System.out.println("Name: " + playlist.getName());

            GetPlaylistsItemsRequest getPlaylistsItemsRequest = spotifyApi
                .getPlaylistsItems("22CFflpj6LBfzI3g5ykVvR")
                .build();
            Paging<PlaylistTrack> playlistTrackPaging = getPlaylistsItemsRequest.execute();
            System.out.println("Total: " + playlistTrackPaging.getTotal());
            System.out.println("Track's first artist: " + ((Track) playlistTrackPaging.getItems()[0].getTrack()).getArtists()[0]);
            System.out.println("Track: " + playlistTrackPaging.getItems()[0].getTrack());


            return "this work";

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
            return "this didn't work";
        }


	}

}
