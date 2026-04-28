package service;

import dao.AlumneDao;
import dao.CursDao;
import exception.BusinessException;
import exception.ResourceNotFoundException;
import exception.ValidationException;
import model.Alumne;
import model.Curs;

import java.util.ArrayList;

public class InstitutService {

    private final CursDao cursDao = new CursDao();
    private final AlumneDao alumneDao = new AlumneDao();

    public ArrayList<Curs> obtenirCursos() {
        return cursDao.llistarTots();
    }

    public ArrayList<Alumne> obtenirTotsElsAlumnes() {
        return alumneDao.llistarTots();
    }

    public Alumne obtenirAlumnePerId(int id) {
        validarId(id, "L'id de l'alumne ha de ser positiu.");
        return alumneDao.buscarPerId(id);
    }

    public void crearAlumne(String nom, String email, int cursId) {
        validarText(nom, "El nom no pot estar buit.");
        validarText(email, "L'email no pot estar buit.");
        validarId(cursId, "L'id del curs ha de ser positiu.");

        Curs curs = cursDao.buscarPerId(cursId);
        if (curs == null) {
            throw new ResourceNotFoundException("No existeix cap curs amb id " + cursId + ".");
        }
        if (alumneDao.existeixEmail(email.trim())) {
            throw new ValidationException("Ja existeix un alumne amb aquest email.");
        }
        alumneDao.inserir(new Alumne(nom.trim(), email.trim(), curs));
    }

    public void actualitzarAlumne(int id, String nom, String email, int cursId) {
        validarId(id, "L'id de l'alumne ha de ser positiu.");
        validarText(nom, "El nom no pot estar buit.");
        validarText(email, "L'email no pot estar buit.");
        validarId(cursId, "L'id del curs ha de ser positiu.");

        Alumne alumne = alumneDao.buscarPerId(id);
        if (alumne == null) {
            throw new ResourceNotFoundException("No existeix cap alumne amb id " + id + ".");
        }
        Curs curs = cursDao.buscarPerId(cursId);
        if (curs == null) {
            throw new ResourceNotFoundException("No existeix cap curs amb id " + cursId + ".");
        }
        if (alumneDao.existeixEmailExcloentId(email.trim(), id)) {
            throw new ValidationException("Ja existeix un altre alumne amb aquest email.");
        }

        alumne.setNom(nom.trim());
        alumne.setEmail(email.trim());
        alumne.setCurs(curs);
        alumneDao.actualitzar(alumne);
    }

    public void eliminarAlumne(int id) {
        validarId(id, "L'id de l'alumne ha de ser positiu.");
        if (alumneDao.buscarPerId(id) == null) {
            throw new ResourceNotFoundException("No existeix cap alumne amb id " + id + ".");
        }
        alumneDao.eliminar(id);
    }

    public void eliminarCurs(int id) {
        validarId(id, "L'id del curs ha de ser positiu.");
        if (cursDao.buscarPerId(id) == null) {
            throw new ResourceNotFoundException("No existeix cap curs amb id " + id + ".");
        }
        if (alumneDao.comptarPerCurs(id) > 0) {
            throw new BusinessException("No es pot eliminar el curs perquè encara té alumnes assignats.");
        }
        cursDao.eliminar(id);
    }

    public String mostrarCursAmbAlumnes(int idCurs) {
        validarId(idCurs, "L'id del curs ha de ser positiu.");
        Curs curs = cursDao.buscarPerId(idCurs);
        if (curs == null) {
            throw new ResourceNotFoundException("No existeix cap curs amb id " + idCurs + ".");
        }
        ArrayList<Alumne> alumnes = alumneDao.llistarPerCurs(idCurs);
        StringBuilder sb = new StringBuilder();
        sb.append(curs).append(System.lineSeparator());
        sb.append("Alumnes matriculats:").append(System.lineSeparator());
        if (alumnes.isEmpty()) {
            sb.append("- Aquest curs encara no té alumnes");
        } else {
            for (Alumne alumne : alumnes) {
                sb.append("- ").append(alumne.getNom()).append(" · ").append(alumne.getEmail()).append(System.lineSeparator());
            }
        }
        return sb.toString();
    }

    private void validarId(int id, String missatge) {
        if (id <= 0) {
            throw new ValidationException(missatge);
        }
    }

    private void validarText(String text, String missatge) {
        if (text == null || text.isBlank()) {
            throw new ValidationException(missatge);
        }
    }
}
