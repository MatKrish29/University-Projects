package android.example.mobilecoursework1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    private Button identifyCarMakeButton;
    private Button identifyCarImageButton;
    private Button hintsButton;

    private Switch timerSwitch;
    // Storing all the car images in an array so that other activities can access.
    public static int[] carsArray = {R.drawable.audi1, R.drawable.audi2, R.drawable.audi3,
            R.drawable.audi4, R.drawable.audi5, R.drawable.bentley1, R.drawable.bentley2,
            R.drawable.bentley3, R.drawable.bentley4, R.drawable.bentley5, R.drawable.bmw1,
            R.drawable.bmw2, R.drawable.bmw3, R.drawable.bmw4, R.drawable.bmw5, R.drawable.ford1,
            R.drawable.ford2, R.drawable.ford3, R.drawable.ford4, R.drawable.ford5,
            R.drawable.honda1, R.drawable.honda2, R.drawable.honda3, R.drawable.honda4,
            R.drawable.honda5, R.drawable.ferrari1, R.drawable.ferrari2, R.drawable.ferrari3,
            R.drawable.ferrari4, R.drawable.ferrari5, R.drawable.mercedes1, R.drawable.mercedes2,
            R.drawable.mercedes3, R.drawable.mercedes4, R.drawable.mercedes5, R.drawable.nissan1,
            R.drawable.nissan2, R.drawable.nissan3, R.drawable.nissan4, R.drawable.nissan5};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        identifyCarMakeButton = (Button) findViewById(R.id.identify_the_car_make);
        identifyCarImageButton = (Button) findViewById(R.id.identify_the_car_image);
        hintsButton = (Button) findViewById(R.id.hints);
        timerSwitch = (Switch) findViewById(R.id.timer_switch);
    }

    // Method to launch the game activities.
    public void launchGameActivity(View view) {
        Intent intent;
        // Checking which button is clicked using the view and launching the respective activity.
        if (view.getId() == identifyCarMakeButton.getId()) {
            intent = new Intent(MainActivity.this, IdentifyCarMakeActivity.class);
        }
        else if (view.getId() == identifyCarImageButton.getId()) {
            intent = new Intent(MainActivity.this, IdentifyCarImageActivity.class);
        }
        else if (view.getId() == hintsButton.getId()) {
            intent = new Intent(MainActivity.this, HintsActivity.class);
        }
        else {
            intent = new Intent(MainActivity.this, AdvancedLevelActivity.class);
        }
        // Passing the intent message if the switch is turned on or not.
        if (timerSwitch.isChecked()) {
            intent.putExtra("timer", true);
        }
        startActivity(intent);
        // Playing audio when an activity starts.
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.ferraripassingby);
        mediaPlayer.start();
    }
}