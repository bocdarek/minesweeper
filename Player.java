package minesweeper;

import java.util.HashSet;
import java.util.Set;

public class Player {

    private final Set<Field> markedFields = new HashSet<>();
    private final Set<Field> exploredFields = new HashSet<>();

    public Set<Field> getMarkedFields() {
        return markedFields;
    }

    public Set<Field> getExploredFields() {
        return exploredFields;
    }

    public void updateMarkedFields(Field field) {
        if (markedFields.contains(field)) {
            markedFields.remove(field);
        } else {
            markedFields.add(field);
        }
    }
}
