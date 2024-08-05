package src.Utilities;

public class StringUtil {

    // Constants
    private static final int MAX_LINE_LENGTH = 80;

    // Printing indented text
    public static void printIndentedWrappedString(String text, int indent) {

        // Varaibles
        String indentString = " ".repeat(indent);
        String[] words = text.split(" ");

        StringBuilder line = new StringBuilder(indentString);
        for (String word : words) {

            // Check if adding the word would exceed the line length limit
            if (line.length() + word.length() + 1 > MAX_LINE_LENGTH) {
                System.out.println(line.toString());
                line = new StringBuilder(indentString);
            }
            line.append(word).append(" ");

        }

        // Print the last line
        System.out.println(line.toString());

    }

}
