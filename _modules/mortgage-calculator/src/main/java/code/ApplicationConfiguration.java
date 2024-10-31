package code;


import code.service._ComponentScanMarker;
import code.service.input.InputServiceFactory;
import code.service.input.InputServiceI;
import code.service.printing.output.OutputServiceFactory;
import code.service.printing.output.OutputServiceI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {_ComponentScanMarker.class})
public class ApplicationConfiguration {
    @Bean
    public OutputServiceI outputServiceI() {
        return OutputServiceFactory.get(OutputServiceFactory.OutputTo.FILE);
    }

    @Bean
    public InputServiceI inputServiceI() {
        return InputServiceFactory.get(InputServiceFactory.InputForm.BUILDER);
    }


}