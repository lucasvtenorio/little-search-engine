package controller.analysis;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;

/**
 * This Analyzer is pretty much the StandardAnalyzer that comes with Lucene
 * but without the automatic StopWords filter.
 */
public class DefaultAnalyzer extends Analyzer {
  @Override
  protected TokenStreamComponents createComponents(String fieldName) {
    Tokenizer tokenizer = new StandardTokenizer();
    TokenStream filter = new StandardFilter(tokenizer);
    filter = new LowerCaseFilter(filter);
    return new TokenStreamComponents(tokenizer, filter);
  }
}
