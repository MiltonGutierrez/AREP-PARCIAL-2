package eci.arep.parcial;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@CrossOrigin("*")
public class ProxyController {

    private static final String USER_AGENT = "Mozilla/5.0";
    private String[] servers = new String[]{"http://ec2-3-84-86-41.compute-1.amazonaws.com:8081/", "http://ec2-54-162-30-69.compute-1.amazonaws.com:8081/"};
    private int serverSelector = 0;

    @GetMapping("factors")
    public ResponseEntity<Object> calculateFactors(@RequestParam(value = "value") int value) {
        try {
            String URL = servers[serverSelector % 2] + "factors?value=" + value;
            serverSelector++;
            return new ResponseEntity<>(Map.of("operation", "factors", "input", String.valueOf(value), "output",
            getResponseFromServers(URL)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("primes")
    public ResponseEntity<Object> calculatePrimes(@RequestParam(value = "value") int value) {
        try {
            String URL = servers[serverSelector % 2] + "primes?value=" + value;
            serverSelector++;
            return new ResponseEntity<>(Map.of("operation", "primes", "input", String.valueOf(value), "output",
                    getResponseFromServers(URL)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    private String getResponseFromServers(String URL) throws IOException {
        URL obj = new URL(URL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);

        // The following invocation perform the connection implicitly before getting the
        // code
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            return response.toString();
        } else {
            return "GET request not worked";
        }
    }
}
