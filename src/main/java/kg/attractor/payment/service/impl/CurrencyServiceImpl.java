package kg.attractor.payment.service.impl;

import kg.attractor.payment.dao.CurrencyDao;
import kg.attractor.payment.exception.CurrencyNotFoundException;
import kg.attractor.payment.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {
    private final CurrencyDao dao;

    @Override
    public Long getCurrencyIdByName(String name){
        try {
            return dao.getCurrencyIdByName(name.toUpperCase());
        } catch (EmptyResultDataAccessException e) {
            throw new CurrencyNotFoundException("Currency with name " + name + " not found");
        }
    }
}
