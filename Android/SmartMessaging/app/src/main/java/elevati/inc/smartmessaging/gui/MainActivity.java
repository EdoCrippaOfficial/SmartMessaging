package elevati.inc.smartmessaging.gui;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import elevati.inc.parser.Message;
import elevati.inc.parser.MessageParser;
import elevati.inc.smartmessaging.R;
import elevati.inc.smartmessaging.utils.Format;
import elevati.inc.smartmessaging.utils.FormatType;

public class MainActivity extends Activity {

    private String consoleText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // PARSE BUTTON
        findViewById(R.id.bn_parse).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText inputEditText = findViewById(R.id.text_input);
                handleParseButtonClick(inputEditText.getText().toString());
            }
        });

        // CONSOLE BUTTON
        findViewById(R.id.bn_console).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConsoleDialog();
            }
        });
    }

    private void handleParseButtonClick(String inputText) {
        MessageParser parser = new MessageParser(inputText);
        parser.parse();
        Message message = parser.getMessage();
        consoleText = parser.getConsoleText();
        String formattedText = Format.formatText(message.getBody(), FormatType.fromString(message.getFormat()));
        TextView textViewFormatted = findViewById(R.id.text_output);
        textViewFormatted.setText(formattedText);
    }

    private void showConsoleDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setCanceledOnTouchOutside(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_console);
        TextView textViewConsole = dialog.findViewById(R.id.text_console);
        textViewConsole.setText(consoleText);
        dialog.show();
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        Window window = dialog.getWindow();
        if (window == null) return;
        window.setLayout(width * 7/8, height * 3/4);
    }
}
