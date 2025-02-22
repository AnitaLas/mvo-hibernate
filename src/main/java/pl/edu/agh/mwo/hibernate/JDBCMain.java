package pl.edu.agh.mwo.hibernate;

import java.sql.*;

public class JDBCMain {
    public static void main(String[] args) {executeSQL();}

    private static void executeSQL() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:school.db", "", "");
            Statement statement = connection.createStatement();
            Statement statement1 = connection.createStatement();

            String query = "SELECT * FROM schools";

            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                System.out.println("School name: " + resultSet.getString("name"));
                System.out.println("       address: " + resultSet.getString("address"));

                int id = resultSet.getInt("id");
                String query2 = "SELECT * FROM schoolClasses where school_id = " + id;
                ResultSet resultSet2 = statement1.executeQuery(query2);

                while (resultSet2.next()) {
                    System.out.println("Class profil: " + resultSet2.getString("profile"));
                    System.out.println("   startYear: " + resultSet2.getString("startYear"));
                    System.out.println(" currentYear: " + resultSet2.getString("currentYear"));
                }
                System.out.println();
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
