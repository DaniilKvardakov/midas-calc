package midas.util;

import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@UtilityClass
public final class FileUtil {

    /**
     * Метод возвращающий массив строк(фраз) из файла.
     * @param filePath путь к файлу.
     * @return пара путь/массив строк(фраз)
     */
    public static Map.Entry<Path, List<String>> downloadFromFile(String filePath) {
        List<String> lines;
        Path path = Paths.get(filePath);
        try {
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new AbstractMap.SimpleEntry<>(path, lines);
    }


    /**
     * Метод определяющий пути к файлам с фразами.
     * @param mainPackage путь к корневой директории.
     * @return массив строк(фраз).
     */
    public static List<String> getAllRoots(String mainPackage, String extensionType) {
        List<Path> txtFiles = new ArrayList<>();

        try {
            Files.walkFileTree(Paths.get(mainPackage), new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    if (file.toString().endsWith(extensionType)) {
                        txtFiles.add(file);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });


        } catch (IOException e) {
            System.out.println("Неправильный путь к корневому каталогу!");
        }

        return txtFiles
                .stream()
                .map(Path::toString)
                .toList();
    }

}
