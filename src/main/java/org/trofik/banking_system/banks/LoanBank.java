package org.trofik.banking_system.banks;

import org.trofik.banking_system.users.Admin;
import org.trofik.banking_system.users.Client;
import org.trofik.banking_system.users.User;

import java.sql.*;
import java.util.List;

public class LoanBank extends AbstractBank {
    float loanInterestRate;

    public LoanBank(String nameBank, String countryBank, float loanInterestRate) {
        super(nameBank, countryBank);
        this.loanInterestRate = loanInterestRate;
    }

    public boolean takeLoan(User user, Money sumLoan) {
        //...
        return false;
    }

    public boolean makePayment(User user, Money sumPayment) {
        //...
        return false;
    }

    public static List<LoanInformation> getInfoAboutClient(Client client) {
        //...
//        return new LoanInformation(
//                new LoanBank("pass", "pass", 1f),
//                new Date(2021, 1, 1),
//                new Date(2021, 1, 2),
//                15.3f,
//                new Money(15000f, Currency.RUBLES),
//                new Money(1200f, Currency.RUBLES)
//        );
        return null;
    }

    @Override
    public boolean save(Admin admin) {
        //...проверка пароля...
        return false;
    }

    //    public void test() {
//        try {
//            Connection con;
//            con = null;
//
////        Class.forName("org.sqlite.JDBC");
//            con = DriverManager.getConnection("jdbc:sqlite:db/BankingSystemDB.db");
//            Statement stat = con.createStatement();
//            ResultSet res = stat.executeQuery("SELECT * FROM `testTable`");
//            res.next();
//            System.out.println(res.getInt("id"));
//            res.next();
//            System.out.println(res.getInt("id"));
//
//            con.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
}