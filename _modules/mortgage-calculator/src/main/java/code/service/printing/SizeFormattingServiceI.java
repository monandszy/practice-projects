package code.service.printing;

import java.util.Collection;

public interface SizeFormattingServiceI {
    <T> Format getFormats(Collection<T> values, Collection<FormattingFunction<T>> columnDescribers);
    <T> Format getFormats(T value, Collection<FormattingFunction<T>> columnDescribers);
    <T> Format getFormats(Collection<T> values);
    <T> Format valuesToDescribersFormat(Collection<T> keyValues);
}