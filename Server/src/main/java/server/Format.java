package server;

public enum Format {

    NONE ("none"),
    ALL_CAPS ("all caps"),
    CAPITALIZATION ("capitalization"),
    LOWERCASE ("lowercase");

    private String string;

    Format(String string) {
        this.string = string;
    }

    public static Format fromString(String string) {
        for (Format t: Format.values()) {
            if (t.string.equalsIgnoreCase(string))
                return t;
        }
        System.out.println("WARNING: " + string + " non è un formato valido. Nessuna formattazione verrà applicata");
        return NONE;
    }

    public static String formatText(String input, Format format) {
        if (NONE.equals(format)) {
            return input;
        } else if (ALL_CAPS.equals(format)) {
            return input.toUpperCase();
        } else if (CAPITALIZATION.equals(format)) {
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
        } else if (LOWERCASE.equals(format)) {
            return input.toLowerCase();
        }
        return input;
    }
}
