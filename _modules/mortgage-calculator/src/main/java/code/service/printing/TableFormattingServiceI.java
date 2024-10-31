package code.service.printing;

import java.util.Collection;
import java.util.List;

public interface TableFormattingServiceI {

    <T> Format collectionFormatter(Collection<T> values, List<FormattingFunction<T>> columnDescribers);

    Format multiCollectionFormatter(Collection<?>... collections);

    <T, R> Format KeyValueFormatter(
            Collection<T> keyValues,
            Collection<R> valueValues, Collection<FormattingFunction<R>> valueDescribers
    );

    SizeFormattingServiceI getFormatter();

    ColumnFormattingServiceI getColumnFormattingService();

    RowFormattingServiceI getRowFormattingService();


}