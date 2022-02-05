# Banking-System-API

## About

Api for the banking system. You can create admins and clients of the bank, as well as banks (2 types of banks are supported: savings and loan). Various operations are supported, such as

For **Loan**:

1. Open a credit account
2. Make the payment of part of the loan

For **Saving**:
1. Open a deposit
2. Withdraw money from the account

By default, the system runs on the SQLite database located in the `db/` folder. Optionally, you can write in the DatabaseInfo files.java and DataBase Info Users.java path to its database



## Build and use

You can use my build using maven, which is located in the `result_build/` directory, or build the project yourself

## Package structure

```
└───org
    └───trofik
        └───banking_system
            │   HashSHA256.java
            │
            ├───banks
            │       AbstractBank.java
            │       BankInformation.java
            │       ConnectionException.java
            │       Currency.java
            │       DataBaseInfo.java
            │       IncorrectPasswordException.java
            │       LoanBank.java
            │       LoanSaveInformation.java
            │       Money.java
            │       MoneyInformation.java
            │       SavingBank.java
            │       TypesBanks.java
            │
            └───users
                    Admin.java
                    AdminInformation.java
                    Client.java
                    ClientInformation.java
                    DataBaseInfoUsers.java
                    User.java
                    UserInformation.java
```

## Documentation

Coming soon...
