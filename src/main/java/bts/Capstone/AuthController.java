package bts.Capstone;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import io.github.cdimascio.dotenv.Dotenv;

import org.springframework.util.SystemPropertyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.apache.hc.core5.http.ParseException;
import java.io.IOException;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import se.michaelthelin.spotify.model_objects.specification.AudioFeatures;
import se.michaelthelin.spotify.requests.data.tracks.GetAudioFeaturesForTrackRequest;

import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;
import se.michaelthelin.spotify.requests.data.playlists.GetPlaylistsItemsRequest;

import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.tracks.GetTrackRequest;
import se.michaelthelin.spotify.model_objects.specification.AlbumSimplified;
import se.michaelthelin.spotify.model_objects.specification.Image;
import se.michaelthelin.spotify.model_objects.specification.ArtistSimplified;





@RestController
public class AuthController {
    @CrossOrigin(origins = "*")
    @GetMapping("/api")
	public ArrayList index() {
        //Secret keys
        // Dotenv dotenv = Dotenv.configure().load();
        // String clientID = dotenv.get("CLIENT_ID");
        // String clientSecret = dotenv.get("CLIENT_SECRET");
        String clientID = System.getenv("CLIENT_ID");
        String clientSecret = System.getenv("CLIENT_SECRET");


        //Sending secret keys to Spotify API
        SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(clientID)
            .setClientSecret(clientSecret)
            .build();
        
        //Building credentials
        ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials()
            .build();


        try {
            //Get access token and set to Spotify API
            ClientCredentials clientCredentials = clientCredentialsRequest.execute();
            spotifyApi.setAccessToken(clientCredentials.getAccessToken());
            
            String playlistID = System.getenv("PLAYLIST_ID");
            //Get items within a playlist
            GetPlaylistsItemsRequest getPlaylistsItemsRequest = spotifyApi
                .getPlaylistsItems(playlistID)
                .build();
            Paging<PlaylistTrack> playlistTrackPaging = getPlaylistsItemsRequest.execute();

            ArrayList data = new ArrayList();


            for (int i = 0; i < 67; i++) {
                Map<String, String> dictionary = new HashMap<String, String>();
                //Accessing different values in nested data structure from get items within a playlist
                String id = ((Track) playlistTrackPaging.getItems()[i].getTrack()).getId().toString();
                String trackName = ((Track) playlistTrackPaging.getItems()[i].getTrack()).getName().toString();
                String artist = ((Track) playlistTrackPaging.getItems()[i].getTrack()).getArtists()[0].getName().toString();
                String image = ((Track) playlistTrackPaging.getItems()[i].getTrack()).getAlbum().getImages()[0].getUrl();
                String preview = ((Track) playlistTrackPaging.getItems()[i].getTrack()).getPreviewUrl();
                

                //Get the audio feature for each playlist
                GetAudioFeaturesForTrackRequest getAudioFeaturesForTrackRequest = spotifyApi
                    .getAudioFeaturesForTrack(id)
                    .build();
                AudioFeatures audioFeatures = getAudioFeaturesForTrackRequest.execute();
                // System.out.println(audioFeatures.getValence());
                String valenceData = audioFeatures.getValence().toString();
                //Add data in hashmap 
                dictionary.put("id", id);
                dictionary.put("trackName", trackName);
                dictionary.put("valence", valenceData);
                dictionary.put("artist", artist);
                dictionary.put("imageUrl", image);
                dictionary.put("previewUrl", preview);

                //Add hashmap to arraylist to get returned
                data.add(dictionary);
                
            }

            return data;


        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
	}


}
