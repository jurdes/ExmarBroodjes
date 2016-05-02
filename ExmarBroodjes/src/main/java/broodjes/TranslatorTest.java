package broodjes;

import static org.junit.Assert.*;

import org.junit.Test;

public class TranslatorTest {

  @Test
  public void testTranslateWord(){
    assertEquals("salmon",Translator.translateWord("zalm"));
    assertEquals("SALMON",Translator.translateWord("ZALM"));
    assertEquals("Salmon",Translator.translateWord("Zalm"));
  }
  
  @Test
  public void testTranslateSentence() {
	  assertEquals("salmon Salmon SALMON3$",Translator.translateSentence("zalm Zalm ZALM3$"));
	  assertEquals("Gerookte salmon 'fresh' groot, wit", Translator.translateSentence("Gerookte zalm 'fresh' groot, wit"));
  }
  
}
