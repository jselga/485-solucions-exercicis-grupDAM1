package service;

import dao.CursDao;
import exception.ResourceNotFoundException;
import exception.ValidationException;
import model.Curs;

import java.util.ArrayList;
import java.util.Set;

public class CursService {

    // private static final Set<String> NIVELLS_PERMESOS = Set.of("1r DAM", "2n DAM", "1r DAW", "2n DAW");
    private final CursDao cursDao;

    public CursService() {
        this.cursDao = new CursDao();
    }

    public void crearCurs(String nom, String nivell) {
        validarNom(nom);
        validarNivell(nivell);
        cursDao.inserir(new Curs(nom.trim(), nivell.trim()));
    }

    public Curs obtenirPerId(int id) {
        validarId(id, "L'id del curs ha de ser positiu.");
        return cursDao.buscarPerId(id);
    }

    public ArrayList<Curs> obtenirTots() {
        return cursDao.llistarTots();
    }

    public void actualitzarCurs(int id, String nom, String nivell) {
        validarId(id, "L'id del curs ha de ser positiu.");
        validarNom(nom);
        validarNivell(nivell);

        Curs curs = cursDao.buscarPerId(id);
        if (curs == null) {
            throw new ResourceNotFoundException("No existeix cap curs amb id " + id + ".");
        }

        curs.setNom(nom.trim());
        curs.setNivell(nivell.trim());
        cursDao.actualitzar(curs);
    }

    public void eliminarCurs(int id) {
        validarId(id, "L'id del curs ha de ser positiu.");
        Curs curs = cursDao.buscarPerId(id);
        if (curs == null) {
            throw new ResourceNotFoundException("No existeix cap curs amb id " + id + ".");
        }
        cursDao.eliminar(id);
    }

    private void validarNom(String nom) {
        if (nom == null || nom.isBlank()) {
            throw new ValidationException("El nom del curs no pot estar buit.");
        }
    }

    private void validarNivell(String nivell) {
        if (nivell == null || nivell.isBlank()) {
            throw new ValidationException("El nivell no pot estar buit.");
        }
        // if (!NIVELLS_PERMESOS.contains(nivell.trim())) {
        //     throw new ValidationException("El nivell no és permès.");
        // }
    }

    private void validarId(int id, String missatge) {
        if (id <= 0) {
            throw new ValidationException(missatge);
        }
    }
}
