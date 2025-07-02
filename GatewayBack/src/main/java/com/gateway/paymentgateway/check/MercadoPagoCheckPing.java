package com.gateway.paymentgateway.check;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MercadoPagoCheckPing {

    private final RestTemplate restTemplate;

    public MercadoPagoCheckPing() {
        this.restTemplate = new RestTemplate();
    }

    public boolean isOnline() {
        try{
            String url = "https://api.mercadopago.com/ping/";
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            return false;
        }
    }
}
