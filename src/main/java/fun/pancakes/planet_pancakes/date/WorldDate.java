package fun.pancakes.planet_pancakes.date;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@AllArgsConstructor
public class WorldDate {

    private static final Integer CYCLES_IN_YEAR = 8;
    private static final Integer DAYS_IN_CYCLE = 14;
    private static final String WORLD_DATE_REGEX_PATTERN = "([1-9]\\d+)y([1-9]|1[0-4])c(WONDAY|TOODAY|TRIDAY|FORDAY|THIFFDAY|IXDAY|KINGSDAY|NEWDAY|NONDAY|SHUHDAY|SHEDAY|SHARDAY|QUEENSDAY|EMPERORDAY)";

    private Integer year;
    private Integer cycle;
    private WorldDay day;

    public WorldDate(String worldDateString) throws InvalidWorldDateStringException{
        Pattern p = Pattern.compile(WORLD_DATE_REGEX_PATTERN);
        Matcher m = p.matcher(worldDateString);

        if (m.matches()) {
            this.year = Integer.parseInt(m.group(1));
            this.cycle = Integer.parseInt(m.group(2));
            this.day = WorldDay.valueOf(m.group(3));
        } else {
            throw new InvalidWorldDateStringException();
        }
    }
    public Long daysSinceEpoch() {
        long daysInYears = (year - 1) * CYCLES_IN_YEAR * DAYS_IN_CYCLE;
        long daysInCycles = (cycle - 1) * DAYS_IN_CYCLE;
        return daysInYears + daysInCycles + day.getDayOfCycle();
    }
}
