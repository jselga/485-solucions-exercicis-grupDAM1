# Solució projecte Java · Exercici 2 (Alumnes i cursos)

Aquest projecte resol l'exercici 2 del bloc JDBC amb excepcions.

## Què mostra

- Relació 1:N entre `cursos` i `alumnes`
- `ResultSet` mapejat a `Alumne` i `Curs`
- Validacions a `service`
- Esborrat de curs bloquejat si encara té alumnes

## Crear la base de dades

Executa `sql/institut_ex2.sql` a MySQL.

## Configuració

Edita `src/main/resources/db.properties` si cal.

## Executar

```bash
mvn compile
mvn exec:java
```
