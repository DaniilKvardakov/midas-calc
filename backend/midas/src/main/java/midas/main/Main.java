package midas.main;

import java.util.HashMap;
import java.util.Map;


public class Main {
    public static void main(String[] args) {
        HashMap<String, String> map = new HashMap<>(Map.of("S", "S"));
        System.out.println(map.get("SV"));
    }
}
