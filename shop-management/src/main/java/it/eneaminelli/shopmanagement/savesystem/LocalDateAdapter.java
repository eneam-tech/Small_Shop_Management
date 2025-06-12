package it.eneaminelli.shopmanagement.savesystem;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

/**
 * A Gson TypeAdapter for the Java 8 LocalDate class.
 * This converts a LocalDate to a standard ISO 8601 string (e.g., "2024-12-25")
 * and vice-versa.
 */
public class LocalDateAdapter extends TypeAdapter<LocalDate> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    @Override
    public void write(JsonWriter out, LocalDate value) throws IOException {
        if (value == null) {
            out.nullValue(); // Write null to the JSON if the date is null
        } else {
            // Convert the LocalDate to a string and write it to the JSON
            out.value(value.format(FORMATTER));
        }
    }

    @Override
    public LocalDate read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull(); // Consume the null token
            return null;
        } else {
            // Read the string from the JSON and parse it back into a LocalDate
            String dateStr = in.nextString();
            return LocalDate.parse(dateStr, FORMATTER);
        }
    }
}