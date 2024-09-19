package util.helper.refactor;

public class ToPrettyPrint {
    public static String print(String jsonInput) {
        // Indentation level.
        int indent = 0;
        // Result string.
        StringBuilder result = new StringBuilder();

        // Iterate over the characters in the JSON string.
        for (char x : jsonInput.toCharArray()) {

            // If we encounter a '{', we increase the indentation level.
            if (x == '{') {
                indent += 2;
                result.append(x);
                result.append('\n');
                result.append(" ".repeat(Math.max(0, indent)));
            }

            // If we encounter a '[', we add newline character, add '[', and then increase the indentation level.
            else if (x == '[') {
                //result.append('\n');
                //result.append(" ".repeat(Math.max(0, indent)));
                indent += 2;
                result.append(x);
                result.append('\n');
                result.append(" ".repeat(Math.max(0, indent)));
            }

            // If we encounter a '}' or ']', we decrease the indentation level.
            else if (x == '}' || x == ']') {
                indent -= 2;
                result.append('\n');
                result.append(" ".repeat(Math.max(0, indent)));
                result.append(x);
            }

            // If we encounter a ',', we add a newline and indent the next line.
            else if (x == ',') {
                result.append(x);
                result.append('\n');
                result.append(" ".repeat(Math.max(0, indent)));
            }

            // Otherwise, we just add the character to the output string.
            else {
                result.append(x);
            }
        }

        return result.toString();
    }
}
