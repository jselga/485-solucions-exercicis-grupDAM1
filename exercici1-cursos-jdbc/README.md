# Solució projecte Java · Exercici 1 (Cursos)

Aquest projecte resol l'exercici 1 del bloc JDBC amb excepcions.

## Què mostra

- CRUD complet sobre `cursos`
- Validació a `service`
- `SQLException` transformada en `DataAccessException` a `dao`
- `catch` principal a `app`

## Crear la base de dades

Executa `sql/institut_ex1.sql` a MySQL.

## Configuració

Edita `src/main/resources/db.properties` si cal.

## Executar

```bash
mvn compile
mvn exec:java
```
