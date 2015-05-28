package de.schaeferban.lectures;

import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

class RecordingsSelectionListener implements ListSelectionListener {
  private RecordingsTableModel rtm;
  private ListSelectionModel lsm;
  private RecordingPanel recPanel;
  public RecordingsSelectionListener(RecordingsTableModel rtm, RecordingPanel recPanel, ListSelectionModel lsm) {
    this.rtm = rtm;
    this.recPanel = recPanel;
    this.lsm = lsm;
  }
  @Override
  public void valueChanged(ListSelectionEvent e) {
    recPanel.setRecording(rtm.getRecording(lsm.getMinSelectionIndex()));
  }

}
