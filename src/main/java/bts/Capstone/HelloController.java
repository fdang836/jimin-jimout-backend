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

    @GetMapping("/spot")
    public String callAPI() {
        String uri = "https://zenquotes.io/api/random";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class); 
        return result;
    }
    
}
