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

    @Override
    public String toString() {
        StringBuilder information = new StringBuilder();
        information.append("################ Information about User ################\n");
        for (int i = 0; i < infoList.size(); i++) {
            information.append(infoList.get(i).toString());
            information.append("\n");
        }
        information.append("########################################################\n");

        return information.toString();
    }
}
