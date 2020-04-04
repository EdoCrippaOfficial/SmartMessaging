package elevati.inc.smartmessaging.utils;

enum FormatType {

    NONE ("none"),
    ALL_CAPS ("all caps"),
    CAPITALIZATION ("capitalization"),
    LOWERCASE ("lowercase");

    private String string;

    FormatType(String string) {
        this.string = string;
    }

    public static FormatType fromString(String string) {
        for (FormatType t: FormatType.values()) {
            if (t.string.equalsIgnoreCase(string))
                return t;
        }
        return NONE;
    }
}