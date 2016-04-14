package controller;

/*
public class SearchEngine {
    private IndexSearcher searcher = null;
    private QueryParser parser = null;
public SearchEngine() throws IOException {
    searcher = new IndexSearcher(DirectoryReader.open(FSDirectory.open(new File("index-directory"))));
    parser = new QueryParser("content", new StandardAnalyzer());
    }

public TopDocs performSearch(String queryString, int n)
    throws IOException, ParseException {
    Query query = parser.parse(queryString);
    return searcher.search(query, n);
    }

public Document getDocument(int docId)
    throws IOException {
    return searcher.doc(docId);
    }
    }
 */

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchController {
  private IndexSearcher searcher;
  private QueryParser parser;
  private final SearchConfiguration configuration;

  public SearchController(SearchConfiguration configuration) throws IOException {
    this.configuration = configuration;
    searcher = new IndexSearcher(DirectoryReader.open(
        FSDirectory.open(configuration.getIndexPath().toPath())));
    parser = new QueryParser("content", configuration.getAnalyzer());
  }

  public List<Document> performSearch(String queryString) throws ParseException, IOException {
    Query query = parser.parse(queryString);
    TopDocs topDocs = searcher.search(query, 300);
    ArrayList<Document> results = new ArrayList<>();
    for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
      results.add(searcher.doc(scoreDoc.doc));
    }
    return results;
  }
}
