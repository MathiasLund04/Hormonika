package org.example.hormonika;

import Enums.HairStyles;
import Model.Booking;
import Repository.Booking.MySQLBookingRepository;
import Service.BookingService;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentController {

    @FXML private TextField customerName;
    @FXML private TextField phoneNumber;
    @FXML private DatePicker newAppointmentDataPicker;
    @FXML private ComboBox<String> haircutTypeBox;   // medarbejder
    @FXML private ComboBox<String> haircutTypeBox1;  // tid
    @FXML private TextArea descriptionArea;

    @FXML private RadioButton dameKlip;
    @FXML private RadioButton herreKlip;
    @FXML private RadioButton borneKlip;
    @FXML private RadioButton skaegTrim;
    @FXML private RadioButton perm;
    @FXML private RadioButton farvning;

    @FXML private Label errorChoiceLabel;

    private final BookingService bookingService =
            new BookingService(new MySQLBookingRepository(new DAL.DBConfig()));

    @FXML
    public void initialize() {

        // Medarbejdere
        haircutTypeBox.getItems().addAll("Mads", "Ida", "Fie", "Mie", "Monika");

        // Tider (hver 15. minut)
        for (LocalTime t = LocalTime.of(8, 0); !t.isAfter(LocalTime.of(17, 0)); t = t.plusMinutes(15)) {
            haircutTypeBox1.getItems().add(t.toString());
        }

        // ToggleGroup til klipningstyper
        ToggleGroup group = new ToggleGroup();
        dameKlip.setToggleGroup(group);
        herreKlip.setToggleGroup(group);
        borneKlip.setToggleGroup(group);
        skaegTrim.setToggleGroup(group);
        perm.setToggleGroup(group);
        farvning.setToggleGroup(group);

        // Default dato
        newAppointmentDataPicker.setValue(LocalDate.now());
    }

    private HairStyles getSelectedHairStyle() {
        if (dameKlip.isSelected()) return HairStyles.WOMANCUT;
        if (herreKlip.isSelected()) return HairStyles.MANCUT;
        if (borneKlip.isSelected()) return HairStyles.CHILDCUT;
        if (skaegTrim.isSelected()) return HairStyles.BEARD;
        if (perm.isSelected()) return HairStyles.PERM;
        if (farvning.isSelected()) return HairStyles.COLOUR;
        return HairStyles.OTHER;
    }

    @FXML
    private void onMakeNewAppointment() {

        try {
            String name = customerName.getText();
            String phone = phoneNumber.getText();
            LocalDate date = newAppointmentDataPicker.getValue();
            String employeeName = haircutTypeBox.getValue();
            LocalTime time = LocalTime.parse(haircutTypeBox1.getValue());
            HairStyles haircut = getSelectedHairStyle();
            String description = descriptionArea.getText();

            LocalTime endTime = time.plusMinutes(30);

            // Konverter medarbejdernavn → ID
            int hairdresserId = switch (employeeName) {
                case "Mads" -> 0;
                case "Ida" -> 1;
                case "Fie" -> 2;
                case "Mie" -> 3;
                case "Monika" -> 4;
                default -> 0;
            };

            Booking booking = bookingService.createBooking(
                    name,
                    phone,
                    date,
                    time,
                    haircut,
                    hairdresserId,
                    description
            );

            if (booking == null) {
                errorChoiceLabel.setText("Tiden er optaget!");
                return;
            }

            errorChoiceLabel.setText("Booking oprettet!");

            // Skift tilbage til kalenderen efter 1 sekund
            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                    javafx.application.Platform.runLater(() -> {
                        SceneSwitcher.switchTo("calendar-view");
                    });
                } catch (InterruptedException ignored) {}
            }).start();

        } catch (Exception e) {
            errorChoiceLabel.setText("Udfyld alle felter korrekt.");
        }
    }


}