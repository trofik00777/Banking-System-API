package org.trofik.banking_system.banks;

import java.sql.Date;

public class LoanInformation extends MoneyInformation {
    Money moneyLoan;
    Money moneyStay;

    public LoanInformation(AbstractBank bank, Date dateStart, Date dateFinish, float percent, Money moneyLoan, Money moneyStay) {
        super(bank, dateStart, dateFinish, percent);
        this.moneyLoan = moneyLoan;
        this.moneyStay = moneyStay;
    }
}
