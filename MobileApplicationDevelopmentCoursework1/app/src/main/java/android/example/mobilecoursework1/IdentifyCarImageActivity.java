package android.example.mobilecoursework1;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class IdentifyCarImageActivity extends AppCompatActivity {

    private final int[] carsArray = MainActivity.carsArray;
    private CountDownTimer countDownTimer;
    private String car1;
    private String car2;
    private String car3;
    private int carImageId1;
    private int carImageId2;
    private int carImageId3;
    private long timeLeft;
    private boolean switchIsOn;
    private boolean imageIsNotClicked = true;
    private final long timeLeftAtStart = 21000; // 21000 milliseconds [21 seconds].
    private MediaPlayer timerMediaPlayer;
    private MediaPlayer answerMediaPlayer;

    private ImageView carImageView1;
    private ImageView carImageView2;
    private ImageView carImageView3;
    private TextView carModelTextView;
    private TextView resultTextView;
    private TextView timerTextView;
    private Button nextButton;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify_car_image);

        carImageView1 = (ImageView) findViewById(R.id.identify_car_image_imageView1);
        carImageView2 = (ImageView) findViewById(R.id.identify_car_image_imageView2);
        carImageView3 = (ImageView) findViewById(R.id.identify_car_image_imageView3);
        carModelTextView = (TextView) findViewById(R.id.car_model_text);
        resultTextView = (TextView) findViewById(R.id.identify_car_image_result_text);
        timerTextView = (TextView) findViewById(R.id.timerTextView2);
        nextButton = (Button) findViewById(R.id.identify_car_image_next_button);

        nextButton.setVisibility(View.INVISIBLE);
        resultTextView.setVisibility(View.INVISIBLE);

        // Getting the intent to check whether the switch is turned on or not.
        Intent intent = getIntent();
        switchIsOn = intent.getBooleanExtra("timer", false);

        // Restoring the state of the activity when the orientation changes.
        if (savedInstanceState != null) {
            boolean isResultTexViewVisible = savedInstanceState.getBoolean("resultTextView");
            String carModelText = savedInstanceState.getString("carModelText");
            switchIsOn = savedInstanceState.getBoolean("switchIsOn");
            imageIsNotClicked = savedInstanceState.getBoolean("imageClicked");

            carImageId1 = savedInstanceState.getInt("carImage1");
            carImageId2 = savedInstanceState.getInt("carImage2");
            carImageId3 = savedInstanceState.getInt("carImage3");

            car1 = savedInstanceState.getString("displayedCar1");
            car2 = savedInstanceState.getString("displayedCar2");
            car3 = savedInstanceState.getString("displayedCar3");

            carImageView1.setImageResource(carImageId1);
            carImageView2.setImageResource(carImageId2);
            carImageView3.setImageResource(carImageId3);

            carModelTextView.setText(carModelText);

            if (isResultTexViewVisible) {
                nextButton.setVisibility(View.VISIBLE);
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
                }
            }
        }
        else {
            randomImages();
            provideCarModelText();

            if (switchIsOn) {
                startTimer(timeLeftAtStart);
                timerTextView.setVisibility(View.VISIBLE);
            }
            else {
                timerTextView.setVisibility(View.INVISIBLE);
            }
        }
    }

    // Method to execute when user clicks next button.
    public void newRound(View view) {
        randomImages();
        provideCarModelText();
        nextButton.setVisibility(View.INVISIBLE);
        resultTextView.setVisibility(View.INVISIBLE);

        if (switchIsOn) {
            startTimer(timeLeftAtStart);
        }

        stopAnswerSoundPlaying();
        imageIsNotClicked = true;
    }

    // Method to generate random images.
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
        } while (car2.equals(car1)); // To get unique car image.

        do {
            randomInt = random.nextInt(40);
            carImageView3.setImageResource(carsArray[randomInt]);
            // Getting the selected car name.
            car3 = carImageView3.getResources().getString(carsArray[randomInt]);
            car3 = car3.substring(13, car3.length() - 5).toUpperCase();
            // Storing the image id of the selected car.
            carImageId3 = carsArray[randomInt];
        } while (car3.equals(car1) || car3.equals(car2)); // To get unique car image.
    }

    // To provide a car model from randomly selected 3 cars.
    private void provideCarModelText() {
        Random random = new Random();
        int randomNumber = random.nextInt(3);
        String[] randomCarArray = {car1, car2, car3};
        carModelTextView.setText(randomCarArray[randomNumber]);
    }

    // Method to check which image is clicked and to give results accordingly.
    public void checkImage(View view) {
        // To check whether user has already clicked an image, if yes don't need to check the answer.
        if (imageIsNotClicked) {
            if (view.getId() == carImageView1.getId()) {
                checkAnswer(car1);
            } else if (view.getId() == carImageView2.getId()) {
                checkAnswer(car2);
            } else {
                checkAnswer(car3);
            }
            if (switchIsOn) {
                countDownTimer.cancel();
            }
            imageIsNotClicked = false;
            stopTimerSoundPlaying();
        }
    }

    // Method to check the answer.
    private void checkAnswer(String carModel) {
        if (carModelTextView.getText().equals(carModel)) {
            correctAnswer();
        }
        else {
            wrongAnswer();
        }
        nextButton.setVisibility(View.VISIBLE);
    }

    // Method to execute when the answer is correct.
    private void correctAnswer() {
        resultTextView.setText(R.string.correct);
        resultTextView.setBackgroundColor(Color.parseColor("#328108"));
        resultTextView.setVisibility(View.VISIBLE);
        // Playing the answer audio.
        answerMediaPlayer = MediaPlayer.create(this, R.raw.correctapplause);
        answerMediaPlayer.start();
    }

    // Method to execute when the answer is wrong.
    private void wrongAnswer() {
        resultTextView.setText(R.string.wrong);
        resultTextView.setBackgroundColor(Color.parseColor("#DA0303"));
        resultTextView.setVisibility(View.VISIBLE);
        // Playing the answer audio.
        answerMediaPlayer = MediaPlayer.create(this, R.raw.wrong);
        answerMediaPlayer.start();
    }

    // Method to start the timer.
    private void startTimer(long time) {
        // Playing the timer audio.
        timerMediaPlayer = MediaPlayer.create(IdentifyCarImageActivity.this, R.raw.timersound);
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
                imageIsNotClicked = false;
                nextButton.setVisibility(View.VISIBLE);
                stopTimerSoundPlaying();
                wrongAnswer();
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

        String carModelText = carModelTextView.getText().toString();
        outState.putString("carModelText", carModelText);

        outState.putString("displayedCar1", car1);
        outState.putString("displayedCar2", car2);
        outState.putString("displayedCar3", car3);

        outState.putInt("carImage1", carImageId1);
        outState.putInt("carImage2", carImageId2);
        outState.putInt("carImage3", carImageId3);

        outState.putBoolean("imageClicked", imageIsNotClicked);

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