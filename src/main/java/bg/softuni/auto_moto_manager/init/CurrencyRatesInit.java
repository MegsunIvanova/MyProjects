package bg.softuni.auto_moto_manager.init;

import bg.softuni.auto_moto_manager.configuration.OpenExchangeRatesConfig;
import bg.softuni.auto_moto_manager.model.dto.binding.ExchangeRatesDTO;
import bg.softuni.auto_moto_manager.service.CurrencyService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class CurrencyRatesInit implements CommandLineRunner {
    private final OpenExchangeRatesConfig openExchangeRatesConfig;
    private final RestTemplate restTemplate;
    private final CurrencyService currencyService;

    public CurrencyRatesInit(OpenExchangeRatesConfig openExchangeRatesConfig,
                             RestTemplate restTemplate,
                             CurrencyService currencyService) {
        this.openExchangeRatesConfig = openExchangeRatesConfig;
        this.restTemplate = restTemplate;
        this.currencyService = currencyService;
    }

    @Override
    public void run(String... args) {
        if (openExchangeRatesConfig.isEnabled()) {
            String openExchangeRateUrlTemplate = openExchangeRatesConfig.urlTemplate();
            Map<String, String> requestParams = openExchangeRatesConfig.requestParams();
            ExchangeRatesDTO exchangeRatesDTO = restTemplate
                    .getForObject(openExchangeRateUrlTemplate, ExchangeRatesDTO.class, requestParams);

            currencyService.updateRates(exchangeRatesDTO);
        }
    }
}
