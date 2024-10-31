package code;

import de.elnarion.util.plantuml.generator.classdiagram.PlantUMLClassDiagramGenerator;
import de.elnarion.util.plantuml.generator.classdiagram.config.PlantUMLClassDiagramConfigBuilder;
import de.elnarion.util.plantuml.generator.sequencediagram.PlantUMLSequenceDiagramGenerator;
import de.elnarion.util.plantuml.generator.sequencediagram.config.PlantUMLSequenceDiagramConfigBuilder;
import de.elnarion.util.plantuml.generator.sequencediagram.exception.NotFoundException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

public class PlantUmlCreator {

    // every ..|> to ..u|>
    private static final String STARTUML_TO_REPLACE = "@startuml";
    private static final String STARTUML_REPLACEMENT = """
            @startuml
            !theme vibrant
            skinparam classAttributeIconSize 0
            left to right direction
            """;

    private static final String SAVE_DIRECTORY = "plantUML";

    public static void main(String[] args) {
//        Optional<String> s = createSequenceDiagram(MortgageCalculationService.class, "calculate");
        Optional<String> diagramText = createClassDiagram("code");
        saveToFile(diagramText, "ClassDiagram.puml");

    }

    private static Optional<String> createSequenceDiagram(Class inputClass, String method) {
        var builder = new PlantUMLSequenceDiagramConfigBuilder(
                inputClass.getName(),
                method);
        var generator = new PlantUMLSequenceDiagramGenerator(builder.build());
        try {
            return Optional.of(generator.generateDiagramText());
        } catch (NotFoundException e) {
            System.err.printf("Exception while generating SequenceDiagram: %s", e.getMessage());
        }
        return Optional.empty();
    }

    private static Optional<String> createClassDiagram(String packageName) {
        var config = new PlantUMLClassDiagramConfigBuilder(List.of(packageName))
                .withRemoveFields(true)
                .withHideMethods(true)
                .build();
        var generator = new PlantUMLClassDiagramGenerator(config);
        return Optional.of(generator.generateDiagramText()
                .replace(STARTUML_TO_REPLACE, STARTUML_REPLACEMENT)
                .replace(packageName + ".", "")
        );
    }

    private static void saveToFile(Optional<String> diagramText, String fileName) {
        if (diagramText.isPresent()) {
            try {
                writeToFile(diagramText.get(), fileName);
            } catch (IOException e) {
                System.err.printf("Exception while saving to file: %s", e.getMessage());
            }
        }
    }

    private static void writeToFile(String generatedDiagram, String fileName) throws IOException {
        Path directory = Paths.get(".")
                .toRealPath()
                .resolve(Paths.get(SAVE_DIRECTORY));
        if (!Files.exists(directory)) {
            Files.createDirectories(directory);
        }
        try (
                BufferedWriter writer = Files.newBufferedWriter(directory.resolve(fileName))
        ) {
            writer.write(generatedDiagram);
        }
    }
}