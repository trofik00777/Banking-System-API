package org.trofik.banks;

import java.util.*;

class CurrencyExchange {
    Currency from;
    Currency to;

    CurrencyExchange(Currency from, Currency to) {
        this.from = from;
        this.to = to;
    }
}

public class AbstractBank {
    public final String nameBank;
    public final String countryBank;
    public Set<Currency> currencyBank;
    public Map<CurrencyExchange, Float> exchangeRate;

    public AbstractBank(String nameBank, String countryBank) {
        this.nameBank = nameBank;
        this.countryBank = countryBank;
        currencyBank = new HashSet<>();
        exchangeRate = new HashMap<>();
    }

    public void addCurrency(Currency curr) {
        if (!currencyBank.contains(curr)) {
            currencyBank.add(curr);
        } else {
            System.err.println("Данную валюту " + curr.toString() + " уже принимает банк " + nameBank);
        }
    }

    public void addExchangePair(Currency from, Currency to, float cost) {
        CurrencyExchange exchange = new CurrencyExchange(from, to);
        exchangeRate.put(exchange, cost);
    }

    public float getExchangePair(Currency from, Currency to) {
        CurrencyExchange exchange = new CurrencyExchange(from, to);
        return exchangeRate.getOrDefault(exchange, (float) -1);
    }

    public boolean hasExchangePair(Currency from, Currency to) {
        CurrencyExchange exchange = new CurrencyExchange(from, to);
        return exchangeRate.containsKey(exchange);
    }

    public boolean delExchangePair(Currency from, Currency to) {
        CurrencyExchange exchange = new CurrencyExchange(from, to);
        exchange.from = from;
        exchange.to = to;
        if (exchangeRate.containsKey(exchange)) {
            exchangeRate.remove(exchange);
            return true;
        }
        return false;
    }
}
