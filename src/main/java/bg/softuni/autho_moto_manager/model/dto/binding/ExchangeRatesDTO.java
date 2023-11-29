package bg.softuni.autho_moto_manager.model.dto.binding;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;

//{
//  "disclaimer": "Usage subject to terms: https://openexchangerates.org/terms",
//  "license": "https://openexchangerates.org/license",
//  "timestamp": 1698318000,
//  "base": "USD",
//  "rates": {
//    "BGN": 1.854,
//    "EUR": 0.948994
//  }
//}
public record ExchangeRatesDTO(String base, Map<String, BigDecimal> rates) {

    @Override
    public String toString() {

        String ratesData = rates.entrySet().stream().map(entry -> String.format("%s %.5f", entry.getKey(), entry.getValue()))
                .collect(Collectors.joining(" | "));

        return "ExchangeRatesDTO{" +
                "base='" + base + '\'' +
                ", rates=" + ratesData +
                '}';
    }
}



