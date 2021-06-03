package android.example.mobilecoursework1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class HintsActivity extends AppCompatActivity {

    private final int[] carsArray = MainActivity.carsArray;
    private int wrongAnswerSubmitCount;
    private boolean switchIsOn;
    private String car;
    private final long timeLeftAtStart = 21000; // 21000 milliseconds [21 seconds].
    private long timeLeft;
    private int carImageId;
    private MediaPlayer timerMediaPlayer;
    private MediaPlayer answerMediaPlayer;

    private Button actionButton;
    private TextView hintTextView;
    private TextView resultTextView;
    private TextView correctAnswerTextView;
    private TextView timerTextView;
    private TextView triesLeftTextView;
    private ImageView carImageView;
    private EditText userGuessEditText;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hints);

        actionButton = (Button) findViewById(R.id.hints_submit_button);
        hintTextView = (TextView) findViewById(R.id.hints_blanks_text);
        userGuessEditText = (EditText) findViewById(R.id.hints_editText);
        resultTextView = (TextView) findViewById(R.id.hints_result_text);
        timerTextView = (TextView) findViewById(R.id.timer_text_view4);
        triesLeftTextView = (TextView) findViewById(R.id.hints_tries_left_textView);
        carImageView = (ImageView) findViewById(R.id.hints_imageView);
        correctAnswerTextView = (TextView) findViewById(R.id.hints_correct_answer_text);

        resultTextView.setVisibility(View.INVISIBLE);
        correctAnswerTextView.setVisibility(View.INVISIBLE);

        // Getting the intent to check whether the switch is turned on or not.
        Intent intent = getIntent();
        switchIsOn = intent.getBooleanExtra("timer", false);

        // Restoring the state of the activity when the orientation changes.
        if (savedInstanceState != null) {
            boolean isResultTexViewVisible = savedInstanceState.getBoolean("resultTextView");
            boolean isAnswerTexViewVisible = savedInstanceState.getBoolean("answerTextView");
            String buttonText = savedInstanceState.getString("buttonText");
            String lives = savedInstanceState.getString("lives");
            String hintText = savedInstanceState.getString("hintText");
            carImageId = savedInstanceState.getInt("carImage");
            car = savedInstanceState.getString("displayedCar");
            wrongAnswerSubmitCount = savedInstanceState.getInt("submitCount");
            switchIsOn = savedInstanceState.getBoolean("switchIsOn");

            carImageView.setImageResource(carImageId);
            actionButton.setText(buttonText);
            triesLeftTextView.setText(lives);
            hintTextView.setText(hintText);

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
            setBlanks();
            if (switchIsOn) {
                startTimer(timeLeftAtStart);
                timerTextView.setVisibility(View.VISIBLE);
            }
            else {
                timerTextView.setVisibility(View.INVISIBLE);
            }
        }
    }

    // Method to generate random car image.
    private void randomImage() {
        Random random = new Random();
        int randomInt = random.nextInt(40);
        carImageView.setImageResource(carsArray[randomInt]);
        car = carImageView.getResources().getString(carsArray[randomInt]);
        car = car.substring(13, car.length() -5).toUpperCase();
        carImageId = carsArray[randomInt];
    }

    // Method to execute when user goes to the next hint round.
    private void nextHintRound() {
        wrongAnswerSubmitCount = 0;
        randomImage();
        setBlanks();
        resultTextView.setVisibility(View.INVISIBLE);
        userGuessEditText.setText(null);
        userGuessEditText.setHint("Enter Your Guess");
        triesLeftTextView.setText(R.string.initial_lives);
        correctAnswerTextView.setVisibility(View.INVISIBLE);
        actionButton.setText(R.string.submit);
        stopAnswerSoundPlaying();
        if (switchIsOn) {
            startTimer(timeLeftAtStart);
        }
    }

    // Method to set the blanks according to the displayed car image.
    private void setBlanks() {
        String blank = "_ ";
        hintTextView.setText(null);
        for (int i=0; i<car.length(); i++) {
            String setHintText = hintTextView.getText() + blank;
            hintTextView.setText(setHintText);
        }
    }

    // Method to execute when action button is clicked.
    public void performAction(View view) {
        // Checking the label of the button and performing the action accordingly.
        if (actionButton.getText().toString().equals(getString(R.string.submit))) {
            showResults();
        }
        else {
            nextHintRound();
        }
    }

    // Method to check the answer and show results.
    private void showResults() {
        int count = 0;
        boolean correctLetterFound = false;
        String hintBlank = hintTextView.getText().toString();
        char[] carModelGuess = new char[hintBlank.length()];
        String guessLetter = userGuessEditText.getText().toString().toUpperCase();

        // Storing the hint text view text in an array.
        for (int i=0; i<hintBlank.length(); i++) {
            carModelGuess[i] = hintBlank.charAt(i);
        }

        // To make sure null value is not passed on while checking the answer.
        if (guessLetter.equals("")) {
            guessLetter = " ";
        }

        /* Checking whether the guessed letter is there in the actual car image name and filling
        the hint text view accordingly.*/
        for (int i = 0; i < car.length(); i++) {
            if (car.charAt(i) == guessLetter.charAt(0)) {
                carModelGuess[i+count] = guessLetter.charAt(0);
                correctLetterFound = true;
            }
            count++;
        }
        String hint = new String(carModelGuess);
        hintTextView.setText(hint);
        userGuessEditText.setText(null);

        // Actions to perform if correct letter is not found or guessed letter is already found.
        if (!correctLetterFound || hintBlank.contains(guessLetter)) {
            wrongAnswerSubmitCount++;
            String livesText = "LIVES : " + (3-wrongAnswerSubmitCount);
            triesLeftTextView.setText(livesText);
        }

        if (switchIsOn) {
            if (wrongAnswerSubmitCount == 3) {
                stopTimerSoundPlaying();
                countDownTimer.cancel();
            } else {
                stopTimerSoundPlaying();
                countDownTimer.cancel();
                startTimer(timeLeftAtStart);
            }
        }
        checkAnswer();
    }

    // Method to check the answer.
    private void checkAnswer() {
        if (!(hintTextView.getText().toString().contains("_"))) {
            correct();
        }
        else if (wrongAnswerSubmitCount == 3) {
            wrong();
        }
    }

    // Method to execute when the answer is correct.
    private void correct() {
        resultTextView.setText(R.string.correct);
        resultTextView.setBackgroundColor(Color.parseColor("#328108"));
        resultTextView.setVisibility(View.VISIBLE);
        actionButton.setText(R.string.next);
        // Playing the answer audio.
        answerMediaPlayer = MediaPlayer.create(this, R.raw.correctapplause);
        answerMediaPlayer.start();
        if (switchIsOn) {
            stopTimerSoundPlaying();
            countDownTimer.cancel();
        }
    }

    // Method to execute when the answer is correct.
    private void wrong() {
        resultTextView.setText(R.string.wrong);
        resultTextView.setBackgroundColor(Color.parseColor("#DA0303"));
        resultTextView.setVisibility(View.VISIBLE);
        correctAnswerTextView.setText(car);
        correctAnswerTextView.setBackgroundColor(Color.YELLOW);
        correctAnswerTextView.setVisibility(View.VISIBLE);
        actionButton.setText(R.string.next);
        // Playing the answer audio.
        answerMediaPlayer = MediaPlayer.create(this, R.raw.wrong);
        answerMediaPlayer.start();
    }

    // Method to start the timer.
    private void startTimer(long time) {
        // Playing the timer audio.
        timerMediaPlayer = MediaPlayer.create(HintsActivity.this, R.raw.timersound);
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

    // Method to stop timer audio.
    private void stopTimerSoundPlaying() {
        if (timerMediaPlayer != null) {
            timerMediaPlayer.stop();
            timerMediaPlayer.reset();
            timerMediaPlayer.release();
            timerMediaPlayer = null;
        }
    }

    // Method to stop answers audio.
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

        String hintText = hintTextView.getText().toString();
        outState.putString("hintText", hintText);

        String lives = triesLeftTextView.getText().toString();
        outState.putString("lives", lives);

        outState.putInt("submitCount", wrongAnswerSubmitCount);

        if (switchIsOn) {
            String timerText = timerTextView.getText().toString();
            outState.putString("timerText", timerText);
            outState.putLong("timeLeft", timeLeft);
            outState.putBoolean("switchIsOn", true);
        }
    }

    // Stopping timer audio and answer audio when the activity stops.
    @Override
    protected void onStop() {
        super.onStop();
        stopTimerSoundPlaying();
        stopAnswerSoundPlaying();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}