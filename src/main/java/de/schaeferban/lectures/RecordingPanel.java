package de.schaeferban.lectures;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.schaeferban.lectures.util.FormatUtil;
import edu.kit.informatik.atis.streaming.calendar.ws.Lecturer;
import edu.kit.informatik.atis.streaming.calendar.ws.Recording;

public class RecordingPanel extends JPanel {

  private static final long serialVersionUID = -7424957062167773548L;

  private JLabel title = new JLabel("bla");

  private final JLabel topicLabel = new JLabel("Session topic");
  private JLabel topicField = new JLabel();

  private final JLabel durationLabel = new JLabel("Duration");
  private JLabel durationField = new JLabel();

  private final JLabel lecturerLabel = new JLabel("Lecturer");
  private JLabel lecturerField = new JLabel();

  private final JLabel roomLabel = new JLabel("Room");
  private JLabel roomField = new JLabel();

  private final JLabel startTimeLabel = new JLabel("Start time");
  private JLabel startTimeField = new JLabel();

  private final JLabel contentCommentLabel = new JLabel("Content comment");
  private JLabel contentCommentField = new JLabel();

  private final JLabel techCommentLabel = new JLabel("Technical comment");
  private JLabel techCommentField = new JLabel();

  private final JLabel urlLabel = new JLabel("URL");
  private JTextField urlField = new JTextField();

  private JButton copyClpbrd = new JButton("Copy URL to clipboard");

  public RecordingPanel() {
    setLayout(new GridBagLayout());
    GridBagConstraints constraints = new GridBagConstraints();

    constraints.gridx = 0;
    constraints.gridy = 0;
    constraints.anchor = GridBagConstraints.CENTER;
    constraints.gridwidth = 2;
    constraints.insets = new Insets(5, 5, 5, 5);

    // Header

    title.setFont(title.getFont().deriveFont(
            1.5f * title.getFont().getSize()));
    add(title, constraints);

    // Left column
    constraints.gridy++;
    constraints.insets = new Insets(1, 5, 1, 5);
    constraints.gridwidth = 1;
    constraints.anchor = GridBagConstraints.EAST;
    constraints.weightx = 1;
    add(topicLabel, constraints);
    constraints.gridy++;
    add(startTimeLabel, constraints);
    constraints.gridy++;
    add(durationLabel, constraints);
    constraints.gridy++;
    add(lecturerLabel, constraints);
    constraints.gridy++;
    add(roomLabel, constraints);
    constraints.gridy++;
    add(contentCommentLabel, constraints);
    constraints.gridy++;
    add(techCommentLabel, constraints);
    constraints.gridy++;
    add(urlLabel, constraints);

    // Right column

    constraints.gridx++;
    constraints.gridy = 1;
    constraints.weightx = 60;
    constraints.fill = GridBagConstraints.HORIZONTAL;
    constraints.anchor = GridBagConstraints.WEST;

    topicField.setFont(topicField.getFont().deriveFont(Font.PLAIN));
    add(topicField, constraints);
    constraints.gridy++;
    startTimeField.setFont(startTimeField.getFont().deriveFont(Font.PLAIN));
    add(startTimeField, constraints);
    constraints.gridy++;
    durationField.setFont(durationField.getFont().deriveFont(Font.PLAIN));
    add(durationField, constraints);
    constraints.gridy++;
    lecturerField.setFont(lecturerField.getFont().deriveFont(Font.PLAIN));
    add(lecturerField, constraints);
    constraints.gridy++;
    roomField.setFont(lecturerField.getFont().deriveFont(Font.PLAIN));
    add(roomField, constraints);
    constraints.gridy++;
    contentCommentField.setFont(contentCommentField.getFont().deriveFont(Font.PLAIN));
    add(contentCommentField, constraints);
    constraints.gridy++;
    techCommentField.setFont(techCommentField.getFont().deriveFont(Font.PLAIN));
    add(techCommentField, constraints);
    constraints.gridy++;
    urlField.setEditable(false);
    add(urlField, constraints);

    constraints.gridy++;
    copyClpbrd.setEnabled(false);
    copyClpbrd.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        urlField.getText();
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(urlField.getText()), null);
      }
    });
    JPanel buttons = new JPanel();
    buttons.setLayout(new FlowLayout(FlowLayout.LEFT));
    buttons.add(copyClpbrd);
    add(buttons, constraints);

    setRecording(null);
  }

  public void setRecording(Recording r) {
    this.contentCommentField.setText(r == null || r.getContentComment().length() <= 0 ? "‹no comment›" : r.getContentComment());
    this.durationField.setText(r == null ? "‹no duration›" : FormatUtil.formatDuration(r.getDurationInSeconds())+" ("+r.getDurationInSeconds()+")");
    this.roomField.setText(r == null || r.getLectureRoomName() == null || r.getLectureRoomName().length() <= 0 ? "‹no room›" : r.getLectureRoomName());
    this.startTimeField.setText(r == null || r.getSessionBegin() == null ? "‹no start time›" : FormatUtil.formatDateTime(r.getSessionBegin()));
    this.techCommentField.setText(r == null || r.getTechnicalComment().length() <= 0 ? "‹no comment›" : r.getTechnicalComment());
    this.title.setText(r == null ? "<html><i>‹no title›</i></html>" : r.getLectureTitle());
    this.topicField.setText(r == null ? "‹no topic›" : r.getSessionTopic());

    // URL
    if (r == null || r.getUrl().length() <= 0) {
      this.urlField.setText("‹no URL›");
      this.copyClpbrd.setEnabled(false);
    } else {
      this.urlField.setText(r.getUrl());
      this.copyClpbrd.setEnabled(true);
    }

    // Lecturers
    if (r != null && r.getLecture() != null && r.getLecture().getLecturers() != null && r.getLecture().getLecturers().size()>=0) {
      List<Lecturer> lecturers = r.getLecture().getLecturers();
      StringBuilder builder = new StringBuilder("<html>");
      for (int i = 0; i < lecturers.size(); i++) {
        builder.append(
         (lecturers.get(i).getFirstName() + " <strong>" + lecturers.get(i).getLastName() + "</strong>")
           .replaceAll("\\.\\s*([A-ZÄÖÜ])", ".&thinsp;$1")
           .replaceAll("\\s+", "&nbsp;") +
           (i >= 1 ? "<br>" : "")
        );
      }
      builder.append("</html>");
      this.lecturerField.setText(builder.toString());
    } else {
      this.lecturerField.setText("<html>‹no lecturers›</html>");
    }
  }
}
