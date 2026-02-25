package org.example.hormonika;

import Model.Booking;
import Service.BookingService;
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

    private final BookingService bookingService =
            new BookingService(new DBRepo(new DBConfig()));

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
        List<Booking> bookings = bookingService.getCalendar()
                .stream()
                .filter(b -> b.getDate().equals(date))
                .toList();

        for (Booking b : bookings) {
            placeBooking(b);
        }
    }

    private void placeBooking(Booking b) {

        int col = employees.indexOf(b.getHairdresser()) + 1;

        LocalTime dayStart = LocalTime.of(8, 0);
        LocalTime start = b.getTime();
        LocalTime end = b.getEndTime();

        int startRow = (int) Duration.between(dayStart, start).toMinutes() / 15 + 1;
        int endRow = (int) Duration.between(dayStart, end).toMinutes() / 15 + 1;

        Pane p = new Pane();
        p.setStyle("-fx-background-color: " +
                getColorForHairdresser(employees.indexOf(b.getHairdresser())) +
                "; -fx-border-color: gray;");

        Label nameLabel = new Label(b.getName());
        nameLabel.setStyle("-fx-font-size: 10; -fx-text-fill: black;");
        nameLabel.setLayoutX(3);
        nameLabel.setLayoutY(3);
        p.getChildren().add(nameLabel);

        Tooltip.install(p, new Tooltip(
                b.getName() + "\n" +
                        b.getPhoneNr() + "\n" +
                        b.getDescription()
        ));

        p.setOnMouseClicked(e -> openEditBookingDialog(b));

        grid.add(p, col, startRow, 1, endRow - startRow);
    }

    private String getColorForHairdresser(int id) {
        return switch (id) {
            case 0 -> "#87a498";
            case 1 -> "#f4b6c2";
            case 2 -> "#c9cba3";
            case 3 -> "#ffdac1";
            case 4 -> "#b5ead7";
            default -> "#cccccc";
        };
    }

    @FXML
    private void openBookingDialog() {

        Dialog<Booking> dialog = new Dialog<>();
        dialog.setTitle("Ny booking");

        TextField nameField = new TextField();
        TextField phoneField = new TextField();

        ComboBox<String> employeeBox = new ComboBox<>();
        employeeBox.getItems().addAll(employees);

        ComboBox<LocalTime> timeBox = new ComboBox<>();
        for (LocalTime t = LocalTime.of(8, 0); !t.isAfter(LocalTime.of(17, 0)); t = t.plusMinutes(15)) {
            timeBox.getItems().add(t);
        }

        ComboBox<Integer> durationBox = new ComboBox<>();
        durationBox.getItems().addAll(30, 45, 60);
        durationBox.setValue(30);

        TextArea descriptionArea = new TextArea();

        GridPane gp = new GridPane();
        gp.setHgap(10);
        gp.setVgap(10);

        gp.add(new Label("Navn:"), 0, 0);
        gp.add(nameField, 1, 0);

        gp.add(new Label("Telefon:"), 0, 1);
        gp.add(phoneField, 1, 1);

        gp.add(new Label("Frisør:"), 0, 2);
        gp.add(employeeBox, 1, 2);

        gp.add(new Label("Starttid:"), 0, 3);
        gp.add(timeBox, 1, 3);

        gp.add(new Label("Varighed (min):"), 0, 4);
        gp.add(durationBox, 1, 4);

        gp.add(new Label("Beskrivelse:"), 0, 5);
        gp.add(descriptionArea, 1, 5);

        dialog.getDialogPane().setContent(gp);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(btn -> {
            if (btn == ButtonType.OK) {
                try {
                    LocalTime start = timeBox.getValue();
                    int duration = durationBox.getValue();
                    LocalTime end = start.plusMinutes(duration);

                    return bookingService.createBooking(
                            nameField.getText(),
                            Integer.parseInt(phoneField.getText()),
                            datePicker.getValue(),
                            start,
                            null, // HairStyles
                            employeeBox.getValue(),      // ← HER: direkte String, ikke indexOf
                            descriptionArea.getText(),
                            end
                    );


                } catch (Exception e) {
                    return null;
                }
            }
            return null;
        });

        dialog.showAndWait().ifPresent(booking -> {
            if (booking == null) {
                showError("Frisøren er allerede booket på dette tidspunkt");
            } else {
                buildCalendar();
            }
        });
    }

    private void openEditBookingDialog(Booking booking) {

        Dialog<Booking> dialog = new Dialog<>();
        dialog.setTitle("Rediger booking");

        TextField nameField = new TextField(booking.getName());
        TextField phoneField = new TextField(String.valueOf(booking.getPhoneNr()));

        ComboBox<String> employeeBox = new ComboBox<>();
        employeeBox.getItems().addAll(employees);
        employeeBox.setValue(booking.getHairdresser());


        ComboBox<LocalTime> timeBox = new ComboBox<>();
        for (LocalTime t = LocalTime.of(8, 0); !t.isAfter(LocalTime.of(17, 0)); t = t.plusMinutes(15)) {
            timeBox.getItems().add(t);
        }
        timeBox.setValue(booking.getTime());

        TextArea descriptionArea = new TextArea(booking.getDescription());

        GridPane gp = new GridPane();
        gp.setHgap(10);
        gp.setVgap(10);

        gp.add(new Label("Navn:"), 0, 0);
        gp.add(nameField, 1, 0);

        gp.add(new Label("Telefon:"), 0, 1);
        gp.add(phoneField, 1, 1);

        gp.add(new Label("Frisør:"), 0, 2);
        gp.add(employeeBox, 1, 2);

        gp.add(new Label("Tid:"), 0, 3);
        gp.add(timeBox, 1, 3);

        gp.add(new Label("Beskrivelse:"), 0, 4);
        gp.add(descriptionArea, 1, 4);

        dialog.getDialogPane().setContent(gp);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(btn -> {
            if (btn == ButtonType.OK) {
                booking.setName(nameField.getText());
                booking.setPhoneNr(Integer.parseInt(phoneField.getText()));
                booking.setHairdresser(employeeBox.getValue());
                booking.setTime(timeBox.getValue());
                booking.setDescription(descriptionArea.getText());
                return booking;
            }
            return null;
        });

        dialog.showAndWait().ifPresent(b -> buildCalendar());
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}