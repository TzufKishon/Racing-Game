package com.example.tzufapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.widget.Toast;
import com.google.android.material.button.MaterialButton;
import java.util.Random;
import java.util.TimerTask;
import java.util.Timer;

public class Racing_Game extends AppCompatActivity {
    private static int[][] ghostsMatrix = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
    private static int[] pacmansArr = {0, 1, 0};
    private int ifRandomCalled = 0;
    private int lives = 3;
    private boolean gameOverFlag = false;
    private AppCompatImageView[] ghosts;
    private AppCompatImageView[] hearts;
    private AppCompatImageView[] pacmans;
    private AppCompatImageView ghost_image1;
    private AppCompatImageView game_IMG_heart1;
    private AppCompatImageView game_IMG_heart2;
    private AppCompatImageView game_IMG_heart3;
    private AppCompatImageView ghost_image2;
    private AppCompatImageView ghost_image3;
    private AppCompatImageView ghost_image4;
    private AppCompatImageView ghost_image5;
    private AppCompatImageView ghost_image6;
    private AppCompatImageView ghost_image7;
    private AppCompatImageView ghost_image8;
    private AppCompatImageView ghost_image9;
    private AppCompatImageView ghost_image10;
    private AppCompatImageView ghost_image11;
    private AppCompatImageView ghost_image12;
    private AppCompatImageView ghost_image13;
    private AppCompatImageView ghost_image14;
    private AppCompatImageView ghost_image15;
    private AppCompatImageView ghost_image16;
    private AppCompatImageView ghost_image17;
    private AppCompatImageView ghost_image18;
    private AppCompatImageView ghost_image19;
    private AppCompatImageView ghost_image20;
    private AppCompatImageView ghost_image21;
    private AppCompatImageView pacman_image1;
    private AppCompatImageView pacman_image2;
    private AppCompatImageView pacman_image3;
    private MaterialButton game_BTN_left;
    private MaterialButton game_BTN_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_racing_game);
        findViews();
        initViews();
        updateGhosts();
    }

    private void updateGhosts() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                updateGhostsMatrix();
            }
        };
        timer.scheduleAtFixedRate(task, 0, 1000);
    }

    private void updateGhostsMatrix() {
        updateGhostsMatrixPositions();
        randomGhostMatrixPosition();
        updateGhostsPositions();
        checkIfCrash();
        checkIfGameOver();
    }

    private void updateGhostsMatrixPositions() {
        for (int i = 6; i > 0; i--)
            for (int j = 0; j < 3; j++) {
                ghostsMatrix[i][j] = ghostsMatrix[i - 1][j];
            }
    }

    private void randomGhostMatrixPosition() {
        for (int i = 0; i < 3; i++) {
            ghostsMatrix[0][i] = 0;
        }
        if (ifRandomCalled == 0) {
            ifRandomCalled = 1;
            Random rand = new Random();
            int randomNum = rand.nextInt(3);
            ghostsMatrix[0][randomNum] = 1;
        } else
            ifRandomCalled = 0;
    }

    private void updateGhostsPositions() {
        for (int i = 0; i < 21; i++)
            ghosts[i].setVisibility(View.INVISIBLE);

        for (int i = 0; i < 7; i++)
            for (int j = 0; j < 3; j++) {
                if (ghostsMatrix[i][j] == 1) {
                    final int ghostIndex = 3 * i + j;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ghosts[ghostIndex].setVisibility(View.VISIBLE);
                        }
                    });
                }
            }
    }

    private void clicked(int side) {
        int index = getPacmanIndex();
        if (side == 0) {
            if (index > 0)
                updatePacmansUI(side, index);
        } else {
            if (index < 2)
                updatePacmansUI(side, index);
        }
        checkIfCrash();
        checkIfGameOver();
    }

    private void updatePacmansUI(int side, int index) {
        if (side == 0) {
            pacmansArr[index] = 0;
            pacmans[index].setVisibility(View.INVISIBLE);
            pacmansArr[index - 1] = 1;
            pacmans[index - 1].setVisibility(View.VISIBLE);
        } else {
            pacmansArr[index] = 0;
            pacmans[index].setVisibility(View.INVISIBLE);
            pacmansArr[index + 1] = 1;
            pacmans[index + 1].setVisibility(View.VISIBLE);
        }
    }

    private void checkIfCrash() {
        for (int i = 0; i < ghostsMatrix[6].length; i++) {
            if (ghostsMatrix[6][i] == 1 && pacmansArr[i] == 1) {
                if (lives > 1) {
                    crash();
                    lives--;
                    refreshHeartsUI();
                } else {
                    lives--;
                    refreshHeartsUI();
                }
            }
        }
    }

    private void checkIfGameOver() {
        if (lives == 0 && !gameOverFlag) {
            gameOverFlag = true;
            gameOver();
            refreshHeartsUI();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            gameOverFlag = false;
                            lives = 3;
                            refreshHeartsUI();
                        }
                    }, 1500);
                }
            });
        }
    }

    private int getPacmanIndex() {
        for (int i = 0; i < pacmans.length; i++) {
            if (pacmansArr[i] == 1) {
                return i;
            }
        }
        return -1;
    }

    private void crash() {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null) {
            vibrator.vibrate(500);
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(Racing_Game.this, "Crash", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void gameOver() {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null) {
            vibrator.vibrate(500);
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(Racing_Game.this, "Game Over", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void refreshHeartsUI() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < hearts.length; i++) {
                    hearts[i].setVisibility(View.INVISIBLE);
                }
                for (int i = 0; i < lives; i++) {
                    hearts[i].setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void initViews() {
        game_BTN_left.setOnClickListener(v -> clicked(0));
        game_BTN_right.setOnClickListener(v -> clicked(1));
    }

    private void findViews() {
        ghost_image1 = findViewById(R.id.ghost_image1);
        ghost_image2 = findViewById(R.id.ghost_image2);
        ghost_image3 = findViewById(R.id.ghost_image3);
        ghost_image4 = findViewById(R.id.ghost_image4);
        ghost_image5 = findViewById(R.id.ghost_image5);
        ghost_image6 = findViewById(R.id.ghost_image6);
        ghost_image7 = findViewById(R.id.ghost_image7);
        ghost_image8 = findViewById(R.id.ghost_image8);
        ghost_image9 = findViewById(R.id.ghost_image9);
        ghost_image10 = findViewById(R.id.ghost_image10);
        ghost_image11 = findViewById(R.id.ghost_image11);
        ghost_image12 = findViewById(R.id.ghost_image12);
        ghost_image13 = findViewById(R.id.ghost_image13);
        ghost_image14 = findViewById(R.id.ghost_image14);
        ghost_image15 = findViewById(R.id.ghost_image15);
        ghost_image16 = findViewById(R.id.ghost_image16);
        ghost_image17 = findViewById(R.id.ghost_image17);
        ghost_image18 = findViewById(R.id.ghost_image18);
        ghost_image19 = findViewById(R.id.ghost_image19);
        ghost_image20 = findViewById(R.id.ghost_image20);
        ghost_image21 = findViewById(R.id.ghost_image21);
        pacman_image1 = findViewById(R.id.pacman_image1);
        pacman_image2 = findViewById(R.id.pacman_image2);
        pacman_image3 = findViewById(R.id.pacman_image3);
        game_IMG_heart1 = findViewById(R.id.game_IMG_heart1);
        game_IMG_heart2 = findViewById(R.id.game_IMG_heart2);
        game_IMG_heart3 = findViewById(R.id.game_IMG_heart3);
        game_BTN_left = findViewById(R.id.game_BTN_left);
        game_BTN_right = findViewById(R.id.game_BTN_right);

        hearts = new AppCompatImageView[]{
                game_IMG_heart1,
                game_IMG_heart2,
                game_IMG_heart3,
        };
        pacmans = new AppCompatImageView[]{
                pacman_image1,
                pacman_image2,
                pacman_image3,
        };
        ghosts = new AppCompatImageView[]{
                ghost_image1,
                ghost_image2,
                ghost_image3,
                ghost_image4,
                ghost_image5,
                ghost_image6,
                ghost_image7,
                ghost_image8,
                ghost_image9,
                ghost_image10,
                ghost_image11,
                ghost_image12,
                ghost_image13,
                ghost_image14,
                ghost_image15,
                ghost_image16,
                ghost_image17,
                ghost_image18,
                ghost_image19,
                ghost_image20,
                ghost_image21,
        };
    }
}