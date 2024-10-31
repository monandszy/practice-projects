package code.service.printing;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RowFormattingService implements RowFormattingServiceI {
    public <T, R> StringBuilder getKeyValueFormattedRow(
            Format entryFormat,
            List<R> keys,
            T value,
            List<FormattingFunction<T>> valueDescribers
    ) {
        List<R> keyList = new ArrayList<>(keys);
        List<FormattingFunction<T>> valueDescriberList = new ArrayList<>(valueDescribers);
        StringBuilder formattedRow = new StringBuilder();
        for (int i = 0; i < keys.size(); i++) {
            formattedRow.append(entryFormat.getValue(i)
                    .formatted(keyList.get(i), valueDescriberList.get(i).apply(value)));
        }
        return formattedRow;
    }

    public <T> StringBuilder getValueFormattedRow(
            Format columnFormat,
            T value,
            List<FormattingFunction<T>> valueDescribers
    ) {
        List<FormattingFunction<T>> listValues = new ArrayList<>(valueDescribers);
        StringBuilder formattedRow = new StringBuilder();
        for (int i = 0; i < columnFormat.size(); i++) {
            formattedRow.append(columnFormat.getValue(i)
                    .formatted(listValues.get(i).apply(value)));
        }
        return formattedRow;
    }

    public StringBuilder getMultiCollectionFormattedRow(Format format, int index, List<?>... collections) {
        StringBuilder formattedRow = new StringBuilder();
        int counter = 0;
        for (List<?> collection : collections) {
            formattedRow.append(format.getValue(counter)
                    .formatted(collection.get(index)));
            counter++;
        }
        return formattedRow;
    }
}