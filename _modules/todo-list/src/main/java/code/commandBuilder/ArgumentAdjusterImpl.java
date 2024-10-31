package code.commandBuilder;

import code.exceptions.UnknownParameterException;
import code.model.Arguments;
import code.model.Command;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static code.model.Arguments.Column.DEADLINE;
import static code.model.TableData.ARGUMENT_ORDER;
import static code.model.TableData.DATABASE_DATE_FORMAT;
import static code.model.TableData.INPUT_DATE_FORMAT;

public class ArgumentAdjusterImpl implements ArgumentAdjuster {
    @Override
    public List<String> prepareArguments(Map<Arguments.Column, String> rawArguments, Command.Type commandType) {
        List<String> preparedArguments = new ArrayList<>();
        List<Arguments.Column> argumentOrder = getArgumentOrder(commandType);
        switch (commandType) {
            case CREATE, UPDATE, DELETE, DELETE_ALL, READ, READ_GROUPED, COMPLETED ->
                    adjustOrder(rawArguments, argumentOrder, preparedArguments);
        }
        return preparedArguments;
    }

    private static void adjustOrder(Map<Arguments.Column, String> rawArguments, List<Arguments.Column> argumentOrder, List<String> preparedArguments) {
        for (Arguments.Column column : argumentOrder) {
            String argument = Optional.ofNullable(rawArguments.get(column)).orElseThrow(
                    () -> new UnknownParameterException(String.format(
                            "Not found Field: [%s] " +
                                    "from ARGUMENT_ORDER: [%s] " +
                                    "in rawArguments: [%s] %n",
                            column, argumentOrder, rawArguments.keySet())
                    ));
            if (column.equals(DEADLINE)) {
                LocalDateTime starting = LocalDateTime.parse(argument, INPUT_DATE_FORMAT);
                argument = starting.format(DATABASE_DATE_FORMAT);
            }
            preparedArguments.add(argument);
        }
    }

    private static List<Arguments.Column> getArgumentOrder(Command.Type commandType) {
        return Optional.ofNullable(ARGUMENT_ORDER.get(commandType)).orElseThrow(
                () -> new UnknownParameterException(String.format(
                        "Command Type Not In ARGUMENT_ORDER, Provided: [%s]%n", commandType)
                ));
    }
}
