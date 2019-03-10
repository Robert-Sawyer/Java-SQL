package pl.coderslab.workshop2.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Exercise {

    private int id;
    private String title;
    private String description;

    public Exercise(String title, String description) {
        this.title = title;
        this.description = description;
    }
    public Exercise() {

    }


    public void saveToDb(Connection conn) throws SQLException {

        if (this.id == 0) {

            String query = "insert into exercise (title, description) values (?,?);";
            PreparedStatement preparedStatement = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, this.title);
            preparedStatement.setString(2, this.description);
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                this.id = generatedKeys.getInt(1);
            }
        } else {
            String sql = "UPDATE users SET title=?, description=? where id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, this.title);
            preparedStatement.setString(2, this.description);
            preparedStatement.setInt(3, this.id);
            preparedStatement.executeUpdate();
        }
    }

    //============================================================================================

    public static Exercise loadExerciseById(Connection conn, int id) throws SQLException {

        String query = "select * from exercise where id = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            Exercise exercise = new Exercise();
            exercise.id = resultSet.getInt("id");
            exercise.title = resultSet.getString("title");
            exercise.description = resultSet.getString("description");
            return exercise;
        }

        return null;
    }


    public static Exercise[] loadAllExercises(Connection conn) throws SQLException {
        ArrayList<Exercise> exercises = new ArrayList<>();

        String query = "select * from exercise";
        PreparedStatement preparedStatement = conn.prepareStatement(query);

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Exercise exercise = new Exercise();
            exercise.id = resultSet.getInt("id");
            exercise.title = resultSet.getString("title");
            exercise.description = resultSet.getString("description");

            exercises.add(exercise);
        }
        Exercise[] eArray = new Exercise[exercises.size()];
        eArray = exercises.toArray(eArray);
        return eArray;
    }



    public void delete(Connection conn) throws SQLException {

        if (this.id != 0) {
            String sql = "DELETE FROM exercise WHERE id=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, this.id);
            preparedStatement.executeUpdate();
            this.id = 0;
        }
    }

    //================================================================================

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
