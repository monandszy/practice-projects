package code.commandBuilder;

import code.exceptions.UnknownParameterException;
import code.model.Command;

import java.util.List;

public class CommandTypeFactoryImpl implements CommandTypeFactory {

    private static final int STRING_COMMAND_TYPE_POSITION = 0;

    @Override
    public Command.Type getCommandType(List<String> split) {
        String stringCommandType = split.get(STRING_COMMAND_TYPE_POSITION);
        removeStringCommandTypeFromSplit(split);
        return Command.Type.from(stringCommandType).orElseThrow(
                () -> new UnknownParameterException(String.format(
                        "Unknown Command Type: [%s]%n", stringCommandType)
                ));
    }

    @Override
    public void removeStringCommandTypeFromSplit(List<String> split) {
        split.remove(STRING_COMMAND_TYPE_POSITION);
    }
}
