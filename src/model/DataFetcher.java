package model;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class DataFetcher {
  private final static int NUMBER_OF_DOCUMENTS = 200;

//  public ArrayList<WebDocument> readDocuments() throws IOException {
//    ArrayList<WebDocument> results = new ArrayList<>();
//    for (int i = 1; i <= NUMBER_OF_DOCUMENTS; i++) {
//      File file = new File("./files/documents/" + i+ ".json");
//      BufferedReader br = new BufferedReader(new FileReader(file));
//      Gson gson = new Gson();
//      results.add(gson.fromJson(br, WebDocument.class));
//    }
//    return results;
//  }
  public HashMap<String, WebDocument> readDocuments() throws IOException {
    HashMap<String, WebDocument> results = new HashMap<>();
    for (int i = 1; i <= NUMBER_OF_DOCUMENTS; i++) {
      File file = new File("./files/documents/" + i+ ".json");
      BufferedReader br = new BufferedReader(new FileReader(file));
      Gson gson = new Gson();
      results.put(Integer.toString(i), gson.fromJson(br, WebDocument.class));
    }
    return results;
  }
}
