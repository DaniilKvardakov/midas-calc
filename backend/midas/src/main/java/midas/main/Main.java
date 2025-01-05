package midas.main;


import midas.util.CalcFarmUtil;

import java.io.IOException;
import java.net.URISyntaxException;


public class Main {
    public static void main(String[] args) throws URISyntaxException, IOException {

        new CalcFarmUtil().getFarmById("Sex metal designer", 8114496632L);
    }
}
