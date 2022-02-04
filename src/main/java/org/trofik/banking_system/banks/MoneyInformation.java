package org.trofik.banking_system.banks;


public abstract class MoneyInformation {
    protected AbstractBank bank;
    protected long dateStart;
    protected long dateFinish;

    public MoneyInformation(AbstractBank bank, long dateStart, long dateFinish) {
        this.bank = bank;
        this.dateStart = dateStart;
        this.dateFinish = dateFinish;
    }

    @Override
    public String toString() {
        return "MoneyInformation{" +
                "bank=" + bank +
                ", dateStart=" + dateStart +
                ", dateFinish=" + dateFinish +
                '}';
    }
}
