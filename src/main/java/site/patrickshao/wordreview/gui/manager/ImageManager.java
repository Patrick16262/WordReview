package site.patrickshao.wordreview.gui.manager;

import javafx.scene.image.Image;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import site.patrickshao.wordreview.constants.Constants;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageManager {
    private static Map<String, Image> images = new HashMap<String, Image>();
    public static void loadWorkBookCover() throws IOException {
        Files.list(Path.of(Constants.Folders.wordbook_image))
                .peek(path -> {
                    final String file_name = path.getFileName().toString();
                    if (file_name.matches(".*\\.jpg$")) {
                        try (var in = Files.newInputStream(path)) {
                            images.put(file_name.replaceAll("\\.jpg$", ""),
                                    new Image(in));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }).toList();
    }
    public static void loadImages() throws IOException {
        Files.list(Path.of(Constants.Folders.image))
                .peek(path -> {
                    final String file_name = path.getFileName().toString();
                    if (file_name.matches(".*\\.png$")) {
                        try (var in = Files.newInputStream(path)) {
                            images.put(file_name.replaceAll("\\.png$", ""),
                                    new Image(in));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }).toList();
    }
    public static Image getImage(String name) {
        return images.get(name);
    }
}
