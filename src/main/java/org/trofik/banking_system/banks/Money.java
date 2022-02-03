package org.trofik.banking_system.banks;

public class Money {
    private final float money;
    private final Currency currency;

    public Money(float money, Currency currency) {
        this.money = money;
        this.currency = currency;
    }

    public float getMoney() {
        return money;
    }

    public Currency getCurrency() {
        return currency;
    }
}
