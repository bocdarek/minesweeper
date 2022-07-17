package minesweeper;

import java.util.HashSet;
import java.util.Set;

public class Player {

    private Set<Field> visitedFields = new HashSet<>();

    public Set<Field> getVisitedFields() {
        return visitedFields;
    }

    public void setVisitedFields(Set<Field> visitedFields) {
        this.visitedFields = visitedFields;
    }

    public void updateVisitedFields(Field field) {
        if (visitedFields.contains(field)) {
            visitedFields.remove(field);
        } else {
            visitedFields.add(field);
        }
    }

}
