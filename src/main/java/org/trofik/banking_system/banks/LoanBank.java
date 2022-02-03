package org.trofik.banking_system.banks;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.trofik.banking_system.users.Admin;
import org.trofik.banking_system.users.Client;
import org.trofik.banking_system.users.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class LoanBank extends AbstractBank {
    public LoanBank(String nameBank, String countryBank, float loanInterestRate) {
        super(nameBank, countryBank, loanInterestRate);
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
        try {
            ResultSet res = AbstractBank.getInfoClient(client, "Loans");
            Connection con;
            con = DriverManager.getConnection("jdbc:sqlite:" + DataBaseInfo.NAME_DATABASE);

            List<LoanInformation> infoList = new ArrayList<>();
            ResultSet resBank;
            while (res.next()) {
                try {
                    Statement stat = con.createStatement();
                    resBank = stat.executeQuery(String.format(
                            "SELECT * FROM `Banks` WHERE id=%d",
                            res.getInt("id")
                    ));
                } catch (Exception e) {
                    con.close();
                    throw new ConnectionException();

                }
                infoList.add(new LoanInformation(
                        new LoanBank(
                                resBank.getString("name"),
                                resBank.getString("country"),
                                resBank.getFloat("percent")
                        ),
                        res.getDate("dateStart"),
                        res.getDate("dateFinish"),
                        new Money(res.getFloat("sumLoan"), Currency.valueOf(res.getString("currency"))),
                        new Money(res.getFloat("sumStay"), Currency.valueOf(res.getString("currency")))
                ));
            }
            return infoList;
        } catch (SQLException e) {
            System.err.println("Bad connection!");
            throw new ConnectionException();
        } catch (Exception e) {
            System.err.println("Oops...");
            throw e;
        }
    }

    public boolean save(Admin admin) {
        return save(admin, 1);
    }
}