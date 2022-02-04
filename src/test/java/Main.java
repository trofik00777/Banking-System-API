import com.google.gson.JsonObject;
import org.trofik.banking_system.banks.*;
import org.trofik.banking_system.users.Admin;
import org.trofik.banking_system.users.Client;
import org.trofik.banking_system.users.User;

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
//        Admin admin1 = new Admin("olegtink", "qwerty12345");
//        System.out.println(admin1.getName() + " " + admin1.getSurname() + " " + admin1.id);
        LoanBank bank = new LoanBank(admin);
        Client client = new Client("petr123", "my_password");
        System.out.println(client.login + client.id + client.name + client.surname + client.password + client.getInfo());
        System.out.println(bank.makePayment(client, new Money(100f, Currency.EUROS)));
//        bank.addExchangePair(Currency.RUBLES, Currency.DOLLAR, 0.02f);
//        bank.save(admin);
//        System.out.println(bank.currencyBank);
//        System.out.println(bank.exchangeRate);


        //olegtink-qwerty12345
        //ivanp-HardPassword
        //petr123-my_password
        //oliv24-olivia
        //taw98-yoyo


    }
}
