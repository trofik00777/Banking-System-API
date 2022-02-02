import org.trofik.banking_system.banks.AbstractBank;
import org.trofik.banking_system.banks.Currency;

public class Main {
    public static void main(String[] args) {
        AbstractBank bank = new AbstractBank("СберБанк", "Russia");
        bank.addCurrency(Currency.RUBLES);
        bank.addCurrency(Currency.DOLLAR);

        bank.addExchangePair(Currency.DOLLAR, Currency.RUBLES, 78.15f);
        bank.addExchangePair(Currency.RUBLES, Currency.DOLLAR, 78.36f);
        System.out.println(bank.getExchangePair(Currency.DOLLAR, Currency.RUBLES));
        System.out.println(bank.getExchangePair(Currency.RUBLES, Currency.DOLLAR));
    }
}
