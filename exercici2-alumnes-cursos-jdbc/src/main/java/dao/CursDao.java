package dao;

import db.DatabaseConnection;
import exception.DataAccessException;
import model.Curs;

import java.sql.*;
import java.util.ArrayList;

public class CursDao {

    public Curs buscarPerId(int id) {
        String sql = "SELECT id, nom, nivell FROM cursos WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Curs(rs.getInt("id"), rs.getString("nom"), rs.getString("nivell"));
                }
            }
            return null;
        } catch (SQLException e) {
            throw new DataAccessException("No s'ha pogut recuperar el curs amb id " + id + ".", e);
        }
    }

    public ArrayList<Curs> llistarTots() {
        ArrayList<Curs> cursos = new ArrayList<>();
        String sql = "SELECT id, nom, nivell FROM cursos ORDER BY id";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                cursos.add(new Curs(rs.getInt("id"), rs.getString("nom"), rs.getString("nivell")));
            }
            return cursos;
        } catch (SQLException e) {
            throw new DataAccessException("No s'han pogut recuperar els cursos.", e);
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM cursos WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataAccessException("No s'ha pogut eliminar el curs amb id " + id + ".", e);
        }
    }
}
