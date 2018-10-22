import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.assertEquals;

public class Part2_0_JavaTimeTest {

    @Test
    public void when_localDateCreated_expect_daysArePrintedOk() {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plus(1, ChronoUnit.DAYS);
        LocalDate yesterday = tomorrow.minusDays(2);

        System.out.println(today);
        System.out.println(tomorrow);
        System.out.println(yesterday);
    }

    @Test
    public void when_localDateTimeCreated_expect_print() {
        LocalDateTime a = LocalDateTime.of(2018, 10, 4, 12, 01);

        System.out.println(a);
    }

    @Test
    public void when_parisVersusSydney_expect_timeDifferenceIs8Hours() {
        ZoneId zone1 = ZoneId.of("Europe/Paris");
        ZoneId zone2 = ZoneId.of("Australia/Sydney");

        LocalTime now1 = LocalTime.now(zone1);
        LocalTime now2 = LocalTime.now(zone2);

        long hoursBetween = ChronoUnit.HOURS.between(now1, now2);
        long minutesBetween = ChronoUnit.MINUTES.between(now1, now2);

        System.out.println(hoursBetween);
        System.out.println(minutesBetween);
        assertEquals(9, hoursBetween);
    }

    @Test
    public void when_zoned_expect_print() {
        ZonedDateTime dateTime = ZonedDateTime.parse("2018-10-04T10:15:30+01:00[Europe/Paris]");
        System.out.println(dateTime);
    }

    @Test
    public void when_period_expect_nextBirthdayIsComputed() {
        LocalDate today = LocalDate.now();
        LocalDate birthday = LocalDate.of(1989, Month.DECEMBER, 1);

        LocalDate nextBDay = birthday.withYear(today.getYear());

        //If your birthday has occurred this year already, add 1 to the year.
        if (nextBDay.isBefore(today) || nextBDay.isEqual(today)) {
            nextBDay = nextBDay.plusYears(1);
        }

        Period p = Period.between(today, nextBDay);
        long p2 = ChronoUnit.DAYS.between(today, nextBDay);
        System.out.println("There are " + p.getMonths() + " months, and " +
                p.getDays() + " days until your next birthday. (" +
                p2 + " total days)");
    }

    @Test
    public void when_dateTimeFormetter_expect_print() {
        String ldStr = DateTimeFormatter.ISO_DATE.format(LocalDate.now());
        System.out.println(ldStr);
        String odtStr = DateTimeFormatter.ISO_DATE.format(OffsetDateTime.now());
        System.out.println(odtStr);
        String zdtStr = DateTimeFormatter.ISO_ZONED_DATE_TIME.format(ZonedDateTime.now());
        System.out.println(zdtStr);

        LocalDate fromStringDate1 = LocalDate.parse("2018-10-03", DateTimeFormatter.ISO_DATE);
        LocalDate fromStringDate2 = LocalDate.parse("2018-10-03");
        assertEquals(fromStringDate1, fromStringDate2);
    }
}
