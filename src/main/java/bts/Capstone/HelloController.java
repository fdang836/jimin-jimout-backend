package bts.Capstone;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HelloController {
    @GetMapping("/hello")
	public String index() {
		return "Greetings from Spring Boot!";
	}

    /*
    @GetMapping("/api")
    public String callAPI() {
        String uri = "https://api.spotify.com/v1/audio-features/345";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class); 
        return result;
    }
     */


    
    // @GetMapping("/stuff")
    // public String callAPI() {
    //     String uri = "https://zenquotes.io/api/random";
    //     RestTemplate restTemplate = new RestTemplate();
    //     String result = restTemplate.getForObject(uri, String.class); 
    //     return result;
    // }
    

    

    
    
}
