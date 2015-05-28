package de.schaeferban.lectures;

import javax.swing.JProgressBar;

import edu.kit.informatik.atis.streaming.calendar.ws.StreamingCalendarWsException_Exception;
import edu.kit.informatik.atis.streaming.calendar.ws.StreamingCalendarWs_Service;

public class GetLatestRecordingsRunnable implements Runnable {
  private RecordingsTableModel rtm;
  private StreamingCalendarWs_Service service;
  private int numRecordings = 20;
  private JProgressBar progress;
  public GetLatestRecordingsRunnable(RecordingsTableModel rtm, StreamingCalendarWs_Service service, int numRecordings, JProgressBar progress) {
    this.rtm = rtm;
    this.service = service;
    this.numRecordings = numRecordings;
    this.progress = progress;
  }
  @Override
  public void run() {
    if (progress != null) {
      progress.setIndeterminate(true);
    }
    try {
      rtm.addRecordings(service.getStreamingCalendarWsPort().getLastRecordings(numRecordings));
    } catch (StreamingCalendarWsException_Exception e) {
      e.printStackTrace();
    }
    if (progress != null) {
      progress.setIndeterminate(false);
    }
  }
}
