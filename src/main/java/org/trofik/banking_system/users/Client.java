package org.trofik.banking_system.users;

import org.trofik.banking_system.banks.*;

import java.sql.*;
import java.util.List;

public class Client extends User {
    public Client(String name, String surname, String login, String password) {
        super(name, surname, login, password, false);
    }

    public Client(String login, String password) {
        super(login, password, false);
    }

    public Client(String login, String password, boolean isHashed) {
        super(login, password, false, isHashed);
    }

    public LoanSaveInformation takeLoan(String nameBank, Money sumLoan) {
        try {
            Admin admin = takeAdminBank(nameBank, 1);
            LoanBank loanBank = new LoanBank(admin);
            return loanBank.takeLoan(this, sumLoan);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean makePayment(String nameBank, Money sumLoan) {
        try {
            Admin admin = takeAdminBank(nameBank, 1);
            LoanBank loanBank = new LoanBank(admin);
            return loanBank.makePayment(this, sumLoan);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public LoanSaveInformation giveMoneyForSaving(String nameBank, Money sumForSave) {
        try {
            Admin admin = takeAdminBank(nameBank, 2);
            SavingBank savingBank = new SavingBank(admin);
            return savingBank.giveMoneyForSaving(this, sumForSave);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Money takeMoneyFromBank(String nameBank) {
        try {
            Admin admin = takeAdminBank(nameBank, 2);
            SavingBank savingBank = new SavingBank(admin);
            return savingBank.takeMoneyFromBank(this);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Admin takeAdminBank(String nameBank, int typeBank) {
        try (Connection con = DriverManager.getConnection("jdbc:sqlite:" + DataBaseInfoUsers.NAME_DATABASE)) {
            Statement stat = con.createStatement();
            ResultSet res;

            res = stat.executeQuery(String.format(
                    "SELECT * FROM `Banks` WHERE name='%s' AND typeBank=%d",
                    nameBank,
                    typeBank
            ));

            if (res.next()) {
                int idAdmin = res.getInt("adminId");
                res = stat.executeQuery(String.format(
                        "SELECT * FROM `Admins` WHERE id=%d",
                        idAdmin
                ));
                res.next();
                return new Admin(res.getString("login"), res.getString("password"), true);
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ClientInformation getInfo() {
        List<LoanSaveInformation> loansSaves = LoanBank.getInfoAboutClient(this);
        loansSaves.addAll(SavingBank.getInfoAboutClient(this));
        return new ClientInformation(this, loansSaves);
    }

    @Override
    public String toString() {
        return "Client{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", id=" + id +
                '}';
    }
}
