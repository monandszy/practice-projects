package code.commandBuilder;

import code.exceptions.UnknownParameterException;
import code.model.Arguments;
import lombok.Value;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Value
public class ArgumentExtractorImpl implements ArgumentExtractor {

    private static final int ARGUMENT_FIELD_POSITION_IN_ARGUMENT = 0;
    private static final int ARGUMENT_VALUE_POSITION_IN_ARGUMENT = 1;

    Splitter splitter;

    @Override
    public Map<Arguments.Column, String> getArguments(List<String> stringArguments) {
        Map<Arguments.Column, String> arguments = new HashMap<>();
        for (String stringArgument : stringArguments) {
            List<String> split = splitter.split(stringArgument, "=");
            String stringArgumentField = split.get(ARGUMENT_FIELD_POSITION_IN_ARGUMENT);
            String stringArgumentValue = split.get(ARGUMENT_VALUE_POSITION_IN_ARGUMENT);
            Arguments.Column column = Arguments.Column.from(stringArgumentField).orElseThrow(
                    () -> new UnknownParameterException(String.format(
                            "Unknown Field: [%s]%n", stringArgumentField)
                    ));
            arguments.put(column, stringArgumentValue);
        }
        return arguments;
    }
}
