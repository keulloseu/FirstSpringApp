package pl.kurs.java.firstspringapp.service;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pl.kurs.java.firstspringapp.model.Root;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class MoneyExchangeService {
    private Root body;
    private Map<String, BigDecimal> rates;
    private final Scanner myScanner;
    private boolean keepRunning;

    public MoneyExchangeService() {
        myScanner = new Scanner(System.in);
        RestTemplate restTemplate = new RestTemplate();
        body = getExchangeValuesFromAPI(restTemplate);
        rates = body.getRates();
    }

    public Root getExchangeValuesFromAPI(RestTemplate restTemplate) {
        ResponseEntity<Root> exchangeResponse = restTemplate
                .exchange("http://api.exchangeratesapi.io/v1/latest?access_key=5edfea0655a25a1841dbd74dbc0fc0f7&format=1",
                        HttpMethod.GET, null, Root.class);
        return exchangeResponse.getStatusCode() == HttpStatus.OK ? exchangeResponse.getBody() : new Root();
    }

    public void run() {
        System.out.println("Witaj w kantorze wymiany walut!!");
        do {
            System.out.println("Podaj ile jakiej waluty chcesz wymienić i na jaka?");
            System.out.println("Podaj wymieniana walute: ");
            String givenCurrency = myScanner.next();
            System.out.println("Podaj walute jaka chcesz uzyskac: ");
            String desiredCurrency = myScanner.next();
            try {
                System.out.println("Podaj ilosc waluty jaka masz: ");
                BigDecimal currencyAmount = myScanner.nextBigDecimal();
                System.out.println("Za: " + currencyAmount + givenCurrency + " mozesz uzyskac: "
                        + getDesiredCurrencyAmount(givenCurrency, desiredCurrency, currencyAmount) + desiredCurrency);
            } catch (InputMismatchException e) {
                System.out.println("podaj odpowiednia liczbe!!");
                continue;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            System.out.println("jesli chcesz zakonczyc dzialanie programu wcisnij [y], jesli chcesz wykonac kolejna operacje wcisnij dowolny inny przycisk");
            keepRunning = !myScanner.next().equalsIgnoreCase("y");
        } while (keepRunning);
    }

    private BigDecimal getDesiredCurrencyAmount(String givenCurrency, String desiredCurrency, BigDecimal currencyAmount) throws Exception {
        if (rates == null || rates.isEmpty() || !rates.containsKey(givenCurrency) || !rates.containsKey(desiredCurrency)) {
            throw new Exception("01");
        }
        return currencyAmount.divide(rates.get(givenCurrency), RoundingMode.HALF_DOWN).multiply(rates.get(desiredCurrency));
    }
}
