package fun.pancakes.planet_pancakes.date;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum WorldDay {
    WONDAY(1),
    TOODAY(2),
    TRIDAY(3),
    FORDAY(4),
    THIFFDAY(5),
    IXDAY(6),
    KINGSDAY(7),
    NEWDAY(8),
    NONDAY(9),
    SHUHDAY(10),
    SHEDAY(11),
    SHARDAY(12),
    QUEENSDAY(13),
    EMPERORDAY(14);

    private static final Map<Integer, WorldDay> BY_DAY_OF_CYCLE = new HashMap<>();

    WorldDay(Integer dayOfCycle){
        this.dayOfCycle = dayOfCycle;
    }

    static {
        for(WorldDay w: values()){
            BY_DAY_OF_CYCLE.put(w.dayOfCycle, w);
        }
    }

    private final Integer dayOfCycle;

    public static WorldDay valueOfIndex(int dayOfCycle) {
        return BY_DAY_OF_CYCLE.get(dayOfCycle);
    }
}
