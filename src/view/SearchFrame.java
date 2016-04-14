package view;

import controller.SearchConfiguration;
import controller.SearchController;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;

import javax.swing.*;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.HyperlinkEvent;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;


public class SearchFrame extends JFrame implements DocumentListener {
  private final SearchConfiguration searchConfiguration;
  private JTextField entry;
  private JLabel status;
  private JTextPane textArea;

  public SearchFrame(String title, SearchConfiguration searchConfiguration) {
    this.searchConfiguration = searchConfiguration;
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setTitle(title);
    initComponents();
    entry.getDocument().addDocumentListener(this);
  }

  private void initComponents() {
    entry = new JTextField();
    textArea = new JTextPane();
    status = new JLabel();
    JLabel jLabel1 = new JLabel();

//    textArea.setColumns(20);
//    textArea.setLineWrap(false);
//    textArea.setRows(20);
//    textArea.setWrapStyleWord(true);
    textArea.setEditable(false);
    textArea.setContentType("text/html");
    textArea.addHyperlinkListener(
        (HyperlinkEvent e) -> {
          if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
            if(Desktop.isDesktopSupported()) {
              try {
                Desktop.getDesktop().browse(e.getURL().toURI());
              }
              catch (IOException | URISyntaxException e1) {
                e1.printStackTrace();
              }
            }
          }
        });

    JScrollPane jScrollPane1 = new JScrollPane(textArea);

    jLabel1.setText("Enter text to search:");

    GroupLayout layout = new GroupLayout(getContentPane());
    getContentPane().setLayout(layout);

    //Create a parallel group for the horizontal axis
    ParallelGroup hGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);

    //Create a sequential and a parallel groups
    SequentialGroup h1 = layout.createSequentialGroup();
    ParallelGroup h2 = layout.createParallelGroup(GroupLayout.Alignment.TRAILING);

    //Add a container gap to the sequential group h1
    h1.addContainerGap();

    //Add a scroll pane and a label to the parallel group h2
    h2.addComponent(jScrollPane1, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE);
    h2.addComponent(status, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE);

    //Create a sequential group h3
    SequentialGroup h3 = layout.createSequentialGroup();
    h3.addComponent(jLabel1);
    h3.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED);
    h3.addComponent(entry, GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE);

    //Add the group h3 to the group h2
    h2.addGroup(h3);
    //Add the group h2 to the group h1
    h1.addGroup(h2);

    h1.addContainerGap();

    //Add the group h1 to the hGroup
    hGroup.addGroup(GroupLayout.Alignment.TRAILING, h1);
    //Create the horizontal group
    layout.setHorizontalGroup(hGroup);


    //Create a parallel group for the vertical axis
    ParallelGroup vGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
    //Create a sequential group v1
    SequentialGroup v1 = layout.createSequentialGroup();
    //Add a container gap to the sequential group v1
    v1.addContainerGap();
    //Create a parallel group v2
    ParallelGroup v2 = layout.createParallelGroup(GroupLayout.Alignment.BASELINE);
    v2.addComponent(jLabel1);
    v2.addComponent(entry, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE);
    //Add the group v2 tp the group v1
    v1.addGroup(v2);
    v1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
    v1.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE);
    v1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
    v1.addComponent(status);
    v1.addContainerGap();

    vGroup.addGroup(v1);
    layout.setVerticalGroup(vGroup);
    pack();
  }

  @Override
  public void insertUpdate(DocumentEvent e) {
    search();
  }

  @Override
  public void removeUpdate(DocumentEvent e) {
    search();
  }

  @Override
  public void changedUpdate(DocumentEvent e) {}

  private void search() {
    // If the query is empty, clear the status message and the results.
    if (getQuery().equals("")){
      setResults("");
      message("");
      return;
    }

    try {
      SearchController searchEngine = new SearchController(searchConfiguration);
      java.util.List<Document> results = searchEngine
          .performSearch(getQuery());
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("<html><body>");
      for (Document result : results) {
        stringBuilder.append("<div style=\"padding: 0px 8px 8px 8px;color: rgb(26, 13, 171); font-family: arial, sans-serif;text-decoration: none;\">");
        stringBuilder.append("<a href=\"");
        stringBuilder.append(result.get("url"));
        stringBuilder.append("\" style=\"text-decoration: none;\">");
        stringBuilder.append(result.get("title"));
        stringBuilder.append("</a>");
        stringBuilder.append("</div>");
      }
      stringBuilder.append("</body>/</html>");
      setResults(stringBuilder.toString());
      message("The query '" + getQuery() +  "' returned " + results.size() + " results! :)");
    } catch (IOException io) {
      message("Internal problem with the documents/indexes :(");
    } catch (ParseException parse) {
      message("Problem parsing your query :(");
    }
  }
  private String getQuery() {
    return entry.getText();
  }
  private void setResults(String results) {
    textArea.setText(results);
  }
  private void message(String msg) {
    status.setText(msg);
  }
}
