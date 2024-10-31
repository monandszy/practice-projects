package code.service.printing.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
public class OutputServiceFactory {

    public static OutputServiceI get(OutputTo outputTo) {
        return outputTo.getOutputServiceI();
    }

    @Getter
    @AllArgsConstructor
    public enum OutputTo {
        FILE(new OutputToFile()),
        CONSOLE(new OutputToConsole()),
        LOG(new OutputToLog());

        private final OutputServiceI outputServiceI;
    }
}