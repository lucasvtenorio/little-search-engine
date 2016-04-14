package controller.analysis;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;

/**
 * This Analyzer uses the DefaultAnalyzer and adds a Stemming filter.
 */
public class StemmingAnalyzer extends Analyzer{

  @Override
  protected TokenStreamComponents createComponents(String fieldName) {
    Tokenizer tokenizer = new StandardTokenizer();
    TokenStream filter = new StandardFilter(tokenizer);
    filter = new LowerCaseFilter(filter);
    filter = new PorterStemFilter(filter);
    return new TokenStreamComponents(tokenizer, filter);
  }
}
