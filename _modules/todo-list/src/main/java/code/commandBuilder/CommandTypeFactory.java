package code.commandBuilder;

import code.model.Command;

import java.util.List;

public interface CommandTypeFactory {
    Command.Type getCommandType(List<String> split);

    void removeStringCommandTypeFromSplit(List<String> split);
}
