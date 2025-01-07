package midas.util;

import midas.model.PhraseEntity;
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


@Component
@PropertySource("classpath:phrases.properties")
public class DataSupplierUtil {

    @Value("${phrase.icon.root}")
    private String dataRoot;


    // Возвращает массив строк из файла.
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

    // Определяет пути к файлам с фразами.
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

    // Возвращает все PhraseEntity.
    public List<PhraseEntity> getAllPhrases() {

        List<PhraseEntity> phrases = new ArrayList<>();

        for (String root : getAllRoots("backend/midas/src/main/resources/static/data")) {
            Map.Entry<Path, List<String>> source = downloadFromFile(root);
            String heroName = source.getKey().getFileName().toString().replaceAll(".txt", "");
            List<String> rawPhrases = source.getValue();
            phrases.add(new PhraseEntity(upFirst(heroName), source.getKey().toString().replaceAll("\\\\", "/").replaceAll(".txt", ".png"), rawPhrases));
        }

        return phrases;
    }

    public String upFirst(String string){
        return String.valueOf(string.charAt(0)).toUpperCase() + string.substring(1);
    }



}
