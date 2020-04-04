package elevati.inc.smartmessaging.gui;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import elevati.inc.parser.Message;
import elevati.inc.parser.MessageParser;
import elevati.inc.smartmessaging.R;
import elevati.inc.smartmessaging.utils.Format;

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
        List<Message> messages = parser.getMessages();
        consoleText = parser.getConsoleText();
        String textToDisplay = Format.formatMessages(messages);
        TextView textViewFormatted = findViewById(R.id.text_output);
        textViewFormatted.setMovementMethod(new ScrollingMovementMethod());
        textViewFormatted.setText(textToDisplay);
    }

    // Dialog della console
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
