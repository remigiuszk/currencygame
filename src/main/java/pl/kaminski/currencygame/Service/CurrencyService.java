package pl.kaminski.currencygame.Service;

import com.fasterxml.jackson.databind.JsonNode;
import org.decimal4j.util.DoubleRounder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

@Service
public class CurrencyService {
    private String[] currencyList;
    private String randomCurrency;
    private Double currencyValue;
    private Random generator = new Random();

    public CurrencyService() {
        initTable();
        initGame();

    }

    private void initTable() {
        currencyList = new String[9];
        currencyList[0] = "USD";
        currencyList[1] = "EUR";
        currencyList[2] = "GBP";
        currencyList[3] = "CHF";
        currencyList[4] = "HUF";
        currencyList[5] = "RUB";
        currencyList[6] = "NOK";
        currencyList[7] = "HKD";
    }

    private double jsonCurrency(String foreignCurrency) {
        String url = "http://api.nbp.pl/api/exchangerates/rates/a/" + foreignCurrency + "/?format=json";
        RestTemplate restTemplate = new RestTemplate();
        double currency = 0;
            currency = restTemplate.getForObject(url, JsonNode.class).get("rates").get(0).get("mid").asDouble();




        return DoubleRounder.round(currency, 2);
    }

    private String generateRandomCurrency() {
        return currencyList[generator.nextInt(8)];
    }

    public void initGame() {
        randomCurrency = generateRandomCurrency();
        currencyValue = jsonCurrency(randomCurrency);
    }

    public String getRandomCurrency() {
        return randomCurrency;
    }

    public Double getCurrencyValue() {
        return currencyValue;
    }

}
