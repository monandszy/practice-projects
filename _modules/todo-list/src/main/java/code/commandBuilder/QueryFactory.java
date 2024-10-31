package code.commandBuilder;

import code.model.Command;

public interface QueryFactory {
    String getQuery(Command.Type commandType);
}
