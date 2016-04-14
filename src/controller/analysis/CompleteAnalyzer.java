package controller.analysis;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;

/**
 * This Analyzer uses the DefaultAnalyzer and adds a StopWords filter and a Stemming filter.
 */
public class CompleteAnalyzer extends Analyzer{
  @Override
  protected TokenStreamComponents createComponents(String s) {
    Tokenizer tokenizer = new StandardTokenizer();
    TokenStream filter = new StandardFilter(tokenizer);
    filter = new LowerCaseFilter(filter);
    filter = new StopFilter(filter, StopAnalyzer.ENGLISH_STOP_WORDS_SET);
    filter = new PorterStemFilter(filter);
    return new TokenStreamComponents(tokenizer, filter);
  }
}
