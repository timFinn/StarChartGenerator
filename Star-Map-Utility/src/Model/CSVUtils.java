// Class: CSVUtils.java
// Package: StarMap
// Purpose: To provide utilities for reading and writing to and from a CSV file.
// Author: Evan McGraw - Some code provided from online link, see  below.

package Model;

// Necessary Imports
import java.io.IOException; 
import java.io.Writer;
import java.util.List;


// code credited to 'mkyong.com/java/how-to-read-and-parse-csv-file-in-java'
public class CSVUtils {
    private static final char DEFAULT_SEPARATOR = ',';
    // Constructor with IO exception handle for writing.
    public static void writeLine(Writer w, List<String> values) throws IOException {
        writeLine(w, values, DEFAULT_SEPARATOR, ' ');
    }
    // Overloaded constructor with IO exception handle for writing.
    public static void writeLine(Writer w, List<String> values, char separators) throws IOException {
        writeLine(w, values, separators, ' ');
    }

    //https://tools.ietf.org/html/rfc4180
    private static String followCVSformat(String value) {
        // Provides an escape sequence for the '\' character.
        String result = value;
        if (result.contains("\"")) {
            result = result.replace("\"", "\"\"");
        }
        return result;

    }
    // Function used to write to a CSV file, using ',' as a seperator.
    public static void writeLine(Writer w, List<String> values, char separators, char customQuote) throws IOException {

        boolean first = true;

        //default customQuote is empty

        if (separators == ' ') {
            separators = DEFAULT_SEPARATOR;
        }

        StringBuilder sb = new StringBuilder();
        for (String value : values) {
            if (!first) {
                sb.append(separators);
            }
            if (customQuote == ' ') {
                sb.append(followCVSformat(value));
            } else {
                sb.append(customQuote).append(followCVSformat(value)).append(customQuote);
            }

            first = false;
        }
        sb.append("\n");
        w.append(sb.toString());
    }
}
