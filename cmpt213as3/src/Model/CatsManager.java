package Model;

import java.util.List;
import java.util.ArrayList;

public class CatsManager {
    private List<Cat> cats = new ArrayList<>();

    public Cat getCat(int index) {
        return cats.get(index);
    }
}


