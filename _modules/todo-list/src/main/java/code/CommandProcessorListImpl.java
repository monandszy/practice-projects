package code;

import code.commandBuilder.CommandBuilder;
import code.databaseRunner.DatabaseRunner;
import lombok.Value;

import java.util.List;
import java.util.Optional;

@Value
public class CommandProcessorListImpl implements CommandProcessor {

    List<String> stringCommands;

    CommandBuilder commandBuilder;

    DatabaseRunner databaseRunner;

    @Override
    public void processCommands() {
        stringCommands.stream()
                .map(commandBuilder::buildCommand)
                .filter(Optional::isPresent)
                .map(Optional::get)
//                .peek(System.out::println)
                .forEach(databaseRunner::run);
    }


}
