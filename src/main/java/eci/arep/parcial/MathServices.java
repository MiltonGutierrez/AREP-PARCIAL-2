package eci.arep.parcial;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class MathServices {

    public String factors(int num) throws Exception {
        if(num <= 0){
            throw new Exception("Only positive integers allowed");
        }
        return convertToExpectedResponseString(getFactorsList(num));
    }

    public String primes(int num) throws Exception {
        if(num <= 0){
            throw new Exception("Only positive integers allowed");
        }
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 1; i < num; i++) {
            if(getFactorsList(i).size() == 1){
                list.add(i);
            }
        }
        return convertToExpectedResponseString(list);
    }

    private String convertToExpectedResponseString(List<Integer> list) {
        return list.toString().substring(1, list.toString().length() - 1);
    }

    private List<Integer> getFactorsList(int num){
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 1; i <= num; i++) {
            if (num % i == 0) {
                list.add(i);
            }
        }
        return list;
    }

}
