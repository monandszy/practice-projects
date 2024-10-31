package code.commandBuilder;

import code.exceptions.UnknownParameterException;
import code.model.Arguments;
import code.model.Command;

import java.util.Map;

import static code.model.Arguments.Column.DATA;
import static code.model.Arguments.Column.SORT;

public class QueryAdjusterImpl implements QueryAdjuster {
    @Override
    public String prepareQuery(String query, Map<Arguments.Column, String> rawArguments, Command.Type commandType) {
        switch (commandType) {
            case READ_ALL -> query = prepareREAD_ALL(query, rawArguments);
            case COMPLETED -> query = prepareCOMPLETED(query);
        }
        return query;
    }

    private String prepareCOMPLETED(String query) {
        return query.replace("?1", Arguments.Status.COMPLETED.name());
    }


    private String prepareREAD_ALL(String query, Map<Arguments.Column, String> rawArguments) {
        if (rawArguments.isEmpty()) {
            query = query.replace("?1", "NAME").replace("?2", "ASC");
        } else if (rawArguments.size() == 1) {
            String[] split = rawArguments.get(SORT).split(",");
            String orderBy = split[0];
            String orderMode = split[1];
            if (DATA.getName().contains(orderBy) && Arguments.SortDir.valuesAsList().contains(orderMode)) {
                query = query.replace("?1", orderBy).replace("?2", orderMode);
            } else {
                throw new UnknownParameterException(String.format(
                        "Unknown orderBy: [%s] or orderMode: [%s] %n",
                        orderBy, orderMode));
            }
        } else {
            throw new UnknownParameterException(String.format(
                    "Wrong number of READ_ALL parameters expected 0 or 1 found: [%s]%n",
                    rawArguments.size()));
        }
        return query;
    }
}
