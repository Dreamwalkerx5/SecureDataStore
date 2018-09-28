package model;

import Utilities.FXDialogs;
import org.apache.derby.jdbc.EmbeddedDriver;

import java.sql.*;
import java.util.LinkedList;

class DerbyInterface {
    private Connection conn = null;
    private PreparedStatement pstmt;
    private Statement stmt;
    private ResultSet rs = null;

    String createSQL = "CREATE TABLE datastore (ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
            + " DATA VARCHAR(240), CONSTRAINT primary_key PRIMARY KEY (id))";

    DerbyInterface() {

        Driver derbyEmbeddedDriver = new EmbeddedDriver();

        try {

            DriverManager.registerDriver(derbyEmbeddedDriver);
            conn = DriverManager.getConnection("jdbc:derby:testdb2; create=true", "STEVE", "zqpm06275");
            stmt = conn.createStatement();

        } catch (SQLException ex) {
            FXDialogs.errorDialog("Error", "Derby Database problem.", "Error opening database: " + ex);
        }

    }

    boolean createNewDataBase() {

        try {
            stmt.execute(createSQL);
            FXDialogs.informationDialog("Information", "Derby Database.", "Created new Derby database.");

        } catch (SQLException ex) {
            FXDialogs.errorDialog("Error", "Derby Database", "Error creating Derby database:\n" + ex);

        }

        return true;
    }

    boolean deleteDataBase() {
        try {
            stmt.execute("drop table datastore");
            FXDialogs.informationDialog("Information", "Derby Database", "Cleared old Derby database.");
            return true;
        } catch (SQLException ex) {
            FXDialogs.errorDialog("Error", "Derby Database", "Error clearing Derby database:\n" + ex);

        }
        return false;

    }

    void getDataBase(LinkedList<Model> dataBase) {

        if (conn != null) {
            try {

                rs = stmt.executeQuery("select * from datastore");

                while (rs.next()) {

                    //System.out.printf("%d %s\n", rs.getInt(1), rs.getString(2));

                    Model model = new Model(rs.getInt(1), rs.getString(2));
                    dataBase.add(model);

                }

            } catch (SQLException ex) {
                FXDialogs.errorDialog("Error", "Derby Database.", "Error reading Derby database:\n" + ex);

            }

        }

    }

    void saveDataBase(LinkedList<Model> dataBase) {

    }

    void updateModel(Model model) {

        try {

            pstmt = conn.prepareStatement("UPDATE datastore SET DATA=? WHERE ID=?");
            pstmt.setString(1, model.getCryptoText());
            pstmt.setInt(2, model.getKey());

            pstmt.executeUpdate();

        } catch (SQLException ex) {
            FXDialogs.errorDialog("Error", "Derby Database.", "Error updating Derby database:\n" + ex);

        }

    }

    void deleteModel(Model model) {

        try {

            pstmt = conn.prepareStatement("DELETE FROM datastore WHERE ID=?");
            pstmt.setInt(1, model.getKey());

            pstmt.executeUpdate();

        } catch (SQLException ex) {
            FXDialogs.errorDialog("Error", "Derby Database.", "Error deleting from Derby database:\n" + ex);

        }

    }

    void addModel(Model model) {

        try {

            pstmt = conn.prepareStatement("insert into datastore" + "(DATA) values(?)");
            pstmt.setString(1, model.getCryptoText());
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            FXDialogs.errorDialog("Error", "Derby Database.", "Error adding data to Derby database:\n" + ex);

        }

    }

    void shutdown() {

        // Shut down the Derby database and release resources
        try {

            DriverManager.getConnection("jdbc:derby:;shutdown=true");

        } catch (SQLException ex) {

            if (((ex.getErrorCode() == 50000) && ("XJ015".equals(ex.getSQLState())))) {

                System.out.println("Derby shut down" + " normally");

            } else {
                FXDialogs.errorDialog("Error", "Derby Database.", "Error shutting down Derby database:\n" + ex);
                
            }
        }
    }
}
