package code.commandBuilder;

import java.util.List;

public interface Splitter {
    List<String> split(String stringCommand, String regex);
}
