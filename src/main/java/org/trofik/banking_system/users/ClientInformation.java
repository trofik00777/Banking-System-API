package org.trofik.banking_system.users;

import org.trofik.banking_system.banks.LoanInformation;
import org.trofik.banking_system.banks.MoneyInformation;

import java.util.List;

public class ClientInformation extends UserInformation {
    public ClientInformation(User user, List<LoanInformation> infoList) {
        super(user, infoList);
    }
}
