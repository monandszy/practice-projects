package code.commandBuilder;

import code.model.Command;

import java.util.Optional;

public interface CommandBuilder {
    Optional<Command> buildCommand(String command);
}
