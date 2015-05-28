/**
 *
 */
package de.schaeferban.lectures;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.JTableHeader;

import edu.kit.informatik.atis.streaming.calendar.ws.StreamingCalendarWs_Service;

public class Main {
  private static JProgressBar progress = new JProgressBar();
  public static void main(String[] args) {

    RecordingsTableModel rtm = new RecordingsTableModel();
    JTable table = new JTable(rtm);
    table.setTableHeader(new JTableHeader(table.getColumnModel()));

    JScrollPane recordingsPane = new JScrollPane(table);
    recordingsPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 1), "Latest recordings"));

    RecordingPanel recPanel = new RecordingPanel();

    table.getSelectionModel().addListSelectionListener(new RecordingsSelectionListener(rtm, recPanel, table.getSelectionModel()));
    table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    JFrame frame = new JFrame("Lecture recordings");
    frame.setLayout(new BorderLayout());
    frame.add(BorderLayout.NORTH, progress);
    frame.add(BorderLayout.CENTER, recordingsPane);
    frame.add(BorderLayout.SOUTH, recPanel);
    frame.setSize(700, 500);
    frame.setLocation(100, 100);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    StreamingCalendarWs_Service service = new StreamingCalendarWs_Service();

    Thread refreshRecordingsThread = new Thread(new GetLatestRecordingsRunnable(rtm, service, 50, progress));
    refreshRecordingsThread.start();

  }

}
