package org.trofik.banking_system.banks;

public class LoanSaveInformation extends MoneyInformation {
    Money moneyOpen;
    Money moneyClose;

    public LoanSaveInformation(AbstractBank bank, long dateStart, long dateFinish, Money moneyOpen, Money moneyClose) {
        super(bank, dateStart, dateFinish);
        this.moneyOpen = moneyOpen;
        this.moneyClose = moneyClose;
    }

    @Override
    public String toString() {
        return "LoanInformation{" +
                "moneyLoan=" + moneyOpen +
                ", moneyStay=" + moneyClose +
                ", bank=" + bank +
                ", dateStart=" + dateStart +
                ", dateFinish=" + dateFinish +
                '}';
    }
}
