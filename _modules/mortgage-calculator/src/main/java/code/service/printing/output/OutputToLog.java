package code.service.printing.output;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OutputToLog implements OutputServiceI{
    @Override
    public void save(String message) {
        log.info(message);
    }
}