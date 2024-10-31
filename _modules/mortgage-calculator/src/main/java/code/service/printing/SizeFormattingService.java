package code.service.printing;

import lombok.Builder;
import lombok.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

@Value
@Builder
@Service
public class SizeFormattingService implements SizeFormattingServiceI {

    String emptyFormat = "%s";
    String dataFormat = "%%%ds";

    public <T> Format getFormats(Collection<T> values, Collection<FormattingFunction<T>> columnDescribers) {
        Integer[] columnSizes = calculateMaxColumnSize(values, columnDescribers);
        String[] stringsForFormatting = createStringsForFormatting(columnSizes);
        return new Format(stringsForFormatting);
    }

    public <T> Format getFormats(T value, Collection<FormattingFunction<T>> columnDescribers) {
        Integer[] columnSizes = calculateMaxColumnSize(List.of(value), columnDescribers);
        String[] stringsForFormatting = createStringsForFormatting(columnSizes);
        return new Format(stringsForFormatting);
    }

    public <T> Format getFormats(Collection<T> values) {
        Integer[] columnSizes = calculateMaxColumnSize(values, List.of(e -> e));
        String[] stringsForFormatting = createStringsForFormatting(columnSizes);
        return new Format(stringsForFormatting);
    }

    public <T> Format valuesToDescribersFormat(Collection<T> keyValues) {
        Function<T, Object> constantKeyFunction = e -> Math.max(e.toString().length(), 0);
        int numberOfColumns = keyValues.size();
        List<Integer> columnSizes = new ArrayList<>(numberOfColumns);
        for (T keyValue : keyValues) {
            columnSizes.add((Integer) constantKeyFunction.apply(keyValue));
        }
        Integer[] columnSizesArray = columnSizes.toArray(new Integer[numberOfColumns]);
        String[] stringsForFormatting = createStringsForFormatting(columnSizesArray);
        return new Format(stringsForFormatting);
    }

    private <T> Integer[] calculateMaxColumnSize(Collection<T> values, Collection<FormattingFunction<T>> columnDescribers) {
        int numberOfColumns = columnDescribers.size();
        List<Integer> columnSizes = new ArrayList<>(numberOfColumns);
        for (var columnValue : columnDescribers) {
            Integer columnSize = values.stream()
                    .map(value -> columnValue.apply(value).toString())
                    .map(String::length)
                    .max(Comparator.naturalOrder())
                    .orElseGet(() -> 0);
            columnSizes.add(columnSize);
        }
        return columnSizes.toArray(new Integer[numberOfColumns]);
    }

    private String[] createStringsForFormatting(Integer[] columnSizes) {
        int numberOfColumns = columnSizes.length;
        String[] stringsForFormat = new String[numberOfColumns];
        for (int i = 0; i < numberOfColumns; i++) {
            if (columnSizes[i] == 0) {
                stringsForFormat[i] = emptyFormat;
            } else {
                stringsForFormat[i] = dataFormat.formatted(columnSizes[i]);
            }
        }
        return stringsForFormat;
    }
}