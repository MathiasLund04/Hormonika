package org.example.hormonika;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.time.Duration;
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

        for (LocalTime t = start; !t.isAfter(end); t = t.plusMinutes(15)) {
            Label timeLabel = new Label(t.toString());
            timeLabel.setStyle("-fx-padding: 5;");
            grid.add(timeLabel, 0, row);
            row++;
        }
    }

    private void loadBookingsForDate(LocalDate date) {
        List<Booking> bookings = DemoData.getBookings(date);

        for (Booking b : bookings) {
            placeBooking(b);
        }
    }

    private void placeBooking(Booking b) {
        int col = employees.indexOf(b.employee()) + 1;

        LocalTime start = LocalTime.of(8, 0);

        int startRow = (int) Duration.between(start, b.start()).toMinutes() / 15 + 1;
        int endRow = (int) Duration.between(start, b.end()).toMinutes() / 15 + 1;

        for (int row = startRow; row < endRow; row++) {
            Pane p = new Pane();
            p.setStyle("-fx-background-color: #87a498; -fx-border-color: gray;");
            grid.add(p, col, row);
        }
    }

    @FXML
    private void openBookingDialog() {
        Dialog<Booking> dialog = new Dialog<>();
        dialog.setTitle("Ny booking");

        // Felter
        ComboBox<String> employeeBox = new ComboBox<>();
        employeeBox.getItems().addAll(employees);

        ComboBox<LocalTime> startBox = new ComboBox<>();
        ComboBox<LocalTime> endBox = new ComboBox<>();

        for (LocalTime t = LocalTime.of(8,0); !t.isAfter(LocalTime.of(17,0)); t = t.plusMinutes(15)) {
            startBox.getItems().add(t);
            endBox.getItems().add(t);
        }

        GridPane gp = new GridPane();
        gp.setHgap(10);
        gp.setVgap(10);

        gp.add(new Label("Medarbejder:"), 0, 0);
        gp.add(employeeBox, 1, 0);

        gp.add(new Label("Starttid:"), 0, 1);
        gp.add(startBox, 1, 1);

        gp.add(new Label("Sluttid:"), 0, 2);
        gp.add(endBox, 1, 2);

        dialog.getDialogPane().setContent(gp);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(btn -> {
            if (btn == ButtonType.OK) {
                return new Booking(
                        employeeBox.getValue(),
                        startBox.getValue(),
                        endBox.getValue()
                );
            }
            return null;
        });

        dialog.showAndWait().ifPresent(booking -> {
            BookingStore.addBooking(datePicker.getValue(), booking);
            buildCalendar();
        });
    }

}
