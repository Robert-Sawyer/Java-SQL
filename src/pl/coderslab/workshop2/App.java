package pl.coderslab.workshop2;

import pl.coderslab.workshop2.models.User;

import java.sql.*;

public class App {

    public static void main(String[] args) {
        try (Connection conn = ConnectionManager.getConnection()) {

            System.out.println("hello");


            User user = User.loadUserById(conn, 8);
            System.out.println(user);

            if(user != null) {

               user.delete(conn);

                System.out.println(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }

    }
}
