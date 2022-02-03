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
    public LoanBank(String nameBank, String countryBank, float loanInterestRate, long time) {
        super(nameBank, countryBank, loanInterestRate, time);
    }

    public LoanBank(Admin admin) {
        super(admin);
    }

    public LoanInformation takeLoan(Client client, Money sumLoan) {
        try (Connection con = DriverManager.getConnection("jdbc:sqlite:" + DataBaseInfo.NAME_DATABASE)) {
            Statement stat = con.createStatement();
            ResultSet res;

            res = stat.executeQuery(String.format(
                    "SELECT * FROM `Loans` WHERE clientId=%d AND bankId=%d",
                    client.id,
                    this.idBank
            ));

            if (res.next()) {
                return null;
            }

            Date dateStart = new Date(System.currentTimeMillis()), dateFinish = new Date(System.currentTimeMillis() + this.time);
            float moneyStay = sumLoan.getMoney() * (1 + this.percent);

            stat.executeUpdate(String.format(
                    "INSERT INTO `Loans` (bankId, clientId, sumLoan, sumStay, dateStart, dateFinish, currency) " +
                            "VALUES (%d, %d, %f, %f, '%s', '%s', '%s')",
                    this.idBank,
                    client.id,
                    sumLoan.getMoney(),
                    moneyStay,
                    dateStart,
                    dateFinish,
                    sumLoan.getCurrency().toString()
            ));

            return new LoanInformation(
                    this,
                    dateStart,
                    dateFinish,
                    sumLoan,
                    new Money(moneyStay, sumLoan.getCurrency())
            );

        } catch (SQLException e) {
            System.err.println("Bad connection");
            throw new ConnectionException();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ConnectionException();
        }
    }

    public boolean makePayment(Client client, Money sumPayment) {
        try (Connection con = DriverManager.getConnection("jdbc:sqlite:" + DataBaseInfo.NAME_DATABASE)) {
            Statement stat = con.createStatement();
            ResultSet res;

            res = stat.executeQuery(String.format(
                    "SELECT * FROM `Loans` WHERE clientId=%d AND bankId=%d",
                    client.id,
                    this.idBank
            ));

            if (!res.next()) {
                return false;
            }

            float sumToPay = sumPayment.getMoney();

            if (sumPayment.getCurrency() != Currency.valueOf(res.getString("currency"))) {
                sumToPay = exchangeRate.get(new CurrencyExchange(sumPayment.getCurrency(),
                        Currency.valueOf(res.getString("currency"))));
            }
            float sumStay = res.getFloat("sumStay") - sumToPay;
            if (sumStay <= 0) {
                stat.executeUpdate(String.format(
                        "DELETE FROM `Loans` WHERE bankId=%d AND clientId=%d",
                        this.idBank,
                        client.id
                ));
            } else {
                stat.executeUpdate(String.format(
                        "UPDATE `Loans` SET sumStay=%f WHERE bankId=%d AND clientId=%d",
                        sumStay,
                        this.idBank,
                        client.id
                ));
            }

            return true;

        } catch (SQLException e) {
            System.err.println("Bad connection");
            throw new ConnectionException();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ConnectionException();
        }
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
                            "SELECT * FROM `Admins` WHERE id=(SELECT adminId FROM `Banks` WHERE id=%d LIMIT 1)",
                            res.getInt("id")
                    ));
                } catch (Exception e) {
                    con.close();
                    throw new ConnectionException();

                }
                infoList.add(new LoanInformation(
                        new LoanBank(
                                new Admin(
                                        resBank.getString("login"),
                                        resBank.getString("password")
                                )
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

    @Override
    public boolean save(Admin admin) {
        return save(admin, 1);
    }
}