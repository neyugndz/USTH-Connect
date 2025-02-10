package vn.edu.usth.connect.Schedule.TimeTable.Calender;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CalenderUtils {

    public static LocalDate selectedDate;

    // Format date to DD MMMM YYYY
    public static String formattedDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        return date.format(formatter);
    }

    // Format time to hh:mm:ss am/pm
    public static String formattedTime(LocalTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
        return time.format(formatter);
    }

    // Format time for event hh:mm
    public static String formattedShortTime(LocalTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return time.format(formatter);
    }

    // Change Datetime to Format: MMMM YYYY
    public static String monthYearFromDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM - yyyy");
        return date.format(formatter);
    }

    // Format date to MMMM DD
    public static String monthDayFromDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d");
        return date.format(formatter);
    }

    //
    public static ArrayList<LocalDate> daysInMonthArray() {
        // Create new Array store LocalDate YYYY-MMMM-DDDD
        ArrayList<LocalDate> daysInMonthArray = new ArrayList<>();

        // YearMonth => Change from YYYY-MMMM-DDDD to Month-Year
        YearMonth yearMonth = YearMonth.from(selectedDate);

        // Return number of days in that month
        int daysInMonth = yearMonth.lengthOfMonth();

        // LocateDate Month before and after
        LocalDate prevMonth = selectedDate.minusMonths(1);
        LocalDate nextMonth = selectedDate.plusMonths(1);

        // Same as yearMonth
        YearMonth prevYearMonth = YearMonth.from(prevMonth);
        int prevDaysInMonth = prevYearMonth.lengthOfMonth();

        // Create LocalDate start from day 1
        // getDayOfWeek: return Mon, Tue, .. => trans to 1, 2, 3, ...
        LocalDate firstOfMonth = CalenderUtils.selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        // I think show the days in the month :D
        for (int i = 1; i <= 42; i++) {

            if (i <= dayOfWeek)
                // Day from previous month
                daysInMonthArray.add(LocalDate.of(prevMonth.getYear(), prevMonth.getMonth(), prevDaysInMonth + i - dayOfWeek));
            else if (i > daysInMonth + dayOfWeek)
                // Day from next month
                daysInMonthArray.add(LocalDate.of(nextMonth.getYear(), nextMonth.getMonth(), i - dayOfWeek - daysInMonth));
            else
                // Day from current month
                daysInMonthArray.add(LocalDate.of(selectedDate.getYear(), selectedDate.getMonth(), i - dayOfWeek));
        }
        return daysInMonthArray;
    }

    public static ArrayList<LocalDate> daysInWeekArray(LocalDate selectedDate) {
        ArrayList<LocalDate> days = new ArrayList<>();
        LocalDate current = sundayForDate(selectedDate);
        LocalDate endDate = current.plusWeeks(1);

        while (current.isBefore(endDate)) {
            days.add(current);
            current = current.plusDays(1);
        }
        return days;
    }

    private static LocalDate sundayForDate(LocalDate current) {
        LocalDate oneWeekAgo = current.minusWeeks(1);

        while (current.isAfter(oneWeekAgo)) {
            if (current.getDayOfWeek() == DayOfWeek.SUNDAY)
                return current;

            current = current.minusDays(1);
        }

        return null;
    }

}
