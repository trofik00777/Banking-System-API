package org.trofik.banking_system.banks;

import org.trofik.banking_system.users.Admin;
import org.trofik.banking_system.users.Client;

import java.sql.*;
import java.util.List;

public class LoanBank extends AbstractBank {
    public LoanBank(String nameBank, String countryBank, float loanInterestRate, long time) {
        super(nameBank, countryBank, loanInterestRate, time);
    }

    public LoanBank(Admin admin) {
        super(admin);
    }

    public LoanSaveInformation takeLoan(Client client, Money sumLoan) {
        return openOperationInBank(client, sumLoan, true);
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
                if (!currencyBank.contains(sumPayment.getCurrency())) {
                    return false;
                }
                sumToPay = exchangeRate.get(new CurrencyExchange(sumPayment.getCurrency(),
                        Currency.valueOf(res.getString("currency")))) * sumPayment.getMoney();
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

    public static List<LoanSaveInformation> getInfoAboutClient(Client client) {
        return getInfoLoanSave(client, true);
    }

    @Override
    public boolean save(Admin admin) {
        return save(admin, TypesBanks.TYPES_BANKS.get("Loan"));
    }
}