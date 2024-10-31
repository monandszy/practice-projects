package code.commandBuilder;

import code.model.Arguments;
import code.model.Command;

import java.util.List;
import java.util.Map;

public interface ArgumentAdjuster {
    List<String> prepareArguments(Map<Arguments.Column, String> rawArguments, Command.Type commandType);
}
