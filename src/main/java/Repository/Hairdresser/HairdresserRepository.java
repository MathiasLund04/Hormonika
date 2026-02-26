package Repository.Hairdresser;

import Model.Hairdresser;

import java.sql.SQLException;
import java.util.List;

public interface HairdresserRepository {
    String getHairdresserNameById(int id) throws SQLException;
    List<Hairdresser> getHairdressers() throws SQLException;
}
