package code.commandBuilder;

import code.model.Arguments;

import java.util.List;
import java.util.Map;

public interface ArgumentExtractor {

    Map<Arguments.Column, String> getArguments(List<String> stringArguments);
}
