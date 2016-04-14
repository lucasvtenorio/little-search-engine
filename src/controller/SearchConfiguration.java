package controller;

import controller.analysis.CompleteAnalyzer;
import controller.analysis.DefaultAnalyzer;
import controller.analysis.StemmingAnalyzer;
import controller.analysis.StopWordAnalyzer;
import org.apache.lucene.analysis.Analyzer;

import java.io.File;

public class SearchConfiguration {
  private Analyzer analyzer;
  private File indexPath;
  private File reportPath;

  private SearchConfiguration(File indexPath, File reportPath, Analyzer analyzer) {
    this.analyzer = analyzer;
    this.reportPath = reportPath;
    this.indexPath = indexPath;
  }

  public Analyzer getAnalyzer() {
    return this.analyzer;
  }

  public File getIndexPath() {
    return indexPath;
  }

  public static SearchConfiguration getDefaultConfiguration() {
    return new SearchConfiguration(
        new File("./files/indexes/standard-index.index"),
        new File("./files/reports/standard.json"),
        new DefaultAnalyzer());
  }

  public static SearchConfiguration getCompleteConfiguration() {
    return new SearchConfiguration(
        new File("./files/indexes/complete-index.index"),
        new File("./files/reports/complete.json"),
        new CompleteAnalyzer());
  }

  public static SearchConfiguration getStemmingConfiguration() {
    return new SearchConfiguration(
        new File("./files/indexes/stemming-index.index"),
        new File("./files/reports/stemming.json"),
        new StemmingAnalyzer());
  }

  public static SearchConfiguration getStopWordConfiguration() {
    return new SearchConfiguration(
        new File("./files/indexes/stop-word-index.index"),
        new File("./files/reports/stop-word.json"),
        new StopWordAnalyzer());
  }
}
