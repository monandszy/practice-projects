package code.service.printing;

import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
public final class Format {
    private final String[] stringsForFormat;

    public Format(Collection<String> stringsForFormat) {
        this.stringsForFormat = stringsForFormat.toArray(new String[stringsForFormat.size()]);
    }

    public String[] getAsArray() {
        return stringsForFormat;
    }

    public List<String> getAsList() {
        return Arrays.asList(stringsForFormat);
    }

    public String getValue(int index) {
        return stringsForFormat[index];
    }

    public int size() {
        return stringsForFormat.length;
    }
}