package regression;

import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.UnaryOperator;

/** Recording is deliberately opt-in and never overwrites an existing oracle. */
public final class Baseline {
    private Baseline() {}

    public static List<String> originalTemplates() throws IOException {
        String directory = System.getProperty("dry.baseline");
        Assertions.assertNotNull(directory, "Supply -Ddry.baseline=<external baseline directory>");
        return Files.readAllLines(Path.of(directory).resolve("template-inventory.txt"), StandardCharsets.UTF_8);
    }

    public static void verify(String name, String actual) throws IOException {
        verify(name, actual, UnaryOperator.identity());
    }

    public static void verify(String name, String actual, UnaryOperator<String> approvedChanges) throws IOException {
        String directory = System.getProperty("dry.baseline");
        Assertions.assertNotNull(directory, "Supply -Ddry.baseline=<external baseline directory>");
        Path root = Path.of(directory).toAbsolutePath().normalize();
        Assertions.assertFalse(root.startsWith(Path.of("").toAbsolutePath().normalize()),
                "Baseline artifacts must live outside the checkout");
        Assertions.assertFalse(Boolean.getBoolean("dry.record") && Files.exists(root.resolve("SEALED")),
                "This baseline is sealed. Run comparisons without -Ddry.record=true.");
        Path file = root.resolve(name + ".txt");
        if (Boolean.getBoolean("dry.record") && !Files.exists(file)) {
            Files.createDirectories(file.getParent());
            Files.writeString(file, actual, StandardCharsets.UTF_8);
        }
        Assertions.assertTrue(Files.exists(file), "Missing baseline: " + file);
        Assertions.assertEquals(approvedChanges.apply(Files.readString(file, StandardCharsets.UTF_8)), actual, name);
    }
}
