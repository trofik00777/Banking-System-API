package org.trofik.banking_system.banks;

import java.sql.Date;

public class LoanInformation extends MoneyInformation {
    Money moneyLoan;
    Money moneyStay;

    public LoanInformation(AbstractBank bank, long dateStart, long dateFinish, Money moneyLoan, Money moneyStay) {
        super(bank, dateStart, dateFinish);
        this.moneyLoan = moneyLoan;
        this.moneyStay = moneyStay;
    }

    @Override
    public String toString() {
        return "LoanInformation{" +
                "moneyLoan=" + moneyLoan +
                ", moneyStay=" + moneyStay +
                ", bank=" + bank +
                ", dateStart=" + dateStart +
                ", dateFinish=" + dateFinish +
                '}';
    }
}
