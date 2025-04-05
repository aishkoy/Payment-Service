package kg.attractor.payment.service;

import java.math.BigDecimal;

public interface CurrencyConverterService {
    BigDecimal getRate(Long fromCurrency, Long toCurrency);
}
