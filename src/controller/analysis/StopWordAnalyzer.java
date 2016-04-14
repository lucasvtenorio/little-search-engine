package controller.analysis;


import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;

/**
 * This Analyzer uses the DefaultAnalyzer and adds a StopWords filter.
 */
public class StopWordAnalyzer extends Analyzer{
  @Override
  protected TokenStreamComponents createComponents(String fieldName) {
    Tokenizer tokenizer = new StandardTokenizer();
    TokenStream filter = new StandardFilter(tokenizer);
    filter = new LowerCaseFilter(filter);
    filter = new StopFilter(filter, StopAnalyzer.ENGLISH_STOP_WORDS_SET);
    return new TokenStreamComponents(tokenizer, filter);
  }
}
