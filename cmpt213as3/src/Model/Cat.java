package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Cat {
    private int prevDir = -1;  // 0 1 2 3 -> N E S W
    private List<Integer> lst = new ArrayList<>();

    public Cat() {
        for (int i = 0; i < 4; i++) {
            lst.add(i);
        }
    }

    public void move() {
        Collections.shuffle(lst);

        if (prevDir == -1) {

        } else {

        }

    }

}
