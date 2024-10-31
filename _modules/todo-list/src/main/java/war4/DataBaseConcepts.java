package war4;

import java.util.Locale;

public class DataBaseConcepts {
    // CRUD - create, read, update, delete.
    // relational / NoSQL
    // SQL - Structured Query Language
    // DBMS - database management system
    // PostgresSQL, Oracle DataBase, MySQL - interface
    // Server - dostarcza funkcjonalność, warstwa izolacji. - Client
    // Czym są dane? - tabelki.
    // JDBC - Java DataBase Connectivity
    // JPA - Java Presistance API

    // Data integrity
    // entity integrity - different indexes
    // domain integrity - set data types
    // referential integrity - multiple databases (relations between)
    // client-defined integrity - custom rules (max ten signs etc.)

    // Constraints - examples
    // NOT NULL
    // UNIQUE
    // DEFAULT
    // CHECK - format
    // PRIMARY KEY - index // auto_increment
    // FOREIGN KEY - other database's key

    public static void main(String[] args) {
        Locale locale = Locale.getDefault();
        System.out.println(locale);
    }

}
