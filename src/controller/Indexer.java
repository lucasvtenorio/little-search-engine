package controller;


import model.DataFetcher;
import model.WebDocument;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

public class Indexer {
  private IndexWriter indexWriter;
  private final SearchConfiguration configuration;

  public Indexer(SearchConfiguration configuration) {
    this.configuration = configuration;
  }

  private IndexWriter getIndexWriter() throws IOException {
    if (indexWriter == null) {

      File indexFile = configuration.getIndexPath();
      if (indexFile.exists()) {
        deleteFile(indexFile);
      }
      Directory indexDir = FSDirectory.open(indexFile.toPath());
      IndexWriterConfig config = new IndexWriterConfig(configuration.getAnalyzer());
      indexWriter = new IndexWriter(indexDir, config);
    }
    return indexWriter;
  }

  public final void closeIndexWriter() throws IOException {
    if (this.indexWriter != null) {
      indexWriter.close();
    }
    indexWriter = null;
  }

  private void indexWebDocument(String id, WebDocument webDocument) throws IOException{
    IndexWriter writer = getIndexWriter();
    Document doc = new Document();
    doc.add(new StringField("id", id, Field.Store.YES));
    doc.add(new StringField("title", webDocument.title, Field.Store.YES));
    doc.add(new StringField("url", webDocument.url, Field.Store.YES));
    doc.add(new StringField("author", webDocument.author, Field.Store.YES));
    String fullSearchableText = webDocument.title + " " + webDocument.content;
    doc.add(new TextField("content", fullSearchableText, Field.Store.NO));
    writer.addDocument(doc);
  }

  public final void rebuildIndexes() throws IOException {
    DataFetcher dataFetcher = new DataFetcher();
    HashMap<String, WebDocument> documents = dataFetcher.readDocuments();

    //List<WebDocument> documents = dataFetcher.readDocuments();
    for (Entry<String, WebDocument> document: documents.entrySet()) {
      indexWebDocument(document.getKey(), document.getValue());
    }
    closeIndexWriter();
  }

  private void deleteFile(File f) throws IOException {
    if (f.isDirectory()) {
      for (File c : f.listFiles())
        deleteFile(c);
    }
    if (!f.delete())
      throw new FileNotFoundException("Failed to delete file: " + f);
  }
}
