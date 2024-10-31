package code.service.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class InputServiceFactory {

    public static InputServiceI get(InputForm inputForm) {
        return inputForm.getInputServiceI();
    }

    @Getter
    @AllArgsConstructor
    public enum InputForm {
        FILE(new InputFromFile()),
        BUILDER(new InputFromBuilder());

        private final InputServiceI inputServiceI;
    }
}
