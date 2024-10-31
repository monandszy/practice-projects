package code.commandBuilder;

import code.model.Arguments;
import code.model.Command;

import java.util.Map;

public interface QueryAdjuster {
    String prepareQuery(String query, Map<Arguments.Column, String> rawArguments, Command.Type commandType);
}
