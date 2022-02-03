package org.trofik.banking_system.users;

import org.trofik.banking_system.banks.MoneyInformation;

import java.util.List;

public abstract class UserInformation {
    protected User user;
    protected List<?> infoList;

    public UserInformation(User user, List<?> infoList) {
        this.user = user;
        this.infoList = infoList;
    }
}
