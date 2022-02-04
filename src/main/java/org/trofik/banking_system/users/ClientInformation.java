package org.trofik.banking_system.users;

import org.trofik.banking_system.banks.LoanSaveInformation;

import java.util.List;

public class ClientInformation extends UserInformation {
    public ClientInformation(User user, List<LoanSaveInformation> infoList) {
        super(user, infoList);
    }
}
