import com.google.gson.JsonObject;
import org.trofik.banking_system.banks.*;
import org.trofik.banking_system.users.Admin;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
//        AbstractBank bank = new AbstractBank("СберБанк", "Russia");
//        bank.addCurrency(Currency.RUBLES);
//        bank.addCurrency(Currency.DOLLAR);
//
//        bank.addExchangePair(Currency.DOLLAR, Currency.RUBLES, 78.15f);
//        bank.addExchangePair(Currency.RUBLES, Currency.DOLLAR, 78.36f);
//        System.out.println(bank.getExchangePair(Currency.DOLLAR, Currency.RUBLES));
//        System.out.println(bank.getExchangePair(Currency.RUBLES, Currency.DOLLAR));

//        LoanBank b = new LoanBank("Name", "Rus");
//        b.test();

//        JsonObject json = new JsonObject();
//        json.addProperty("hello", 12);
//        json.addProperty("test", "val");
//        json.addProperty("fuck", 67.5f);
//        System.out.println(json);

//        Admin admin = new Admin("Ivan", "Petrov", "ivanp", "HardPassword");
        Admin admin = new Admin("ivanp", "HardPassword");
        System.out.println(admin.getName() + " " + admin.getSurname() + " " + admin.id);
        AbstractBank bank = new LoanBank(admin);
        bank.addExchangePair(Currency.RUBLES, Currency.DOLLAR, 0.02f);
        bank.save(admin);
        System.out.println(bank.currencyBank);
        System.out.println(bank.exchangeRate);
    }
}
