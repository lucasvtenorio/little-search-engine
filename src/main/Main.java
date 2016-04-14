package main;

import controller.Indexer;
import controller.SearchConfiguration;
import org.apache.lucene.queryparser.classic.ParseException;
import view.Reporter;
import view.SearchFrame;

import javax.swing.*;
import java.io.IOException;

public class Main {
  public static void main(String[] args) throws IOException, ParseException {
    SearchConfiguration defaultConfiguration = SearchConfiguration.getDefaultConfiguration();
    SearchConfiguration stopWordConfiguration = SearchConfiguration.getStopWordConfiguration();
    SearchConfiguration stemmingConfiguration = SearchConfiguration.getStemmingConfiguration();
    SearchConfiguration completeConfiguration = SearchConfiguration.getCompleteConfiguration();

    Indexer defaultIndexer = new Indexer(defaultConfiguration);
    Indexer stopIndexer = new Indexer(stopWordConfiguration);
    Indexer stemmingIndexer = new Indexer(stemmingConfiguration);
    Indexer completeIndexer = new Indexer(completeConfiguration);

    log("Indexing Default Analyzer...");
    defaultIndexer.rebuildIndexes();
    log("Indexing StopWord Analyzer...");
    stopIndexer.rebuildIndexes();
    log("Indexing Stemming Analyzer...");
    stemmingIndexer.rebuildIndexes();
    log("Indexing the StopWord+Stemming Analyzer...");
    completeIndexer.rebuildIndexes();
    log("Launching UI...");

    SwingUtilities.invokeLater(() -> {
      new SearchFrame("Default", defaultConfiguration).setVisible(true);
      new SearchFrame("StopWord", stopWordConfiguration).setVisible(true);
      new SearchFrame("Stemming", stemmingConfiguration).setVisible(true);
      new SearchFrame("StopWord+Stemming", completeConfiguration).setVisible(true);
    });

    Reporter reporter = new Reporter(defaultConfiguration);
    reporter.writeReport("the company reports");

//    SearchConfiguration configuration = SearchConfiguration.getDefaultConfiguration();
//
//    Indexer indexer = new Indexer(configuration);
//    System.out.println("Indexing...");
//    indexer.rebuildIndexes();
//    System.out.println("Indexed!");
//    SearchController searchEngine = new SearchController(configuration);
//    List<Document> results = searchEngine.performSearch("is an android phone better than an iPhone");
//    System.out.println("Printing results:" + results.size());
//    for (Document result : results) {
//      System.out.println(result.get("title") + " url:\t" + result.get("url"));
//    }
  }

  static void log(String log) {
    System.out.println(log);
  }
}
