package org.trofik.banking_system.users;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.trofik.banking_system.HashSHA256;
import org.trofik.banking_system.banks.*;

import java.sql.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

public abstract class User {
    public final String name;
    public final String surname;
    public final String login;
    public final String password;
    public final int id;

    public User(String name, String surname, String login, String password, boolean isAdmin) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = HashSHA256.sha256(password);

        try (Connection con = DriverManager.getConnection("jdbc:sqlite:" + DataBaseInfoUsers.NAME_DATABASE)) {
            Statement stat = con.createStatement();
            ResultSet res;
            stat.executeUpdate(String.format(
                    "INSERT INTO `%s` (name, surname, login, password) VALUES ('%s', '%s', '%s', '%s')",
                    isAdmin ? "Admins" : "Clients",
                    this.name,
                    this.surname,
                    this.login,
                    this.password
            ));

            res = stat.executeQuery(String.format(
                    "SELECT id FROM `%s` WHERE login='%s'",
                    isAdmin ? "Admins" : "Clients",
                    this.login

            ));
            res.next();
            this.id = res.getInt("id");

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
    }

    public User(String login, String password, boolean isAdmin) {
        try (Connection con = DriverManager.getConnection("jdbc:sqlite:" + DataBaseInfoUsers.NAME_DATABASE)) {
            Statement stat = con.createStatement();
            ResultSet res;
            password = HashSHA256.sha256(password);
            res = stat.executeQuery(String.format("SELECT * FROM `%s` WHERE login='%s' AND password='%s'",
                    isAdmin ? "Admins" : "Clients", login, password));
            if (!res.next()) {
                throw new IncorrectPasswordException();
            }
            name = res.getString("name");
            surname = res.getString("surname");
            this.login = login;
            this.password = password;
            id = res.getInt("id");
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
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public abstract UserInformation getInfo();
}
