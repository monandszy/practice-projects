package code.model;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static code.model.Arguments.Column.DATA;
import static code.model.Arguments.Column.DEADLINE;
import static code.model.Arguments.Column.DESCRIPTION;
import static code.model.Arguments.Column.NAME;
import static code.model.Arguments.Column.PRIORITY;
import static code.model.Arguments.Column.SORT;
import static code.model.Command.Type.COMPLETED;
import static code.model.Command.Type.CREATE;
import static code.model.Command.Type.DELETE;
import static code.model.Command.Type.DELETE_ALL;
import static code.model.Command.Type.READ;
import static code.model.Command.Type.READ_ALL;
import static code.model.Command.Type.READ_GROUPED;
import static code.model.Command.Type.UPDATE;

public interface TableData {
    String CREATE_TABLE_QUERY =
            "CREATE TABLE TODOLIST" +
                    "(" +
                    "    ID          SERIAL       NOT NULL," +
                    "    NAME        VARCHAR(20)  NOT NULL," +
                    "    DESCRIPTION VARCHAR(128) NOT NULL," +
                    "    DEADLINE    TIMESTAMP WITH TIME ZONE," +
                    "    PRIORITY    INT          NOT NULL," +
                    "    PRIMARY KEY (ID)," +
                    "    UNIQUE (NAME)" +
                    ");";

    String ALTER_TABLE_QUERY =
            "ALTER TABLE TODOLIST" +
                    "    ADD COLUMN STATUS VARCHAR(20) NOT NULL DEFAULT 'TODO';";



    DateTimeFormatter INPUT_DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd.MM.uuuu HH:mm");

    DateTimeFormatter DATABASE_DATE_FORMAT =
            DateTimeFormatter.ofPattern("uuuu.MM.dd HH:mm");

    Map<Command.Type, String> QUERIES = Map.of(
            CREATE, "INSERT INTO TODOLIST (NAME, DESCRIPTION, DEADLINE, PRIORITY) VALUES (?, ?, ?::timestamp, ?::int);",
            Command.Type.UPDATE, "UPDATE TODOLIST SET DESCRIPTION = ?, DEADLINE = ?::timestamp, PRIORITY = ?::int WHERE NAME = ?;",
            Command.Type.COMPLETED, "UPDATE TODOLIST SET STATUS = '?1' WHERE NAME = ?;",
            Command.Type.DELETE, "DELETE FROM TODOLIST WHERE NAME = ?;",
            Command.Type.DELETE_ALL, "DELETE FROM TODOLIST;",
            Command.Type.READ, "SELECT * FROM TODOLIST WHERE NAME = ?;",
            Command.Type.READ_ALL, "SELECT * FROM TODOLIST ORDER BY ?1 ?2;",
            Command.Type.READ_GROUPED, "SELECT DATE(deadline) AS DATE, ARRAY_AGG(name) AS TASKS FROM TODOLIST GROUP BY DATE(deadline) ORDER BY DATE DESC;"
    );

    Map<Command.Type, List<Arguments.Column>> ARGUMENT_ORDER = Map.of(
            CREATE, List.of(NAME, DESCRIPTION, DEADLINE, PRIORITY),
            UPDATE, List.of(DESCRIPTION, DEADLINE, PRIORITY, NAME),
            DELETE, List.of(NAME),
            DELETE_ALL, List.of(),
            COMPLETED, List.of(NAME),
            READ, List.of(NAME),
            READ_ALL, List.of(DATA, SORT), //!
            READ_GROUPED, List.of()
    );
}
