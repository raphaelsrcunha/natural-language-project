package rsc;

import opennlp.tools.tokenize.SimpleTokenizer;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;

public class WordCounter {

    public static int countWords(String text) {
        SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;
        String[] tokens = tokenizer.tokenize(text);
        return tokens.length;
    }

    public static String cleanText(String text) {
        text = StringEscapeUtils.unescapeHtml4(text); // Parcourir HTML, supprimer la ponctuation, et normaliser les accents
        text = StringUtils.stripAccents(text);  // Supprimer les accents pour comparer correctement
        text = text.replaceAll("[^\\w\\s]", ""); // Supprimer la ponctuation
        return text;
    }
}
