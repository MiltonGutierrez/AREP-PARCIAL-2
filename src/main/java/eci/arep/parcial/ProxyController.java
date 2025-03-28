package eci.arep.parcial;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ProxyController{


    
    @GetMapping("factors")
    public ResponseEntity<Object> calculateFactors(@RequestParam(value="value") int value){
        try {
            return new ResponseEntity<>(Map.of("operation", "factors", "input", String.valueOf(value), "output", mathServices.factors(value)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("primes")
    public ResponseEntity<Object> calculatePrimes(@RequestParam(value="value") int value){
        try {
            return new ResponseEntity<>(Map.of("operation", "factors", "input", String.valueOf(value), "output", mathServices.primes(value)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        
    }
}
