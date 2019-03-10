package pl.coderslab.workshop2.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Solution {

    private int id;
    private String created;
    private String updated;
    private String description;

    public Solution(String created, String updated, String description) {

    }

    public Solution() {

    }

    public void saveToDb(Connection conn) throws SQLException {

        if (this.id == 0) {

            String query = "insert into solution (created, description) values (?, ?);";
            PreparedStatement preparedStatement = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, this.created);
            preparedStatement.setString(2, this.description);
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                this.id = generatedKeys.getInt(1);
            }
        } else {
            String sql = "UPDATE solution SET updated=?, description=? where id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, this.updated);
            preparedStatement.setString(2, this.description);
            preparedStatement.setInt(3, this.id);
            preparedStatement.executeUpdate();
        }
    }

    public static Solution loadSolutionsById(Connection conn, int id) throws SQLException {

        String query = "select * from solution where id = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            Solution solution = new Solution();
            solution.id = resultSet.getInt("id");
            solution.created = resultSet.getString("created");
            solution.updated = resultSet.getString("updated");
            solution.description = resultSet.getString("description");
            return solution;
        }

        return null;
    }

    public static Solution[] loadAllSolutions(Connection conn) throws SQLException {

        ArrayList<Solution> solutions = new ArrayList<>();

        String query = "select * from solution";
        PreparedStatement preparedStatement = conn.prepareStatement(query);

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Solution solution = new Solution();
            solution.id = resultSet.getInt("id");
            solution.created = resultSet.getString("created");
            solution.updated = resultSet.getString("updated");
            solution.description = resultSet.getString("description");

            solutions.add(solution);
        }
        Solution[] sArray = new Solution[solutions.size()];
        sArray = solutions.toArray(sArray);
        return sArray;
    }

    public static Solution[] loadAllByUsersId(Connection conn) throws SQLException {

    }

    public static Solution[] loadAllByExerciseId(Connection conn) throws SQLException {

    }

    public void delete(Connection conn) throws SQLException {

        if (this.id != 0) {
            String sql = "DELETE FROM solution WHERE id=?";
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



}
