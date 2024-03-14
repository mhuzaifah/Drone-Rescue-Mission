package ca.mcmaster.se2aa4.island.team119;

import eu.ace_design.island.game.actions.Heading;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;

public class Information {

    private HashMap<InfoType, Object> storage;

    Information() {
        this.storage = new HashMap<InfoType, Object>();
    }

    public <T> T retreive(InfoType type, Class<T> klass) {
        Object item = storage.get(type);

        if (item == null) {
            return null;
        }

        if (!klass.isInstance(item)) {
            throw new IllegalArgumentException("Incompatible types");
        }

        return  klass.cast(item);
    }

    public void store(InfoType type, Object item) {

        switch(type) {
            case HEADING -> { item = Direction.toDirection((String) item); }
            case BUDGET, COST, EXTRAS, STATUS -> {}
            default -> throw new IllegalArgumentException(type + " is not a valid information type that we can store");
        }

        storage.put(type, item);
    }

}
