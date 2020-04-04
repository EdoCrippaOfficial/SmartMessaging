package elevati.inc.smartmessaging.utils;


import java.util.List;

import elevati.inc.parser.Message;

public class Format {

    private static String formatBody(String input, FormatType format) {
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

    public static String formatMessages(List<Message> messages) {
        StringBuilder messagesText = new StringBuilder();
        int i = 0, j = 0;
        for (Message m: messages) {
            i++;
            j++;
            messagesText.append("Message ").append(i).append(":\n");
            messagesText.append("To: ");
            for (String receiver: m.getReceivers()) {
                messagesText.append(receiver).append("; ");
            }
            messagesText.append("\n");
            messagesText.append("Priority: ").append(m.getPriority()).append("\n");
            messagesText.append("Title: ").append(m.getTitle()).append("\n");
            String formattedBody = Format.formatBody(m.getBody(), FormatType.fromString(m.getFormat()));
            messagesText.append("Body:\n").append(formattedBody).append("\n");
            if (m.isCc())
                messagesText.append("CC\n");
            if (m.getImg().length() > 0) {
                messagesText.append("Image URL: ").append(m.getImg()).append("\n");
            }
            messagesText.append("\n\n");
            if (m.isSendAfterThis()) {
                messagesText.append("Sending messages: ");
                for (int k = j-1; k >= 0; k--) {
                    messagesText.append(i-k).append("; ");
                }
                j = 0;
                messagesText.append("\n\n");
            }
        }
        return messagesText.toString();
    }
}
