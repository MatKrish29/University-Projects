package android.example.mobilecoursework1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class AdvancedLevelActivity extends AppCompatActivity {

    private final int[] carsArray = MainActivity.carsArray;
    private int scoreCount;
    private int submitCount;
    private boolean switchIsOn;
    private long timeLeft;
    private String car1;
    private String car2;
    private String car3;
    private int carImageId1;
    private int carImageId2;
    private int carImageId3;
    private CountDownTimer countDownTimer;
    private final long timeLeftAtStart = 21000; // 21000 milliseconds [21 seconds].
    private MediaPlayer timerMediaPlayer;
    private MediaPlayer answerMediaPlayer;

    private TextView correctAnswerTextView1;
    private TextView correctAnswerTextView2;
    private TextView correctAnswerTextView3;
    private TextView resultTextView;
    private TextView scoreTextView;
    private TextView triesLeftTextView;
    private TextView timerTextView;
    private EditText userAnswerEditText1;
    private EditText userAnswerEditText2;
    private EditText userAnswerEditText3;
    private ImageView carImageView1;
    private ImageView carImageView2;
    private ImageView carImageView3;
    private Button actionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_level);

        correctAnswerTextView1 = (TextView) findViewById(R.id.advanced_correct_answer1);
        correctAnswerTextView2 = (TextView) findViewById(R.id.advanced_correct_answer2);
        correctAnswerTextView3 = (TextView) findViewById(R.id.advanced_correct_answer3);
        timerTextView = (TextView) findViewById(R.id.timer_text_view3);
        resultTextView = (TextView) findViewById(R.id.advanced_result_text);
        scoreTextView = (TextView) findViewById(R.id.score_text);
        triesLeftTextView = (TextView) findViewById(R.id.advanced_level_tries_textView);
        userAnswerEditText1 = (EditText) findViewById(R.id.edit_text1);
        userAnswerEditText2 = (EditText) findViewById(R.id.edit_text2);
        userAnswerEditText3 = (EditText) findViewById(R.id.edit_text3);
        carImageView1 = (ImageView) findViewById(R.id.advanced_imageView1);
        carImageView2 = (ImageView) findViewById(R.id.advanced_imageView2);
        carImageView3 = (ImageView) findViewById(R.id.advanced_imageView3);
        actionButton = (Button) findViewById(R.id.advanced_submit_button);

        correctAnswerTextView1.setVisibility(View.INVISIBLE);
        correctAnswerTextView2.setVisibility(View.INVISIBLE);
        correctAnswerTextView3.setVisibility(View.INVISIBLE);
        resultTextView.setVisibility(View.INVISIBLE);

        ViewGroup.LayoutParams layoutParams = triesLeftTextView.getLayoutParams();
        int orientation = getResources().getConfiguration().orientation;

        // Getting the intent to check whether the switch is turned on or not.
        Intent intent = getIntent();
        switchIsOn = intent.getBooleanExtra("timer", false);

        // Restoring the state of the activity when the orientation changes.
        if (savedInstanceState != null) {
            boolean isResultTexViewVisible = savedInstanceState.getBoolean("resultTextView");
            boolean isAnswerTextView1Visible = savedInstanceState.getBoolean("answerTextView1");
            boolean isAnswerTextView2Visible = savedInstanceState.getBoolean("answerTextView2");
            boolean isAnswerTextView3Visible = savedInstanceState.getBoolean("answerTextView3");
            boolean isEditText1Enabled = savedInstanceState.getBoolean("editText1Enabled");
            boolean isEditText2Enabled = savedInstanceState.getBoolean("editText2Enabled");
            boolean isEditText3Enabled = savedInstanceState.getBoolean("editText3Enabled");
            String score = savedInstanceState.getString("score");
            String lives = savedInstanceState.getString("lives");
            String editText1 = savedInstanceState.getString("editText1");
            String editText2 = savedInstanceState.getString("editText2");
            String editText3 = savedInstanceState.getString("editText3");

            switchIsOn = savedInstanceState.getBoolean("switchIsOn");

            carImageId1 = savedInstanceState.getInt("carImage1");
            carImageId2 = savedInstanceState.getInt("carImage2");
            carImageId3 = savedInstanceState.getInt("carImage3");

            car1 = savedInstanceState.getString("displayedCar1");
            car2 = savedInstanceState.getString("displayedCar2");
            car3 = savedInstanceState.getString("displayedCar3");

            carImageView1.setImageResource(carImageId1);
            carImageView2.setImageResource(carImageId2);
            carImageView3.setImageResource(carImageId3);

            scoreTextView.setText(score);
            triesLeftTextView.setText(lives);

            scoreCount = savedInstanceState.getInt("scoreCount");
            submitCount = savedInstanceState.getInt("submitCount");

            userAnswerEditText1.setText(editText1);
            userAnswerEditText2.setText(editText2);
            userAnswerEditText3.setText(editText3);

            if (isResultTexViewVisible) {
                actionButton.setText(R.string.next);
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
                    if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                        layoutParams.width = 1000;
                    }
                    else {
                        layoutParams.width = 1710;
                    }
                    triesLeftTextView.setLayoutParams(layoutParams);
                }
                if (resultText.equals(getString(R.string.correct))) {
                    resultTextView.setBackgroundColor(Color.parseColor("#328108"));
                }
                else {
                    resultTextView.setBackgroundColor(Color.parseColor("#DA0303"));
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
                    if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                        layoutParams.width = 1000;
                    }
                    else {
                        layoutParams.width = 1710;
                    }
                    triesLeftTextView.setLayoutParams(layoutParams);
                }
            }
            if (isAnswerTextView1Visible) {
                String answerText = savedInstanceState.getString("answerText1");
                correctAnswerTextView1.setText(answerText);
                correctAnswerTextView1.setBackgroundColor(Color.YELLOW);
                correctAnswerTextView1.setVisibility(View.VISIBLE);
            }
            else {
                correctAnswerTextView1.setVisibility(View.INVISIBLE);
            }
            if (isAnswerTextView2Visible) {
                String answerText = savedInstanceState.getString("answerText2");
                correctAnswerTextView2.setText(answerText);
                correctAnswerTextView2.setBackgroundColor(Color.YELLOW);
                correctAnswerTextView2.setVisibility(View.VISIBLE);
            }
            else {
                correctAnswerTextView2.setVisibility(View.INVISIBLE);
            }
            if (isAnswerTextView3Visible) {
                String answerText = savedInstanceState.getString("answerText3");
                correctAnswerTextView3.setText(answerText);
                correctAnswerTextView3.setBackgroundColor(Color.YELLOW);
                correctAnswerTextView3.setVisibility(View.VISIBLE);
            }
            else {
                correctAnswerTextView3.setVisibility(View.INVISIBLE);
            }

            if (submitCount > 0) {
                if (isEditText1Enabled) {
                    userAnswerEditText1.setBackgroundColor(Color.parseColor("#DA0303"));
                    userAnswerEditText1.setTextColor(Color.WHITE);
                } else {
                    userAnswerEditText1.setBackgroundColor(Color.parseColor("#328108"));
                    userAnswerEditText1.setTextColor(Color.WHITE);
                    userAnswerEditText1.setEnabled(false);
                }
                if (isEditText2Enabled) {
                    userAnswerEditText2.setBackgroundColor(Color.parseColor("#DA0303"));
                    userAnswerEditText2.setTextColor(Color.WHITE);
                } else {
                    userAnswerEditText2.setBackgroundColor(Color.parseColor("#328108"));
                    userAnswerEditText2.setTextColor(Color.WHITE);
                    userAnswerEditText2.setEnabled(false);
                }
                if (isEditText3Enabled) {
                    userAnswerEditText3.setBackgroundColor(Color.parseColor("#DA0303"));
                    userAnswerEditText3.setTextColor(Color.WHITE);
                } else {
                    userAnswerEditText3.setBackgroundColor(Color.parseColor("#328108"));
                    userAnswerEditText3.setTextColor(Color.WHITE);
                    userAnswerEditText3.setEnabled(false);
                }
            }
        }
        else {
            randomImages();

            if (switchIsOn) {
                startTimer(timeLeftAtStart);
                timerTextView.setVisibility(View.VISIBLE);
            }
            else {
                timerTextView.setVisibility(View.INVISIBLE);
                if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                    layoutParams.width = 1000;
                }
                else {
                    layoutParams.width = 1710;
                }
                triesLeftTextView.setLayoutParams(layoutParams);
            }
        }
    }

    // Method to execute when user goes to the next round.
    private void nextRound() {
        correctAnswerTextView1.setVisibility(View.INVISIBLE);
        correctAnswerTextView2.setVisibility(View.INVISIBLE);
        correctAnswerTextView3.setVisibility(View.INVISIBLE);
        resultTextView.setVisibility(View.INVISIBLE);

        userAnswerEditText1.setBackgroundColor(Color.TRANSPARENT);
        userAnswerEditText2.setBackgroundColor(Color.TRANSPARENT);
        userAnswerEditText3.setBackgroundColor(Color.TRANSPARENT);

        userAnswerEditText1.setTextColor(Color.BLACK);
        userAnswerEditText2.setTextColor(Color.BLACK);
        userAnswerEditText3.setTextColor(Color.BLACK);

        userAnswerEditText1.setEnabled(true);
        userAnswerEditText2.setEnabled(true);
        userAnswerEditText3.setEnabled(true);

        userAnswerEditText1.setText("");
        userAnswerEditText2.setText("");
        userAnswerEditText3.setText("");

        submitCount = 0;
        scoreCount = 0;
        scoreTextView.setText(R.string.initial_score);
        triesLeftTextView.setText(R.string.initial_lives);

        actionButton.setText(R.string.submit);
        stopAnswerSoundPlaying();
        randomImages();

        if (switchIsOn) {
            startTimer(timeLeftAtStart);
        }
    }

    // Method to generate random car images.
    private void randomImages() {
        Random random = new Random();
        int randomInt = random.nextInt(40);
        carImageView1.setImageResource(carsArray[randomInt]);
        // Getting the selected car name.
        car1 = carImageView1.getResources().getString(carsArray[randomInt]);
        car1 = car1.substring(13, car1.length() - 5).toUpperCase();
        // Storing the image id of the selected car.
        carImageId1 = carsArray[randomInt];

        do {
            randomInt = random.nextInt(40);
            carImageView2.setImageResource(carsArray[randomInt]);
            // Getting the selected car name.
            car2 = carImageView2.getResources().getString(carsArray[randomInt]);
            car2 = car2.substring(13, car2.length() - 5).toUpperCase();
            // Storing the image id of the selected car.
            carImageId2 = carsArray[randomInt];
        } while (car2.equals(car1)); // To get unique car make image.

        do {
            randomInt = random.nextInt(40);
            carImageView3.setImageResource(carsArray[randomInt]);
            // Getting the selected car name.
            car3 = carImageView3.getResources().getString(carsArray[randomInt]);
            car3 = car3.substring(13, car3.length() - 5).toUpperCase();
            // Storing the image id of the selected car.
            carImageId3 = carsArray[randomInt];
        } while (car3.equals(car1) || car3.equals(car2)); // To get unique car make image.
    }

    // Method to execute when user clicks action button.
    public void performAction(View view) {
        // Checking the label on the action button and performing the action accordingly.
        if (actionButton.getText().toString().equals(getString(R.string.submit))) {
            showResults();
        }
        else {
            nextRound();
        }
    }

    // Method to show results.
    private void showResults() {
        submitCount++;
        scoreCount = 0;

        String userAnswer1 = userAnswerEditText1.getText().toString().toUpperCase();
        String userAnswer2 = userAnswerEditText2.getText().toString().toUpperCase();
        String userAnswer3 = userAnswerEditText3.getText().toString().toUpperCase();

        checkAnswers(car1, userAnswer1, userAnswerEditText1, correctAnswerTextView1);
        checkAnswers(car2, userAnswer2, userAnswerEditText2, correctAnswerTextView2);
        checkAnswers(car3, userAnswer3, userAnswerEditText3, correctAnswerTextView3);

        if (switchIsOn) {
            if (submitCount == 3 || scoreCount == 3) {
                countDownTimer.cancel();
            }
            else {
                countDownTimer.cancel();
                startTimer(timeLeftAtStart);
            }
        }

        // Displaying the chances left.
        String livesText = "LIVES : " + (3-submitCount);
        triesLeftTextView.setText(livesText);
    }

    // Method to check the answers provided by the user.
    private void checkAnswers(String carModel, String userAnswer, EditText userAnswerEditText,
                              TextView correctAnswerTextView) {
        if (userAnswer.equals(carModel)) {
            correctAnswer(userAnswerEditText);
        }
        else {
            wrongAnswer(carModel, userAnswerEditText, correctAnswerTextView);
        }
        stopTimerSoundPlaying();
    }

    // Method to execute when the answer is correct.
    private void correctAnswer(EditText userAnswerEditText) {
        userAnswerEditText.setBackgroundColor(Color.parseColor("#328108"));
        userAnswerEditText.setTextColor(Color.WHITE);
        userAnswerEditText.setEnabled(false);

        scoreCount++;
        String scoreText = "SCORE : " + scoreCount;
        scoreTextView.setText(scoreText);
        // Actions to perform when user gets all correct.
        if (scoreCount == 3) {
            resultTextView.setText(R.string.correct);
            resultTextView.setBackgroundColor(Color.parseColor("#328108"));
            resultTextView.setVisibility(View.VISIBLE);
            actionButton.setText(R.string.next);
            // Playing the answer audio.
            answerMediaPlayer = MediaPlayer.create(this, R.raw.correctapplause);
            answerMediaPlayer.start();
        }
    }

    // Method to execute when the answer is wrong.
    private void wrongAnswer(String carModel, EditText userAnswerEditText,
                             TextView correctAnswerTextView) {
        userAnswerEditText.setBackgroundColor(Color.parseColor("#DA0303"));
        userAnswerEditText.setTextColor(Color.WHITE);

        // Actions to perform when user loses all the 3 chances.
        if (submitCount == 3) {
            resultTextView.setText(R.string.wrong);
            resultTextView.setBackgroundColor(Color.parseColor("#DA0303"));
            resultTextView.setVisibility(View.VISIBLE);
            actionButton.setText(R.string.next);
            correctAnswerTextView.setText(carModel);
            correctAnswerTextView.setBackgroundColor(Color.YELLOW);
            correctAnswerTextView.setVisibility(View.VISIBLE);
            // Playing the answer audio.
            answerMediaPlayer = MediaPlayer.create(this, R.raw.wrong);
            answerMediaPlayer.start();
        }
    }

    // Method to start the timer.
    private void startTimer(long time) {
        // Playing the timer audio.
        timerMediaPlayer = MediaPlayer.create(AdvancedLevelActivity.this, R.raw.timersound);
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

    // Method to stop the timer audio.
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
        if (correctAnswerTextView1.getVisibility() == View.VISIBLE) {
            outState.putBoolean("answerTextView1", true);
            String answer = correctAnswerTextView1.getText().toString();
            outState.putString("answerText1", answer);
        }
        if (correctAnswerTextView2.getVisibility() == View.VISIBLE) {
            outState.putBoolean("answerTextView2", true);
            String answer = correctAnswerTextView2.getText().toString();
            outState.putString("answerText2", answer);

        }
        if (correctAnswerTextView3.getVisibility() == View.VISIBLE) {
            outState.putBoolean("answerTextView3", true);
            String answer = correctAnswerTextView3.getText().toString();
            outState.putString("answerText3", answer);
        }
        if (userAnswerEditText1.isEnabled()) {
            outState.putBoolean("editText1Enabled", true);
        }
        if (userAnswerEditText2.isEnabled()) {
            outState.putBoolean("editText2Enabled", true);
        }
        if (userAnswerEditText3.isEnabled()) {
            outState.putBoolean("editText3Enabled", true);
        }

        String editText1 = userAnswerEditText1.getText().toString();
        outState.putString("editText1", editText1);

        String editText2 = userAnswerEditText2.getText().toString();
        outState.putString("editText2", editText2);

        String editText3 = userAnswerEditText3.getText().toString();
        outState.putString("editText3", editText3);

        String lives = triesLeftTextView.getText().toString();
        outState.putString("lives", lives);

        String score = scoreTextView.getText().toString();
        outState.putString("score", score);

        outState.putInt("submitCount", submitCount);
        outState.putInt("scoreCount", scoreCount);

        outState.putString("displayedCar1", car1);
        outState.putString("displayedCar2", car2);
        outState.putString("displayedCar3", car3);

        outState.putInt("carImage1", carImageId1);
        outState.putInt("carImage2", carImageId2);
        outState.putInt("carImage3", carImageId3);

        if (switchIsOn) {
            String timerText = timerTextView.getText().toString();
            outState.putString("timerText", timerText);
            outState.putLong("timeLeft", timeLeft);
            outState.putBoolean("switchIsOn", true);
        }
    }

    // Stopping the timer and audios when the activity stops.
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