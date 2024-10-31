package code.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Value
public class Command {

    String query;

    Type type;

    List<String> arguments;

    @Getter
    @AllArgsConstructor
    public enum Type {
        CREATE("CREATE"),
        UPDATE("UPDATE"),
        DELETE("DELETE"),
        DELETE_ALL("DELETE ALL"),
        READ("READ"),
        READ_ALL("READ ALL"),
        READ_GROUPED("READ GROUPED"),
        COMPLETED("COMPLETED");

        private final String name;

        public static Optional<Type> from(String stringCommandType) {
            return Arrays.stream(Type.values())
                    .filter(e -> e.name.equals(stringCommandType))
                    .findAny();
        }

        public static List<String> valuesAsList() {
            return Arrays.stream(Type.values())
                    .map(Type::name)
                    .toList();
        }
    }
}
