package code.service.printing;

import java.util.List;

public interface RowFormattingServiceI {
    <T, R> StringBuilder getKeyValueFormattedRow(
            Format entryFormat,
            List<R> keys,
            T value,
            List<FormattingFunction<T>> valueDescribers
    );

    <T> StringBuilder getValueFormattedRow(
            Format columnFormat,
            T value,
            List<FormattingFunction<T>> valueDescribers
    );

    StringBuilder getMultiCollectionFormattedRow(Format format, int index, List<?>... collections);
}