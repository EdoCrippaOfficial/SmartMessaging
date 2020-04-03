package elevati.inc.smartmessaging.utils;


public class Format {

    public static String formatText(String input, FormatType format) {
        if (FormatType.NONE.equals(format)) {
            return input;
        } else if (FormatType.ALL_CAPS.equals(format)) {
            return input.toUpperCase();
        } else if (FormatType.CAPITALIZATION.equals(format)) {
            int pos = 0;
            boolean capitalize = true;
            StringBuilder sb = new StringBuilder(input);
            while (pos < sb.length()) {
                if (sb.charAt(pos) == '.') {
                    capitalize = true;
                } else if (capitalize && !Character.isWhitespace(sb.charAt(pos))) {
                    sb.setCharAt(pos, Character.toUpperCase(sb.charAt(pos)));
                    capitalize = false;
                }
                pos++;
            }
            return sb.toString();
        } else if (FormatType.LOWERCASE.equals(format)) {
            return input.toLowerCase();
        }
        return input;
    }
}
