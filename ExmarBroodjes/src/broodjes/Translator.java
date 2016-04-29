package broodjes;

import java.util.HashMap;
import java.util.Map;

public class Translator {
  
  private static final Map<String,String> translations = new HashMap<>();

  public static String translateWord(String word) {
    
    String translatedWord = translations.get(word.toLowerCase());
    if (translatedWord == null) {
      return word; 
    }
    translatedWord = fixCasing(word, translatedWord);
    
    return translatedWord;
  }
  
  public static String translateSentence(String sentence) {
    StringBuffer result = new StringBuffer();

    int startPos = 0;
    int endPos = 0;
    while (startPos < sentence.length() && endPos <= sentence.length()) {
      if (!Character.isLetter(sentence.charAt(startPos))) {
        result.append(sentence.charAt(endPos));
        startPos++;
        endPos++;
        continue;
      }
      if (endPos == sentence.length() || !Character.isLetter(sentence.charAt(endPos))) {
        String word = sentence.substring(startPos, endPos);
        result.append(translateWord(word));
        startPos = endPos;
      } else {
        endPos++;
      }
    }
    
    return result.toString();
  }
  
  private static String fixCasing(String original, String translated) {
    char firstChar = original.charAt(0);
    char lastChar = original.charAt(original.length()-1);
    boolean firstUp = Character.isUpperCase(firstChar);
    boolean lastUp = Character.isUpperCase(lastChar);
    
    if (firstUp && lastUp) {
      return translated.toUpperCase();
    } else if (!firstUp) { 
      return translated.toLowerCase();
    } else {
      return translated.substring(0, 1).toUpperCase() + translated.substring(1, translated.length()).toLowerCase();
    }
      
    
  }
  
  static {
    translations.put("zalm","salmon");
    translations.put("geitenkaas","goatcheese");
    translations.put("spek","bacon");
    translations.put("broodje","sandwich");
    translations.put("honing","honey");
    translations.put("klein","small");
    translations.put("groot","big");
    translations.put("terugbetaling","repayment");
    translations.put("met","with");
    translations.put("wit","white");
    translations.put("grof","brown");
    translations.put("tuinkers","cress");
    translations.put("jonge","young");
    translations.put("kaas","cheese");
    translations.put("smos","club");
    translations.put("en","and");
    translations.put("beenham","ham");
    translations.put("gerookte","smoked");
    translations.put("aardappelsla","potatosalad");
    translations.put("kip","chicken");
    translations.put("natuur", "plain");
    translations.put("tonijnsalade", "tunasalad");
    translations.put("komkommersla", "cucumbersalad");
    translations.put("gezond", "healthy");
    translations.put("vleessalade", "meatsalad");
    translations.put("kipsalade", "chickensalad");
    translations.put("ei", "egg");
    translations.put("salade","salad");
  }
  
  
}
