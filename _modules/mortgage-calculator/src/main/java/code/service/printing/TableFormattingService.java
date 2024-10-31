package code.service.printing;

import lombok.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Value
@Service
public class TableFormattingService implements TableFormattingServiceI {

    SizeFormattingServiceI formatter;
    ColumnFormattingServiceI columnFormattingService;
    RowFormattingServiceI rowFormattingService;

    // each describer a column
    public <T> Format collectionFormatter(Collection<T> values, List<FormattingFunction<T>> columnDescribers) {
        return columnFormattingService.getColumnFormat(formatter.getFormats(values, columnDescribers));
    }

    // each collection a column, one describer.
    public Format multiCollectionFormatter(Collection<?>... collections) {
        List<Format> formats = new ArrayList<>(collections.length);
        for (Collection<?> collection : collections) {
            formats.add(formatter.getFormats(collection));
        }
        return columnFormattingService.getColumnFormatInLine(
                formats.toArray(new Format[collections.length]));
    }

    // transforms the key list into list of formats, then adds it with values to form pairs [k:v]
    public <T, R> Format KeyValueFormatter(
            Collection<T> keyValues,
            Collection<R> valueValues, Collection<FormattingFunction<R>> valueDescribers
    ) {
        Format keyFormat = formatter.valuesToDescribersFormat(keyValues);
        Format valueFormat = formatter.getFormats(valueValues, valueDescribers);
        return columnFormattingService.getEntryFormat(keyFormat, valueFormat);
    }
}