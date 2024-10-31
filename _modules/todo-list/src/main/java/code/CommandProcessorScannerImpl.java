package code;

import code.commandBuilder.CommandBuilder;
import code.databaseRunner.DatabaseRunner;
import lombok.Value;

import java.util.Scanner;

@Value
public class CommandProcessorScannerImpl implements CommandProcessor {

    Scanner scanner;

    CommandBuilder commandBuilder;

    DatabaseRunner databaseRunner;

    @Override
    public void processCommands() {
        while(scanner.hasNext()) {
            commandBuilder
                    .buildCommand(scanner.nextLine().toUpperCase())
                    .ifPresent(databaseRunner::run);
        }
    }
}
