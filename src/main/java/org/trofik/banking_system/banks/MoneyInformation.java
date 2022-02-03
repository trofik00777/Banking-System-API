package org.trofik.banking_system.banks;

import java.sql.Date;

public abstract class MoneyInformation {
    protected AbstractBank bank;
    protected Date dateStart;
    protected Date dateFinish;
    protected float percent;

    public MoneyInformation(AbstractBank bank, Date dateStart, Date dateFinish, float percent) {
        this.bank = bank;
        this.dateStart = dateStart;
        this.dateFinish = dateFinish;
        this.percent = percent;
    }
}
