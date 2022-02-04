package org.trofik.banking_system.banks;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.trofik.banking_system.users.Admin;
import org.trofik.banking_system.users.Client;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

class CurrencyExchange {
    Currency from;
    Currency to;

    CurrencyExchange(Currency from, Currency to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return from.toString() + "," + to.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrencyExchange that = (CurrencyExchange) o;
        return from == that.from && to == that.to;
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }
}

public abstract class AbstractBank {
    public final String nameBank;
    public final String countryBank;
    public Set<Currency> currencyBank;
    public Map<CurrencyExchange, Float> exchangeRate;
    public float percent;
    public int idBank;
    public long time;

    protected AbstractBank(String nameBank, String countryBank, float percent, long time) {
        this.nameBank = nameBank;
        this.countryBank = countryBank;
        this.percent = percent;
        this.time = time;
        currencyBank = new HashSet<>();
        exchangeRate = new HashMap<>();
        idBank = -1;
    }

    /*protected AbstractBank(String nameBank, String countryBank, float percent, long time,
                           Set<Currency> currencyBank, Map<CurrencyExchange, Float> exchangeRate) {
        this.nameBank = nameBank;
        this.countryBank = countryBank;
        this.percent = percent;
        this.time = time;
        this.currencyBank = currencyBank;
        this.exchangeRate = exchangeRate;
        idBank = -1;
    }*/

    protected AbstractBank(Admin admin) {
        try (Connection con = DriverManager.getConnection("jdbc:sqlite:" + DataBaseInfo.NAME_DATABASE)) {
            Statement stat = con.createStatement();
            ResultSet res;
            res = stat.executeQuery(String.format("SELECT id FROM `Admins` WHERE login='%s' AND password='%s'",
                    admin.login, admin.password));
            if (!res.next()) {
                throw new IncorrectPasswordException();
            }
            res = stat.executeQuery(String.format("SELECT * FROM `Banks` WHERE adminId=%d", admin.id));
            if (res.next()) {
                nameBank = res.getString("name");
                countryBank = res.getString("country");
                percent = res.getFloat("percent");
                idBank = res.getInt("id");
                time = res.getLong("time");

                if (res.getString("currency").equals("")) {
                    currencyBank = new HashSet<>();
                } else {
                    currencyBank = Arrays.stream(
                            res.getString("currency").split(",")
                    ).map(Currency::valueOf).collect(Collectors.toSet());
                }

                JsonParser parser = new JsonParser();
                JsonObject json = (JsonObject) parser.parse(res.getString("exchangeRate"));
                exchangeRate = new HashMap<>();
                for (String key : json.keySet()) {
                    String[] fromTo = key.split(",");
                    exchangeRate.put(new CurrencyExchange(Currency.valueOf(fromTo[0]), Currency.valueOf(fromTo[1])),
                            json.get(key).getAsFloat());
                }
            } else {
                System.err.println("Bank not found for adminId=" + admin.id);
                throw new ConnectionException();
            }
        } catch (IncorrectPasswordException e) {
            System.err.println("Incorrect Login or Password");
            throw new IncorrectPasswordException();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ConnectionException();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ConnectionException();
        }

        //...проверка пароля...
        // throw IncorrectPassword
        // ...
    }

    public void addCurrency(Currency curr) {
        if (!currencyBank.contains(curr)) {
            currencyBank.add(curr);
        } else {
            System.err.println("Данную валюту " + curr.toString() + " уже принимает банк " + nameBank);
        }
    }

    public void addExchangePair(Currency from, Currency to, float cost) {
        if (!currencyBank.contains(from) || !currencyBank.contains(to)) {
            System.err.println("Данную(-ые) валюту(-ы) " + from.toString() + "/" + to.toString() +
                    " не принимает банк " + nameBank);
            return;
        }
        CurrencyExchange exchange = new CurrencyExchange(from, to);
        exchangeRate.put(exchange, cost);
    }

    public float getExchangePair(Currency from, Currency to) {
        CurrencyExchange exchange = new CurrencyExchange(from, to);
        return exchangeRate.getOrDefault(exchange, (float) -1);
    }

    public boolean hasExchangePair(Currency from, Currency to) {
        CurrencyExchange exchange = new CurrencyExchange(from, to);
        return exchangeRate.containsKey(exchange);
    }

    public boolean delExchangePair(Currency from, Currency to) {
        CurrencyExchange exchange = new CurrencyExchange(from, to);
        if (exchangeRate.containsKey(exchange)) {
            exchangeRate.remove(exchange);
            return true;
        }
        return false;
    }

    protected static ResultSet getInfoClient(Client client, String tableToSearchName) {
        try {
            Connection con;
            con = DriverManager.getConnection("jdbc:sqlite:" + DataBaseInfo.NAME_DATABASE);
            try {
                Statement stat = con.createStatement();
                ResultSet res;
                res = stat.executeQuery(String.format("SELECT id FROM `Clients` WHERE login='%s' AND password='%s'",
                        client.login, client.password));
                if (!res.next()) {
                    throw new IncorrectPasswordException();
                }
                res = stat.executeQuery(String.format(
                        "SELECT * FROM `%s` WHERE clientId=%d",
                        tableToSearchName,
                        client.id
                ));
                return res;
            } catch (IncorrectPasswordException e) {
                System.err.println("Incorrect Login or Password");
                throw new IncorrectPasswordException();
            } catch (Exception e) {
                e.printStackTrace();
                throw new ConnectionException();
            } finally {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ConnectionException();
        }
    }

    protected boolean save(Admin admin, int typeBank) {
        try {
            Connection con;
            con = DriverManager.getConnection("jdbc:sqlite:" + DataBaseInfo.NAME_DATABASE);
            try {
                Statement stat = con.createStatement();
                ResultSet res;
                res = stat.executeQuery(String.format("SELECT id, bankId FROM `Admins` WHERE login='%s' AND password='%s'",
                        admin.login, admin.password));
                if (!res.next()) {
                    throw new IncorrectPasswordException();
                }
                JsonObject json = new JsonObject();
                for (CurrencyExchange key : exchangeRate.keySet()) {
                    json.addProperty(key.toString(), exchangeRate.get(key));
                }
                String strCurrency = String.join(",", currencyBank.stream().map(x -> x.toString()).toList());
                if (idBank > 0) {
                    if (res.getInt("bankId") != idBank) {
                        return false;
                    }
                    stat.executeUpdate(
                            String.format(
                                    "UPDATE `Banks` SET currency='%s', exchangeRate='%s', percent=%f WHERE id=%d AND time=%d",
                                    strCurrency,
                                    json.toString(),
                                    percent,
                                    idBank,
                                    time
                            )
                    );
                } else {
                    stat.executeUpdate(
                            String.format(
                                    "INSERT INTO `Banks` (name, country, currency, exchangeRate, typeBank, adminId, percent, time) " +
                                            "VALUES ('%s', '%s', '%s', '%s', %d, %d, %f, %d)",
                                    nameBank,
                                    countryBank,
                                    strCurrency,
                                    json.toString(),
                                    typeBank,
                                    admin.id,
                                    percent,
                                    time
                            )
                    );
                    res = stat.executeQuery(String.format(
                            "SELECT id FROM `Banks` WHERE name='%s'",
                            nameBank
                    ));
                    res.next();
                    idBank = res.getInt("id");
                    stat.executeUpdate(String.format(
                            "UPDATE `Admins` SET bankId=%d WHERE id=%d",
                            idBank,
                            admin.id
                    ));
                }
            } catch (IncorrectPasswordException e) {
                System.err.println("Incorrect Login or Password");
                return false;
            } catch (Exception e) {
                e.printStackTrace();
                throw new ConnectionException();
            } finally {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ConnectionException();
        }
        return true;
    }

    public abstract boolean save(Admin admin);

    @Override
    public String toString() {
        return "AbstractBank{" +
                "nameBank='" + nameBank + '\'' +
                ", countryBank='" + countryBank + '\'' +
                ", currencyBank=" + currencyBank +
                ", exchangeRate=" + exchangeRate +
                ", percent=" + percent +
                ", idBank=" + idBank +
                ", time=" + time +
                '}';
    }
}
