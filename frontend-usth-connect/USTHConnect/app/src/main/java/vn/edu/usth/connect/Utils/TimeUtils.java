package vn.edu.usth.connect.Utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeUtils {
    // Convert LocalDateTime to a readable string like "X minutes ago"
    public String getTimeAgo(String createdAt) {
        // Try parsing with multiple formats to handle fractional seconds
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"); // Microseconds
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS"); // Milliseconds
        DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"); // No fractional part

        // Try parsing the timestamp string to LocalDateTime
        LocalDateTime dateTime = null;
        try {
            dateTime = LocalDateTime.parse(createdAt, formatter1); // Try microseconds first
        } catch (Exception e) {
            try {
                dateTime = LocalDateTime.parse(createdAt, formatter2); // Try milliseconds
            } catch (Exception ex) {
                try {
                    dateTime = LocalDateTime.parse(createdAt, formatter3); // Try without fractional part
                } catch (Exception exc) {
                    // If parsing fails entirely, return null or handle the error as needed
                    return null;
                }
            }
        }

        // Get the current time
        LocalDateTime now = LocalDateTime.now();

        // Calculate the duration between now and the timestamp
        Duration duration = Duration.between(dateTime, now);

        // If the duration is longer than 2 weeks (14 days), do not display the notification
        if (duration.toDays() > 14) {
            return null;  // This will signal to not create or display the notification
        }

        // If the duration is in weeks, return in weeks format
        if (duration.toDays() > 7) {
            long weeks = duration.toDays() / 7;
            return weeks + (weeks > 1 ? " weeks ago" : " week ago");
        }

        // If the duration is in days
        if (duration.toDays() > 0) {
            return duration.toDays() + (duration.toDays() > 1 ? " days ago" : " day ago");
        }

        // If the duration is in hours
        if (duration.toHours() > 0) {
            return duration.toHours() + (duration.toHours() > 1 ? " hours ago" : " hour ago");
        }

        // If the duration is in minutes
        if (duration.toMinutes() > 0) {
            return duration.toMinutes() + (duration.toMinutes() > 1 ? " minutes ago" : " minute ago");
        }

        // If the duration is in seconds
        if (duration.getSeconds() > 0) {
            return duration.getSeconds() + (duration.getSeconds() > 1 ? " seconds ago" : " second ago");
        }

        // If no time difference, just return "Just now"
        return "Just now";
    }
}
