package code.service.printing;

import java.util.function.Function;

@FunctionalInterface
public interface FormattingFunction <T> extends Function<T, Object> {
    @Override
    Object apply(T t);
}