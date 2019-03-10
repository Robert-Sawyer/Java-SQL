package pl.coderslab.workshop2.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Group {

    private int id;
    private String name;

    public Group(String name) {
        this.name = name;
    }

    public Group() {

    }

    public void saveToDb(Connection conn) throws SQLException {

        if (this.id == 0) {

            String query = "insert into user_group (name) values (?);";
            PreparedStatement preparedStatement = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, this.name);

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                this.id = generatedKeys.getInt(1);
            }
        } else {
            String sql = "UPDATE users SET name=? where id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, this.name);
            preparedStatement.setInt(2, this.id);
            preparedStatement.executeUpdate();
        }
    }

    public static Group loadGroupById(Connection conn, int id) throws SQLException {

        String query = "select * from user_group where id = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            Group group = new Group();
            group.id = resultSet.getInt("id");
            group.name = resultSet.getString("name");
            return group;
        }

        return null;
    }

    public static Group[] loadAllGroup(Connection conn) throws SQLException {

        ArrayList<Group> groups = new ArrayList<>();

        String query = "select * from user_group";
        PreparedStatement preparedStatement = conn.prepareStatement(query);

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Group group = new Group();
            group.id = resultSet.getInt("id");
            group.name = resultSet.getString("name");

            groups.add(group);
        }
        Group[] gArray = new Group[groups.size()];
        gArray = groups.toArray(gArray);
        return gArray;
    }

    public void delete(Connection conn) throws SQLException {

        if (this.id != 0) {
            String sql = "DELETE FROM user_group WHERE id=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, this.id);
            preparedStatement.executeUpdate();
            this.id = 0;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    }
