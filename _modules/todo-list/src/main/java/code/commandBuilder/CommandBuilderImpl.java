package code.commandBuilder;

import code.exceptions.UnknownParameterException;
import code.model.Command;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Value
@Slf4j
public class CommandBuilderImpl implements CommandBuilder {

    Splitter splitter;
    CommandTypeFactory commandTypeFactory;
    ArgumentExtractor argumentExtractor;
    QueryFactory queryFactory;
    ArgumentAdjuster argumentAdjuster;
    QueryAdjuster queryAdjuster;

    @Override
    public Optional<Command> buildCommand(String stringCommand) {
        try {
            List<String> split = splitter.split(stringCommand, ";");
            Command.Type commandType = commandTypeFactory.getCommandType(split);
            var rawArguments = argumentExtractor.getArguments(split);
            String query = queryFactory.getQuery(commandType);

            List<String> preparedArguments = argumentAdjuster.prepareArguments(rawArguments, commandType);
            String preparedQuery = queryAdjuster.prepareQuery(query, rawArguments, commandType);

            return Optional.of(new Command(preparedQuery, commandType, preparedArguments));
        } catch (UnknownParameterException e) {
            log.error(e.getMessage());
            return Optional.empty();
        }
    }
}
