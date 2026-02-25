package org.example.hormonika;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

    public class CalendarController {

        @FXML private DatePicker datePicker;
        @FXML private ScrollPane scrollPane;
        @FXML private GridPane grid;

        private final List<String> employees = List.of("Mads", "Ida", "Fie", "Mie", "Monika");

        @FXML
        public void initialize() {
            datePicker.setValue(LocalDate.now());
            buildCalendar();
        }

        @FXML
        private void onUpdate() {
            buildCalendar();
        }

        private void buildCalendar() {
            grid.getChildren().clear();
            buildEmployeeColumns();
            buildTimeRows();
            loadBookingsForDate(datePicker.getValue());
        }

        private void buildEmployeeColumns() {
            int col = 1;
            for (String emp : employees) {
                Label label = new Label(emp);
                label.setStyle("-fx-font-weight: bold; -fx-padding: 5;");
                grid.add(label, col, 0);
                col++;
            }
        }

        private void buildTimeRows() {
            LocalTime start = LocalTime.of(8, 0);
            LocalTime end = LocalTime.of(17, 0);

            int row = 1;

            for (LocalTime t = start; !t.isAfter(end); t = t.plusMinutes(30)) {
                Label timeLabel = new Label(t.toString());
                timeLabel.setStyle("-fx-padding: 5;");
                grid.add(timeLabel, 0, row);
                row++;
            }
        }

        /*
        private void loadBookingsForDate(LocalDate date) {
            List<Booking> bookings = DemoData.getBookings(date);

            for (Booking b : bookings) {
                placeBooking(b);
            }
        }

        private void placeBooking(Booking b) {
            int col = employees.indexOf(b.employee()) + 1;

            LocalTime start = LocalTime.of(8, 0);

            int startRow = (int) Duration.between(start, b.start()).toMinutes() / 30 + 1;
            int endRow = (int) Duration.between(start, b.end()).toMinutes() / 30 + 1;

            for (int row = startRow; row < endRow; row++) {
                Pane p = new Pane();
                p.setStyle("-fx-background-color: lightblue; -fx-border-color: black;");
                grid.add(p, col, row);
            }
        }

         */
    }


