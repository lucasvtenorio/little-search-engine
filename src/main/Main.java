package main;

import controller.Indexer;
import controller.SearchConfiguration;
import controller.SearchController;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import view.SearchFrame;

import javax.swing.*;
import java.io.IOException;
import java.util.List;

public class Main {
  public static void main(String[] args) throws IOException, ParseException {
    SearchConfiguration configuration = SearchConfiguration.getDefaultConfiguration();

    Indexer indexer = new Indexer(configuration);
    System.out.println("Indexing...");
    indexer.rebuildIndexes();
    System.out.println("Indexed!");
    SearchController searchEngine = new SearchController(configuration);
    List<Document> results = searchEngine.performSearch("is an android phone better than an iPhone");
    System.out.println("Printing results:" + results.size());
    for (Document result : results) {
      System.out.println(result.get("title") + " url:\t" + result.get("url"));
    }
    SwingUtilities.invokeLater(() -> new SearchFrame("RI", configuration).setVisible(true));
  }
}
