package fun.pancakes.planet_pancakes.date;

import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class WorldDateTest {

    @Test
    public void shouldOutputYearDateFromParsedString() throws Exception {
        String dateString = "163y2cIXDAY";

        WorldDate worldDate = new WorldDate(dateString);

        assertThat(worldDate.getYear()).isEqualTo(163);
    }

    @Test
    public void shouldOutputCycleFromParsedString() throws Exception {
        String dateString = "163y2cIXDAY";

        WorldDate worldDate = new WorldDate(dateString);

        assertThat(worldDate.getCycle()).isEqualTo(2);
    }

    @Test
    public void shouldOutputTwoDigitCycleFromParsedString() throws Exception {
        String dateString = "163y12cIXDAY";

        WorldDate worldDate = new WorldDate(dateString);

        assertThat(worldDate.getCycle()).isEqualTo(12);
    }

    @Test
    public void shouldOutputDateWONDAYFromParsedString() throws Exception {
        String dateString = "163y2cWONDAY";

        WorldDate worldDate = new WorldDate(dateString);

        assertThat(worldDate.getDay()).isEqualTo(WorldDay.WONDAY);
    }

    @Test
    public void shouldOutputDateTOODAYFromParsedString() throws Exception {
        String dateString = "163y2cTOODAY";

        WorldDate worldDate = new WorldDate(dateString);

        assertThat(worldDate.getDay()).isEqualTo(WorldDay.TOODAY);
    }

    @Test
    public void shouldOutputDateTRIDAYFromParsedString() throws Exception {
        String dateString = "163y2cTRIDAY";

        WorldDate worldDate = new WorldDate(dateString);

        assertThat(worldDate.getDay()).isEqualTo(WorldDay.TRIDAY);
    }

    @Test
    public void shouldOutputDateFORDAYFromParsedString() throws Exception {
        String dateString = "163y2cFORDAY";

        WorldDate worldDate = new WorldDate(dateString);

        assertThat(worldDate.getDay()).isEqualTo(WorldDay.FORDAY);
    }

    @Test
    public void shouldOutputDateTHIFFDAYFromParsedString() throws Exception {
        String dateString = "163y2cTHIFFDAY";

        WorldDate worldDate = new WorldDate(dateString);

        assertThat(worldDate.getDay()).isEqualTo(WorldDay.THIFFDAY);
    }

    @Test
    public void shouldOutputDateIXDAYFromParsedString() throws Exception {
        String dateString = "163y2cIXDAY";

        WorldDate worldDate = new WorldDate(dateString);

        assertThat(worldDate.getDay()).isEqualTo(WorldDay.IXDAY);
    }

    @Test
    public void shouldOutputDateKINGSDAYFromParsedString() throws Exception {
        String dateString = "163y2cKINGSDAY";

        WorldDate worldDate = new WorldDate(dateString);

        assertThat(worldDate.getDay()).isEqualTo(WorldDay.KINGSDAY);
    }

    @Test
    public void shouldOutputDateNEWDAYFromParsedString() throws Exception {
        String dateString = "163y2cNEWDAY";

        WorldDate worldDate = new WorldDate(dateString);

        assertThat(worldDate.getDay()).isEqualTo(WorldDay.NEWDAY);
    }

    @Test
    public void shouldOutputDateNONDAYFromParsedString() throws Exception {
        String dateString = "163y2cNONDAY";

        WorldDate worldDate = new WorldDate(dateString);

        assertThat(worldDate.getDay()).isEqualTo(WorldDay.NONDAY);
    }

    @Test
    public void shouldOutputDateSHUHDAYFromParsedString() throws Exception {
        String dateString = "163y2cSHUHDAY";

        WorldDate worldDate = new WorldDate(dateString);

        assertThat(worldDate.getDay()).isEqualTo(WorldDay.SHUHDAY);
    }

    @Test
    public void shouldOutputDateSHEDAYFromParsedString() throws Exception {
        String dateString = "163y2cSHEDAY";

        WorldDate worldDate = new WorldDate(dateString);

        assertThat(worldDate.getDay()).isEqualTo(WorldDay.SHEDAY);
    }

    @Test
    public void shouldOutputDateSHARDAYFromParsedString() throws Exception {
        String dateString = "163y2cSHARDAY";

        WorldDate worldDate = new WorldDate(dateString);

        assertThat(worldDate.getDay()).isEqualTo(WorldDay.SHARDAY);
    }

    @Test
    public void shouldOutputDateQUEENSDAYFromParsedString() throws Exception {
        String dateString = "163y2cQUEENSDAY";

        WorldDate worldDate = new WorldDate(dateString);

        assertThat(worldDate.getDay()).isEqualTo(WorldDay.QUEENSDAY);
    }

    @Test
    public void shouldOutputDateEMPERORDAYFromParsedString() throws Exception {
        String dateString = "163y2cEMPERORDAY";

        WorldDate worldDate = new WorldDate(dateString);

        assertThat(worldDate.getDay()).isEqualTo(WorldDay.EMPERORDAY);
    }

    @Test
    public void shouldCalculateCorrectDayNumber() throws Exception {
        String dateString = "163y2cEMPERORDAY";

        WorldDate worldDate = new WorldDate(dateString);

        assertThat(worldDate.daysSinceEpoch()).isEqualTo(18172);
    }

    @Test(expected = InvalidWorldDateStringException.class)
    public void shouldThrowExceptionWhenInvalidDay() throws Exception {
        String dateString = "163y2cALEXDAY";

        new WorldDate(dateString);
    }

    @Test(expected = InvalidWorldDateStringException.class)
    public void shouldThrowExceptionWhenNoDay() throws Exception {
        String dateString = "163y2c";

        new WorldDate(dateString);
    }

    @Test(expected = InvalidWorldDateStringException.class)
    public void shouldThrowExceptionWhenInvalidCycle() throws Exception {
        String dateString = "163y15cEMPERORDAY";

        new WorldDate(dateString);
    }

    @Test(expected = InvalidWorldDateStringException.class)
    public void shouldThrowExceptionWhenZeroCycle() throws Exception {
        String dateString = "163y0cEMPERORDAY";

        new WorldDate(dateString);
    }

    @Test(expected = InvalidWorldDateStringException.class)
    public void shouldThrowExceptionWhenNoCycle() throws Exception {
        String dateString = "163yEMPERORDAY";

        new WorldDate(dateString);
    }

    @Test(expected = InvalidWorldDateStringException.class)
    public void shouldThrowExceptionWhenZeroYear() throws Exception {
        String dateString = "0y5cEMPERORDAY";

        new WorldDate(dateString);
    }

    @Test(expected = InvalidWorldDateStringException.class)
    public void shouldThrowExceptionWhenEmpty() throws Exception {
        String dateString = "";

        new WorldDate(dateString);
    }
}