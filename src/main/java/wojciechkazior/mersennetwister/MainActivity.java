package wojciechkazior.mersennetwister;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText editText = findViewById(R.id.editText);
        final Button button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                int seed = Integer.parseInt(editText.getText().toString());
                generateAndSaveToFile(seed);
                Toast.makeText(MainActivity.this, "The file is ready!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void generateAndSaveToFile(int seed) {
        MersenneTwister mersenneTwister = new MersenneTwister(seed);
        byte[] tenMillionBytes = new byte[10000000];

        for (int i = 0; i < 2500000; i++) {
            int generatedInteger = mersenneTwister.random();
            int arrayIndex = i * 4;
            this.insertIntegerIntoByteArray(tenMillionBytes, generatedInteger, arrayIndex);

            int bytesGenerated = (i + 1) * 4;
            if (bytesGenerated % 1000000 == 0) {
                Log.d("", bytesGenerated + " bytes generated");
            }
        }

        try {
            FileOutputStream fos = openFileOutput("output", MODE_PRIVATE);
            fos.write(tenMillionBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void insertIntegerIntoByteArray(byte[] byteArray, int integer, int index) {
        byte[] integerBytes = ByteBuffer.allocate(4).putInt(integer).array();
        byteArray[index + 0] = integerBytes[0];
        byteArray[index + 1] = integerBytes[1];
        byteArray[index + 2] = integerBytes[2];
        byteArray[index + 3] = integerBytes[3];
    }
}