package code.commandBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SplitterImpl implements Splitter {
    @Override
    public List<String> split(String stringCommand, String regex) {
        return new ArrayList<>(Arrays.asList(stringCommand.split(regex)));
    }
}
