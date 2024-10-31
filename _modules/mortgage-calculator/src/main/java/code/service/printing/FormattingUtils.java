package code.service.printing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class FormattingUtils {

    public <T> Collection<Function<T, Object>> descriptorCreator(Function<T, Object> function, Integer iter) {
        Collection<Function<T, Object>> descriptors = new ArrayList<>(iter);
        for (int i = 0; i < iter; i++) {
            descriptors.add(function);
        }
        return descriptors;
    }

    public static <T> List<Object> functionsToObjects(List<FormattingFunction<T>> functions, T object) {
        List<Object> values = new ArrayList<>(functions.size());
        for (FormattingFunction<T> function : functions) {
            values.add(function.apply(object));
        }
        return values;
    }

    public static int validateFormatLength(Format... formats) {
        int arrayLenght = formats[0].size();
        for (Format format : formats) {
            if (format.size() != arrayLenght)
                throw new RuntimeException("Invalid Formats of unequal sizes");
            else {
                arrayLenght = format.size();
            }
        }
        return arrayLenght;
    }
}