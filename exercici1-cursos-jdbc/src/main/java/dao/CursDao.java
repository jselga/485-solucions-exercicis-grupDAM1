package dao;

import db.DatabaseConnection;
import exception.DataAccessException;
import model.Curs;

import java.sql.*;
import java.util.ArrayList;

public class CursDao {

    public void inserir(Curs curs) {
        String sql = "INSERT INTO cursos (nom, nivell) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, curs.getNom());
            ps.setString(2, curs.getNivell());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    curs.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("No s'ha pogut inserir el curs '" + curs.getNom() + "'.", e);
        }
    }

    public Curs buscarPerId(int id) {
        String sql = "SELECT id, nom, nivell FROM cursos WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRowToCurs(rs);
                }
            }
            return null;
        } catch (SQLException e) {
            throw new DataAccessException("No s'ha pogut buscar el curs amb id " + id + ".", e);
        }
    }

    public ArrayList<Curs> llistarTots() {
        ArrayList<Curs> cursos = new ArrayList<>();
        String sql = "SELECT id, nom, nivell FROM cursos ORDER BY id";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                cursos.add(mapRowToCurs(rs));
            }
            return cursos;
        } catch (SQLException e) {
            throw new DataAccessException("No s'ha pogut recuperar el llistat de cursos.", e);
        }
    }

    public boolean actualitzar(Curs curs) {
        String sql = "UPDATE cursos SET nom = ?, nivell = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, curs.getNom());
            ps.setString(2, curs.getNivell());
            ps.setInt(3, curs.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataAccessException("No s'ha pogut actualitzar el curs amb id " + curs.getId() + ".", e);
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

    private Curs mapRowToCurs(ResultSet rs) throws SQLException {
        return new Curs(rs.getInt("id"), rs.getString("nom"), rs.getString("nivell"));
    }
}
