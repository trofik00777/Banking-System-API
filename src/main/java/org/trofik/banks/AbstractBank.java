package org.trofik.banks;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AbstractBank {
    public final String nameBank;
    public final String countryBank;
    public List<Currency> currencyBank;

    public AbstractBank(String nameBank, String countryBank) {
        this.nameBank = nameBank;
        this.countryBank = countryBank;
        currencyBank = new ArrayList<>();
    }


}
