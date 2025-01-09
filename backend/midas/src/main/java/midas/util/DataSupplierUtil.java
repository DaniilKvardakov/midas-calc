package midas.util;

import midas.annotations.Commented;
import midas.models.PhraseEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
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

/**
 * Компонент выдачи путей к картинкам и фразам героев.
 */
@Commented
@Component
@PropertySource("classpath:secret/phrases.properties")
public class DataSupplierUtil {

    /**
     * Путь к иконками героев и их фразам о мидасе.
     */
    @Value("${phrase.icon.root}")
    private String dataRoot;

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
    public static List<String> getAllRoots(String mainPackage) {
        List<Path> txtFiles = new ArrayList<>();

        try {
            Files.walkFileTree(Paths.get(mainPackage), new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (file.toString().endsWith(".txt")) {
                        txtFiles.add(file);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });


        } catch (IOException e) {
            System.out.println("=(((");
        }

        return txtFiles
                .stream()
                .map(Path::toString)
                .toList();
    }


    /**
     * Метод возвращающий все PhraseEntity.
     * @return сущность фраз.
     * @see PhraseEntity
     */
    public List<PhraseEntity> getAllPhrases() {

        List<PhraseEntity> phrases = new ArrayList<>();

        for (String root : getAllRoots(dataRoot)) {
            Map.Entry<Path, List<String>> source = downloadFromFile(root);
            String heroName = source.getKey().getFileName().toString().replaceAll(".txt", "");
            List<String> rawPhrases = source.getValue();
            phrases.add(new PhraseEntity(upFirst(heroName), source
                    .getKey()
                    .subpath(source.getKey().getNameCount() - 3, source.getKey().getNameCount())
                    .toString()
                    .replaceAll("\\\\", "/")
                    .replaceAll(".txt", ".webp"), rawPhrases));
        }

        return phrases;
    }

    /**
     * Метод возвращающий строку с большой буквы.
     * @param string исходная строка.
     * @return отформатированная строка.
     */
    public String upFirst(String string){
        return String.valueOf(string.charAt(0)).toUpperCase() + string.substring(1);
    }

}
