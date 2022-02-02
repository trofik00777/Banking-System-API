package org.trofik.banking_system.banks;

import org.trofik.banking_system.users.User;

import java.sql.*;

public class LoanBank extends AbstractBank {
    float loanInterestRate;

    public LoanBank(String nameBank, String countryBank) {
        super(nameBank, countryBank);
    }

    public boolean takeLoan(User user, float sumLoan) {
        //...
        return false;
    }

    public boolean makePayment(User user, float sumPayment) {
        //...
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