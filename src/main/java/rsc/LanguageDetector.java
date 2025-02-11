package rsc;

import opennlp.tools.tokenize.SimpleTokenizer;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;

import java.util.List;

public class LanguageDetector {

    private static final List<String> englishKeywords = List.of("the", "and", "is", "it", "in", "of", "to", "you", "a", "that");
    private static final List<String> frenchKeywords = List.of("le", "et", "est", "il", "de", "à", "vous", "un", "ce", "pour");

    public static String detectLanguage(String text) {
        text = cleanText(text);

        SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;
        String[] tokens = tokenizer.tokenize(text);

        int frenchCount = 0;
        int englishCount = 0;

        for(String token : tokens) {
            if(englishKeywords.contains(token.toLowerCase())) {
                englishCount++;
            } else if(frenchKeywords.contains(token.toLowerCase())) {
                frenchCount++;
            }
        }

        if(englishCount > frenchCount) {
            return "English";
        } else if (frenchCount > englishCount) {
            return "Français";
        } else {
            return "Indeterminé";
        }
    }

    public static String cleanText(String text) {
        text = StringEscapeUtils.unescapeHtml4(text); // Parcourir HTML, supprimer la ponctuation, et normaliser les accents
        text = StringUtils.stripAccents(text);  // Supprimer les accents pour comparer correctement
        text = text.replaceAll("[^\\w\\s]", ""); // Supprimer la ponctuation
        return text;
    }
}
