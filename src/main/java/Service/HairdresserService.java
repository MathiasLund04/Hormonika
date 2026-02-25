package Service;

import People.Hairdresser;

import java.util.ArrayList;
import java.util.List;

public class HairdresserService {

    private List<Hairdresser> hairdressers = new ArrayList<>();

    public List<Hairdresser> getHairdressers() {
        return new ArrayList<>(hairdressers);
    }

    public void addHairdresser(Hairdresser hairdresser) {
        hairdressers.add(hairdresser);
    }

    public Hairdresser getHairdresserById(int id) {
        for (Hairdresser hd : hairdressers) {
            if (hd.getId() == id) {
                return hd;
            }
        }
        return null;
    }

}
