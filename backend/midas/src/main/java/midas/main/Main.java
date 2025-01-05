package midas.main;

import midas.util.CalcFarmUtil;

import java.io.IOException;
import java.net.URISyntaxException;


public class Main {
    public static void main(String[] args) throws URISyntaxException, IOException {
        new CalcFarmUtil().getFarmById();
    }
}
