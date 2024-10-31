package code.commandBuilder;

import code.exceptions.UnknownParameterException;
import code.model.Command;

import java.util.Optional;

import static code.model.TableData.QUERIES;

public class QueryFactoryImpl implements QueryFactory {
    @Override
    public String getQuery(Command.Type commandType) {
        return Optional.ofNullable(QUERIES.get(commandType)).orElseThrow(
                () -> new UnknownParameterException(String.format(
                        "Command Type Not In QUERIES, Provided: [%s]%n", commandType)
                ));
    }
}
