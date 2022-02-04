package org.trofik.banking_system.banks;

import org.trofik.banking_system.users.Admin;
import org.trofik.banking_system.users.Client;

import java.sql.*;
import java.util.List;

public class SavingBank extends AbstractBank {
    public SavingBank(String nameBank, String countryBank, float saveInterestRate, long time) {
        super(nameBank, countryBank, saveInterestRate, time);
    }

    public SavingBank(Admin admin) {
        super(admin);
    }

    public LoanSaveInformation giveMoneyForSaving(Client client, Money sumSave) {
        return openOperationInBank(client, sumSave, false);
    }

    public Money takeMoneyFromBank(Client client) {
        try (Connection con = DriverManager.getConnection("jdbc:sqlite:" + DataBaseInfo.NAME_DATABASE)) {
            Statement stat = con.createStatement();
            ResultSet res;
            res = stat.executeQuery(String.format(
                    "SELECT * FROM `Saves` WHERE clientId=%d AND bankId=%d",
                    client.id,
                    this.idBank
            ));
            if (!res.next()) {
                return null;
            }

            float sumToPay;
            if (System.currentTimeMillis() - res.getLong("dateFinish") >= 0) {
                sumToPay = res.getFloat("sumClose");
            } else {
                sumToPay = res.getFloat("sumOpen");
            }

            stat.executeUpdate(String.format(
                    "DELETE FROM `Saves` WHERE bankId=%d AND clientId=%d",
                    this.idBank,
                    client.id
            ));

            return new Money(sumToPay, Currency.valueOf(res.getString("currency")));
        } catch (SQLException e) {
            System.err.println("Bad connection");
            throw new ConnectionException();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ConnectionException();
        }
    }

    public static List<LoanSaveInformation> getInfoAboutClient(Client client) {
        return getInfoLoanSave(client, false);
    }

    @Override
    public boolean save(Admin admin) {
        return save(admin, TypesBanks.TYPES_BANKS.get("Saving"));
    }
}
