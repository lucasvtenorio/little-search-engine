package view;

import controller.SearchConfiguration;
import controller.SearchController;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;

import java.io.IOException;

public class Reporter {
  private SearchController searchEngine;
  public Reporter(SearchConfiguration configuration) throws IOException {
    this.searchEngine = new SearchController(configuration);
  }

  public void writeReport(String query) throws IOException, ParseException {
    java.util.List<Document> results = searchEngine
        .performSearch(query);
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("codigo\ttitulo\turl\n");
    for (Document result : results) {
      stringBuilder.append(result.get("id"));
      stringBuilder.append("\t");
      stringBuilder.append(result.get("title"));
      stringBuilder.append("\t");
      stringBuilder.append(result.get("url"));
      stringBuilder.append("\n");
    }
    System.out.print(stringBuilder.toString());
  }
}
