package rsc;

import opennlp.tools.tokenize.SimpleTokenizer;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;

import java.util.List;

public class SentimentAnalyzer {

    private static final List<String> positiveWords = List.of(
            // Français
            "agréable", "aimable", "adorable", "affectueux", "amical", "amour", "apprécié", "beau", "bien", "bonté",
            "brillant", "calme", "charmant", "chéri", "clair", "confiant", "confortable", "courageux", "créatif", "doux",
            "efficace", "élégant", "enthousiaste", "équilibré", "excellent", "exemplaire", "généreux", "générosité", "gentil",
            "glorieux", "heureux", "inspirant", "intelligent", "joyeux", "légendaire", "magnifique", "merveilleux", "motivé", "noble",
            "optimiste", "parfait", "patient", "précieux", "productif", "rayonnant", "reconnaissant", "réussi", "respecté",
            "satisfait", "serein", "sincère", "sophistiqué", "spécial", "splendide", "stable", "succes", "talentueux",
            "tempéré", "tranquille", "unique", "valable", "vivant", "volontaire", "vraiment", "réaliste", "réconfortant", "sympathique",
            "éblouissant", "magnifique", "consciencieux", "enthousiasmé", "complet", "accompli", "mignon", "soutien", "lumineux",
            "appréciatif", "encourageant", "passionné", "ravi", "libéré", "inspiré", "vivace", "prospère", "prospérité", "indomptable",
            "épanouissant", "admiré", "rayonnant", "toujours", "superbe", "étonnant", "idéal", "meilleur", "réussi", "solide",
            "fertile", "lucratif", "produtif", "éducatif", "suprême", "encourageant",

            // English
            "pleasant", "friendly", "adorable", "affectionate", "amiable", "love", "appreciated", "beautiful", "good", "kindness",
            "brilliant", "calm", "charming", "dear", "clear", "confident", "comfortable", "courageous", "creative", "sweet",
            "efficient", "elegant", "enthusiastic", "balanced", "excellent", "exemplary", "generous", "generosity", "kind",
            "glorious", "happy", "inspiring", "intelligent", "joyful", "legendary", "magnificent", "wonderful", "motivated", "noble",
            "optimistic", "perfect", "patient", "precious", "productive", "radiant", "grateful", "successful", "respected",
            "satisfied", "serene", "sincere", "sophisticated", "special", "splendid", "stable", "successful", "talented",
            "calm", "unique", "worthy", "alive", "willing", "truly", "realistic", "comforting", "sympathetic",
            "dazzling", "magnificent", "conscientious", "enthusiastic", "complete", "accomplished", "cute", "supportive", "bright",
            "appreciative", "encouraging", "passionate", "delighted", "liberated", "inspired", "vibrant", "prosperous", "prosperity", "unstoppable",
            "fulfilling", "admired", "radiant", "always", "superb", "amazing", "ideal", "best", "successful", "strong",
            "fertile", "lucrative", "productive", "educational", "supreme", "encouraging"
    );


    private static final List<String> negativeWords = List.of(
            // Français
            "abominable", "affreux", "agité", "anxieux", "atroce", "bizarre", "blessé", "bouleversé", "chaos", "colère",
            "confus", "contrarié", "dérangé", "détesté", "désastre", "désespéré", "difficile", "difficilement", "déprimant", "désagréable",
            "détestable", "embarrassant", "épuisant", "fâché", "fatigué", "horrible", "hostile", "incapable", "inquiétant", "insatisfait",
            "insensé", "insupportable", "injuste", "intolérable", "mauvais", "mauvais", "mélancolique", "négatif", "nuisible", "odieux",
            "offensant", "orageux", "pessimiste", "pleurer", "perturbé", "prisonnier", "quitter", "révolté", "ridicule", "triste",
            "terrible", "terrifiant", "torturé", "épouvantable", "désespéré", "triste", "fatale", "frustré", "violent", "vulnérable",
            "vexé", "affligé", "agressif", "défaitiste", "battre", "cauchemar", "colérique", "détruit", "douloureux", "délirant",
            "incertain", "indigne", "insultant", "insuffisant", "irritable", "irréparable", "mal", "mauvais", "mécontent", "morne",
            "négativité", "nuisible", "odieux", "peine", "perdre", "pessimisme", "plaindre", "détresse", "répugnant", "rancune",
            "sombre", "tromper", "tumultueux", "violent", "vicié", "vomir", "vulgaire", "vulnérable", "désagréable", "atroce", "désavantage",
            "exaspéré", "faux", "injustifiable", "malheureux", "mauvais", "scandaleux", "pathétique", "dérouté", "rancunier", "révoltant",
            "rejeté", "seul", "traumatisant", "vulnérable", "incapacité", "dépression", "fuite", "épuisement", "frustration",

            // English
            "abominable", "awful", "agitated", "anxious", "atrocious", "bizarre", "hurt", "disturbed", "chaos", "anger",
            "confused", "upset", "disturbed", "hated", "disaster", "desperate", "difficult", "difficultly", "depressing", "unpleasant",
            "detestable", "embarrassing", "exhausting", "angry", "tired", "horrible", "hostile", "incapable", "concerning", "unsatisfied",
            "insane", "intolerable", "unfair", "unbearable", "bad", "bad", "melancholic", "negative", "harmful", "hateful",
            "offensive", "stormy", "pessimistic", "crying", "disturbed", "prisoner", "leave", "revolted", "ridiculous", "sad",
            "terrible", "terrifying", "tortured", "horrendous", "desperate", "sad", "fatal", "frustrated", "violent", "vulnerable",
            "offended", "afflicted", "aggressive", "defeatist", "beat", "nightmare", "angry", "destroyed", "painful", "delirious",
            "uncertain", "unworthy", "insulting", "insufficient", "irritable", "irreparable", "bad", "bad", "discontent", "gloomy",
            "negativity", "harmful", "hateful", "pain", "lose", "pessimism", "complain", "distress", "repugnant", "grudge",
            "dark", "deceive", "tumultuous", "violent", "corrupted", "vomit", "vulgar", "vulnerable", "unpleasant", "atrocious", "disadvantage",
            "exasperated", "false", "unjustifiable", "unhappy", "bad", "scandalous", "pathetic", "confused", "vindictive", "outraged",
            "rejected", "alone", "traumatizing", "vulnerable", "inability", "depression", "escape", "exhaustion", "frustration"
    );


    public static String analyzeSentiment(String text) {

        text = cleanText(text);

        SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;
        String[] tokens = tokenizer.tokenize(text);

        int positiveCount = 0;
        int negativeCount = 0;

        for(String token : tokens) {
            if(positiveWords.contains(token.toLowerCase())) {
                positiveCount++;
            } else if (negativeWords.contains(token.toLowerCase())) {
                negativeCount++;
            }
        }

        if(positiveCount > negativeCount) {
            return "Positif";
        } else if(negativeCount > positiveCount) {
            return "Négatif";
        } else {
            return "Neutre";
        }

    }

    public static String cleanText(String text) {
        text = StringEscapeUtils.unescapeHtml4(text); // Parcourir HTML, supprimer la ponctuation, et normaliser les accents
        text = StringUtils.stripAccents(text);  // Supprimer les accents pour comparer correctement
        text = text.replaceAll("[^\\w\\s]", ""); // Supprimer la ponctuation
        return text;
    }


}
