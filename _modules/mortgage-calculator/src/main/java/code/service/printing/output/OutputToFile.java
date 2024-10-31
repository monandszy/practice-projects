package code.service.printing.output;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

@Service
public class OutputToFile implements OutputServiceI {

    private final Path SAVE_FILE_PATH = Path.of("src/main/resources/OutputData.txt");

    @SneakyThrows
    public OutputToFile() {
        if (Files.exists(SAVE_FILE_PATH)) {
            Files.writeString(SAVE_FILE_PATH, "", StandardOpenOption.TRUNCATE_EXISTING);
        } else {
            Files.createFile(SAVE_FILE_PATH);
        }
    }

    @Override
    public void save(String message) {
        try {
            Files.writeString(SAVE_FILE_PATH, message, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
            Files.writeString(SAVE_FILE_PATH, "\n", StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.printf("Error: [%s] while loading OutputData to Path: [%s]%n", e.getMessage(), SAVE_FILE_PATH);
        }
    }
}