package android.example.mobilecoursework1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Random;

public class IdentifyCarMakeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private final int[] carsArray = MainActivity.carsArray;
    private int carImageId; // To store image id of the randomly selected car.
    private boolean switchIsOn; // To store the state of timer switch.
    private CountDownTimer countDownTimer; // For the purpose of timer.
    private String car; // To store the name of the randomly selected car.
    private long timeLeft; // To store the time when the timer ticks.
    private final long timeLeftAtStart = 21000; // 21000 milliseconds [21 seconds].
    private MediaPlayer timerMediaPlayer; // To play audio when timer runs.
    private MediaPlayer answerMediaPlayer; // To play audio when answer is given.

    private TextView timerTextView;
    private TextView resultTextView;
    private TextView correctAnswerTextView;
    private Button actionButton;
    private ImageView carImageView;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify_car_make);

        actionButton = (Button) findViewById(R.id.identify_button);
        resultTextView = (TextView) findViewById(R.id.result_text);
        correctAnswerTextView = (TextView) findViewById(R.id.correct_answer_text);
        carImageView = (ImageView) findViewById(R.id.car_make_image_view);
        timerTextView = (TextView) findViewById(R.id.timerTextView1);
        spinner = (Spinner) findViewById(R.id.spinner);

        if (spinner != null) {
            spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        }
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.labels_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        if (spinner != null) {
            spinner.setAdapter(adapter);
        }

        // Getting the intent to check whether the switch is turned on or not.
        Intent intent = getIntent();
        switchIsOn = intent.getBooleanExtra("timer", false);

        resultTextView.setVisibility(View.INVISIBLE);
        correctAnswerTextView.setVisibility(View.INVISIBLE);

        // Restoring the state of the activity when the orientation changes.
        if (savedInstanceState != null) {
            boolean isResultTexViewVisible = savedInstanceState.getBoolean("resultTextView");
            boolean isAnswerTexViewVisible = savedInstanceState.getBoolean("answerTextView");
            String buttonText = savedInstanceState.getString("buttonText");
            carImageId = savedInstanceState.getInt("carImage");
            car = savedInstanceState.getString("displayedCar");
            switchIsOn = savedInstanceState.getBoolean("switchIsOn");

            carImageView.setImageResource(carImageId);
            actionButton.setText(buttonText);

            if (isResultTexViewVisible) {
                String resultText = savedInstanceState.getString("resultText");
                resultTextView.setText(resultText);
                resultTextView.setVisibility(View.VISIBLE);
                if (switchIsOn) {
                    String timerText = savedInstanceState.getString("timerText");
                    timerTextView.setText(timerText);
                    timerTextView.setVisibility(View.VISIBLE);
                }
                else {
                    timerTextView.setVisibility(View.INVISIBLE);
                }
                if (isAnswerTexViewVisible) {
                    String answerText = savedInstanceState.getString("answerText");
                    correctAnswerTextView.setText(answerText);
                    correctAnswerTextView.setBackgroundColor(Color.YELLOW);
                    correctAnswerTextView.setVisibility(View.VISIBLE);
                    resultTextView.setBackgroundColor(Color.parseColor("#DA0303"));
                }
                else {
                    resultTextView.setBackgroundColor(Color.parseColor("#328108"));
                }
            }
            else {
                if (switchIsOn) {
                    timeLeft = savedInstanceState.getLong("timeLeft");
                    startTimer(timeLeft);
                    timerTextView.setVisibility(View.VISIBLE);
                }
                else {
                    timerTextView.setVisibility(View.INVISIBLE);
                }
            }
        }
        else {
            randomImage();
            if (switchIsOn) {
                startTimer(timeLeftAtStart);
                timerTextView.setVisibility(View.VISIBLE);
            }
            else {
                timerTextView.setVisibility(View.INVISIBLE);
            }
        }
    }

    // Method to execute when user clicks the action button.
    public void performAction(View view) {
        // Checking the label of the button and performing the action accordingly.
        if (actionButton.getText().toString().equals(getString(R.string.identify))) {
            if (switchIsOn) {
                countDownTimer.cancel();
            }
            showResults();
        }
        else {
            nextImage();
        }
    }

    // Method to execute when user goes to next round.
    private void nextImage() {
        resultTextView.setVisibility(View.INVISIBLE);
        correctAnswerTextView.setVisibility(View.INVISIBLE);
        actionButton.setText(R.string.identify);
        randomImage();
        stopAnswerSoundPlaying();
        if (switchIsOn) {
            startTimer(timeLeftAtStart);
            timerTextView.setVisibility(View.VISIBLE);
        }
    }

    // Method to generate random car image.
    private void randomImage() {
        Random random = new Random();
        int randomInt = random.nextInt(40);
        carImageView.setImageResource(carsArray[randomInt]);
        // Getting the name of the car displayed.
        car = carImageView.getResources().getString(carsArray[randomInt]);
        car = car.substring(13, car.length() -5).toUpperCase();
        // Storing the image id of the selected car.
        carImageId = carsArray[randomInt];
    }

    // Method to show results.
    private void showResults() {
        String selectedCarMake = spinner.getSelectedItem().toString();
        if (selectedCarMake.equals(car)) {
            correctAnswer();
        }
        else {
            wrongAnswer(car);
        }
        actionButton.setText(R.string.next);
        stopTimerSoundPlaying();
    }

    // Method to execute when the correct answer is given.
    private void correctAnswer() {
        resultTextView.setText(getString(R.string.correct));
        resultTextView.setBackgroundColor(Color.parseColor("#328108"));
        resultTextView.setVisibility(View.VISIBLE);
        // Playing the answer audio.
        answerMediaPlayer = MediaPlayer.create(this, R.raw.correctapplause);
        answerMediaPlayer.start();
    }

    // Method to execute when the wrong answer is given.
    private void wrongAnswer(String correctCarMake) {
        resultTextView.setText(getString(R.string.wrong));
        resultTextView.setBackgroundColor(Color.parseColor("#DA0303"));
        resultTextView.setVisibility(View.VISIBLE);
        correctAnswerTextView.setText(correctCarMake);
        correctAnswerTextView.setBackgroundColor(Color.YELLOW);
        correctAnswerTextView.setVisibility(View.VISIBLE);
        // Playing the answer audio.
        answerMediaPlayer = MediaPlayer.create(this, R.raw.wrong);
        answerMediaPlayer.start();
    }

    // Method to start the timer.
    private void startTimer(long time) {
        // Playing the timer audio.
        timerMediaPlayer = MediaPlayer.create(IdentifyCarMakeActivity.this, R.raw.timersound);
        timerMediaPlayer.start();
        timerMediaPlayer.setLooping(true);

        countDownTimer = new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String timerText = "TIMER : " + millisUntilFinished / 1000;
                timeLeft = millisUntilFinished;
                timerTextView.setText(timerText);
            }
            @Override
            public void onFinish() {
                showResults();
            }
        }.start();
    }

    // Method to stop timer audio
    private void stopTimerSoundPlaying() {
        if (timerMediaPlayer != null) {
            timerMediaPlayer.stop();
            timerMediaPlayer.reset();
            timerMediaPlayer.release();
            timerMediaPlayer = null;
        }
    }

    // Method to stop the answers audio.
    private void stopAnswerSoundPlaying() {
        if (answerMediaPlayer != null) {
            answerMediaPlayer.stop();
            answerMediaPlayer.reset();
            answerMediaPlayer.release();
            answerMediaPlayer = null;
        }
    }

    // Saving the state of the activity when the orientation changes.
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (resultTextView.getVisibility() == View.VISIBLE) {
            outState.putBoolean("resultTextView", true);
            String result = resultTextView.getText().toString();
            outState.putString("resultText", result);
        }
        if (correctAnswerTextView.getVisibility() == View.VISIBLE) {
            outState.putBoolean("answerTextView", true);
            String answer = correctAnswerTextView.getText().toString();
            outState.putString("answerText", answer);
        }

        String buttonText = actionButton.getText().toString();
        outState.putString("buttonText", buttonText);

        outState.putString("displayedCar", car);
        outState.putInt("carImage", carImageId);

        if (switchIsOn) {
            String timerText = timerTextView.getText().toString();
            outState.putString("timerText", timerText);
            outState.putLong("timeLeft", timeLeft);
            outState.putBoolean("switchIsOn", true);
        }
    }

    // Stopping the timer and audio when the activity stops.
    @Override
    protected void onStop() {
        super.onStop();
        stopTimerSoundPlaying();
        stopAnswerSoundPlaying();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}