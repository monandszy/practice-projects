package code.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Value
public class Arguments {
    String name;
    String description;
    LocalDateTime deadline;
    Integer priority;
    Status status;
    SortDir sort;

    @Getter
    @AllArgsConstructor
    public enum Column {
        DATA("NAME, DESCRIPTION, DEADLINE, PRIORITY, STATUS"),
        NAME("NAME"),
        DESCRIPTION("DESCRIPTION"),
        DEADLINE("DEADLINE"),
        PRIORITY("PRIORITY"),
        STATUS("STATUS"),
        SORT("SORT");

        private final String name;

        public static Optional<Column> from(String stringColumn) {
            return Arrays.stream(Column.values())
                    .filter(e -> e.name.equals(stringColumn))
                    .findAny();
        }
    }

    @Getter
    @AllArgsConstructor
    public enum SortDir {
        ASC("ASC"),
        DESC("DESC");

        private final String name;

        public static List<String> valuesAsList() {
            return Arrays.stream(SortDir.values())
                    .map(SortDir::getName)
                    .toList();
        }
    }

    public enum Status {
        TODO,
        COMPLETED
    }
}
