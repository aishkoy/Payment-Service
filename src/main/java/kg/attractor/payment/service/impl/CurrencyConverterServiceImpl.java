package kg.attractor.payment.service.impl;

import kg.attractor.payment.dao.CurrencyConverterDao;
import kg.attractor.payment.exception.CurrencyRateNotFoundException;
import kg.attractor.payment.service.CurrencyConverterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
@RequiredArgsConstructor
public class CurrencyConverterServiceImpl implements CurrencyConverterService {
    private final CurrencyConverterDao dao;

    @Override
    public BigDecimal getRate(Long fromCurrency, Long toCurrency){
        try {
            return dao.getRate(fromCurrency, toCurrency);
        } catch (EmptyResultDataAccessException e) {
            throw new CurrencyRateNotFoundException("Currency rate not found");
        }
    }

    @Override
    public BigDecimal convert(Long fromCurrency, Long toCurrency, BigDecimal amount){
        BigDecimal rate = getRate(fromCurrency, toCurrency);
        return amount.multiply(rate);
    }
}
