package rsc;

public class App 
{
    public static void main( String[] args )
    {
        String text1 = "I'm feeling really bad and anxious with all the chaos I'm living recently";
        String text2 = "J'ai une excellent nouvelle pour vous mon ami. Je suis heureux et beaucoup positif.";

        System.out.println("\nText1: \nNumber of words: " + WordCounter.countWords(text1) + "\nLanguage: " + LanguageDetector.detectLanguage(text1) +
                "\nSentiment Analyzer: " + SentimentAnalyzer.analyzeSentiment(text1));

        System.out.println("\nText1: \nNumber of words: " + WordCounter.countWords(text2) + "\nLanguage: " + LanguageDetector.detectLanguage(text2) +
                "\nSentiment Analyzer: " + SentimentAnalyzer.analyzeSentiment(text2));
    }
}
