package de.schaeferban.lectures;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import de.schaeferban.lectures.util.FormatUtil;
import edu.kit.informatik.atis.streaming.calendar.ws.Recording;

public class RecordingsTableModel implements TableModel {
  private static final String LECTURERS = "lecturer(s)";
  private static final String LECTURE_ROOM = "lecture room";
  private static final String LECTURE_TITLE = "lecture title";
  private static final String RECORDING_DATE = "recording date";

  private List<TableModelListener> listeners = new ArrayList<>();
  private List<Recording> recordings = new ArrayList<>();
  private String[] columns = { RECORDING_DATE, LECTURE_TITLE, LECTURE_ROOM, LECTURERS};

  @Override
  public int getRowCount() {
    return recordings.size();
  }

  @Override
  public int getColumnCount() {
    return columns.length;
  }

  @Override
  public String getColumnName(int columnIndex) {
    return columns[columnIndex];
  }

  @Override
  public Class<?> getColumnClass(int columnIndex) {
    return String.class;
  }

  @Override
  public boolean isCellEditable(int rowIndex, int columnIndex) {
    return false;
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    Recording r = recordings.get(rowIndex);
    switch (columns[columnIndex]) {
      case LECTURE_ROOM: return r.getLectureRoomName();
      case LECTURE_TITLE: return r.getLectureTitle();
      case LECTURERS: return r.getLecturers();
      case RECORDING_DATE: return FormatUtil.formatDateTime(r.getSessionBegin());
    }
    return "";
  }

  @Override
  public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
  }

  @Override
  public void addTableModelListener(TableModelListener l) {
    listeners.add(l);
  }

  @Override
  public void removeTableModelListener(TableModelListener l) {
    listeners.remove(l);
  }

  public Recording getRecording(int rowIndex) {
    return recordings.get(rowIndex);
  }

  public void addRecordings(List<Recording> r) {
    recordings.addAll(r);
    for (TableModelListener l : listeners) {
        l.tableChanged(new TableModelEvent(this));
    }
  }

  public void clearRecordings() {
    recordings.clear();
  }
}
