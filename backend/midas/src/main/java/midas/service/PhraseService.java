package midas.service;

import midas.annotations.Commented;
import midas.models.PhraseEntity;
import midas.util.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Компонент выдачи путей к картинкам и фразам героев.
 */
@Commented
@Service
@PropertySource("classpath:secret/phrases.properties")
public class PhraseService {

    /**
     * Путь к иконкам героев и их фразам о мидасе.
     */
    @Value("${phrase.icon.root}")
    private String dataRoot;

    /**
     * Метод возвращающий все PhraseEntity.
     * @return сущность фраз.
     * @see PhraseEntity
     */
    public List<PhraseEntity> getAllPhrases() {

        List<PhraseEntity> phrases = new ArrayList<>();
        String textType = ".txt";
        String webpType = ".webp";

        for (String root : FileUtil.getAllRoots(dataRoot, textType)) {
            Map.Entry<Path, List<String>> source = FileUtil.downloadFromFile(root);
            String heroName = source.getKey().getFileName().toString().replaceAll(textType, "");
            List<String> rawPhrases = source.getValue();
            phrases.add(new PhraseEntity(upFirst(heroName), source
                    .getKey()
                    .subpath(source.getKey().getNameCount() - 3, source.getKey().getNameCount())
                    .toString()
                    .replaceAll("\\\\", "/")
                    .replaceAll(textType, webpType), rawPhrases));
        }

        return phrases;
    }

    /**
     * Метод возвращающий строку с большой буквы.
     * @param string исходная строка.
     * @return отформатированная строка.
     */
    private static String upFirst(String string){
        return String.valueOf(string.charAt(0)).toUpperCase() + string.substring(1);
    }

}
