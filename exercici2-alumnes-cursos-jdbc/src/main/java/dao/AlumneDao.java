package dao;

import db.DatabaseConnection;
import exception.DataAccessException;
import model.Alumne;
import model.Curs;

import java.sql.*;
import java.util.ArrayList;

public class AlumneDao {

    public void inserir(Alumne alumne) {
        String sql = "INSERT INTO alumnes (nom, email, curs_id) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, alumne.getNom());
            ps.setString(2, alumne.getEmail());
            ps.setInt(3, alumne.getCurs().getId());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    alumne.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("No s'ha pogut inserir l'alumne amb email " + alumne.getEmail() + ".", e);
        }
    }

    public Alumne buscarPerId(int id) {
        String sql = "SELECT a.id, a.nom, a.email, c.id AS curs_id, c.nom AS curs_nom, c.nivell AS curs_nivell "
                + "FROM alumnes a JOIN cursos c ON a.curs_id = c.id WHERE a.id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRowToAlumne(rs);
                }
            }
            return null;
        } catch (SQLException e) {
            throw new DataAccessException("No s'ha pogut buscar l'alumne amb id " + id + ".", e);
        }
    }

    public ArrayList<Alumne> llistarTots() {
        ArrayList<Alumne> alumnes = new ArrayList<>();
        String sql = "SELECT a.id, a.nom, a.email, c.id AS curs_id, c.nom AS curs_nom, c.nivell AS curs_nivell "
                + "FROM alumnes a JOIN cursos c ON a.curs_id = c.id ORDER BY a.id";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                alumnes.add(mapRowToAlumne(rs));
            }
            return alumnes;
        } catch (SQLException e) {
            throw new DataAccessException("No s'ha pogut recuperar el llistat d'alumnes.", e);
        }
    }

    public ArrayList<Alumne> llistarPerCurs(int cursId) {
        ArrayList<Alumne> alumnes = new ArrayList<>();
        String sql = "SELECT a.id, a.nom, a.email, c.id AS curs_id, c.nom AS curs_nom, c.nivell AS curs_nivell "
                + "FROM alumnes a JOIN cursos c ON a.curs_id = c.id WHERE c.id = ? ORDER BY a.id";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cursId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    alumnes.add(mapRowToAlumne(rs));
                }
            }
            return alumnes;
        } catch (SQLException e) {
            throw new DataAccessException("No s'han pogut recuperar els alumnes del curs amb id " + cursId + ".", e);
        }
    }

    public boolean actualitzar(Alumne alumne) {
        String sql = "UPDATE alumnes SET nom = ?, email = ?, curs_id = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, alumne.getNom());
            ps.setString(2, alumne.getEmail());
            ps.setInt(3, alumne.getCurs().getId());
            ps.setInt(4, alumne.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataAccessException("No s'ha pogut actualitzar l'alumne amb id " + alumne.getId() + ".", e);
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM alumnes WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataAccessException("No s'ha pogut eliminar l'alumne amb id " + id + ".", e);
        }
    }

    public int comptarPerCurs(int cursId) {
        String sql = "SELECT COUNT(*) FROM alumnes WHERE curs_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cursId);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new DataAccessException("No s'ha pogut comptar els alumnes del curs amb id " + cursId + ".", e);
        }
    }

    public boolean existeixEmailExcloentId(String email, int id) {
        String sql = "SELECT id FROM alumnes WHERE email = ? AND id <> ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setInt(2, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new DataAccessException("No s'ha pogut comprovar l'email " + email + ".", e);
        }
    }

    public boolean existeixEmail(String email) {
        String sql = "SELECT id FROM alumnes WHERE email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new DataAccessException("No s'ha pogut comprovar l'email " + email + ".", e);
        }
    }

    private Alumne mapRowToAlumne(ResultSet rs) throws SQLException {
        Curs curs = new Curs(rs.getInt("curs_id"), rs.getString("curs_nom"), rs.getString("curs_nivell"));
        return new Alumne(rs.getInt("id"), rs.getString("nom"), rs.getString("email"), curs);
    }
}
