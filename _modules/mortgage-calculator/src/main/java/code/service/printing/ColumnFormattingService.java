package code.service.printing;

import lombok.Builder;
import lombok.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static code.service.printing.FormattingUtils.validateFormatLength;

@Builder
@Value
@Service
public class ColumnFormattingService implements ColumnFormattingServiceI{

    String EntryColumnFormat = "| %s: %s ";
    String multiColumnFormat = "| %s ";
    String emptyFormat = "%s";
    String emptyColumnFormat = "|%s%s   ";

    public Format getEntryFormat(Format keyFormat, Format valueFormat) {
        int numberOfColumns = validateFormatLength(keyFormat, valueFormat);
        String[] entryColumns = new String[numberOfColumns];
        for (int i = 0; i < numberOfColumns; i++) {
            if (keyFormat.getAsArray()[i].equals(emptyFormat)) {
                entryColumns[i] = emptyColumnFormat.formatted(keyFormat.getValue(i), valueFormat.getValue(i));
            } else {
                entryColumns[i] = EntryColumnFormat.formatted(keyFormat.getValue(i), valueFormat.getValue(i));
            }
        }
        return new Format(entryColumns);
    }

    public Format getColumnFormat(Format format) {
        int numberOfColumns = format.size();
        String[] columns = new String[numberOfColumns];
        for (int i = 0; i < numberOfColumns; i++) {
            columns[i] = multiColumnFormat.formatted(format.getValue(i));
        }
        return new Format(columns);
    }


    //     Value1, Value2, Value1, Value2, Value1, Value2
    public Format getColumnFormatInTurns(Format... formats) {
        int formatLength = validateFormatLength(formats);
        int numberOfColumns = formats.length;
        String[] columns = new String[numberOfColumns];
        for (int i = 0; i < formatLength; i++) {
            for (int j = 0; j < numberOfColumns; j++) {
                columns[j] = multiColumnFormat.formatted(formats[j].getValue(i));
            }
        }
        return new Format(columns);
    }

    // Value1, Value1, Value1, Value2, Value2, Value2
    public Format getColumnFormatInLine(Format... formats) {
        int numberOfColumns = Arrays.stream(formats).map(Format::size).reduce(0, Integer::sum);
        List<String> columns = new ArrayList<>(numberOfColumns);
        for (Format format : formats) {
            StringBuilder sb = new StringBuilder();
            for (String s : format.getAsArray()) {
                sb.append(multiColumnFormat.formatted(s));
            }
            columns.add(sb.toString());
        }
        return new Format(columns);

    }
}