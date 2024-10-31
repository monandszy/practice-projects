package code.service.printing.output;

import org.springframework.stereotype.Service;

@Service
public class OutputToConsole implements OutputServiceI{
    @Override
    public void save(String message) {
        System.out.println(message);
    }
}