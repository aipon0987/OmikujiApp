package com.example.omikujiapp;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import javax.xml.transform.Result;

public class ResultActivity extends AppCompatActivity {

    private static final int ERROR_CODE = -1;

    private static final String KEY_OMIKUJI_NUMBER = "key_omikuji_namber";

    public static Intent newIntent(Context context, int number) {
        Intent intent = new Intent(context, ResultActivity.class);
        intent.putExtra(KEY_OMIKUJI_NUMBER, number);
        return intent;
    }

    private ImageView omikuji;
    private TextView omikuji2;
    private TextView omikujiback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        omikuji = findViewById(R.id.result_image);
        omikuji2 = findViewById(R.id.result_text);
        omikujiback = findViewById(R.id.back_buttom_text);

        omikuji.setVisibility(View.VISIBLE);
        omikuji2.setVisibility(View.GONE);

        int number = getIntent().getIntExtra(KEY_OMIKUJI_NUMBER, ERROR_CODE);
        resultSettings(number);

        omikujiback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });

        startFadeAnimation();

    }

    private void startFadeAnimation(){
        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(omikuji, "alpha", 1f, 0f);
        fadeOut.setDuration(2000);

        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(omikuji2,"alpha", 0f, 1f);
        fadeIn.setDuration(2000);

        fadeOut.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(@NonNull Animator animator) {

            }

            @Override
            public void onAnimationEnd(@NonNull Animator animator) {
                omikuji.setVisibility(View.GONE);

                omikuji2.setVisibility(View.VISIBLE);

                Log.d(ResultActivity.class.getSimpleName(), "onAnimationEnd!!!");
                fadeIn.start();

            }

            @Override
            public void onAnimationCancel(@NonNull Animator animator) {

            }

            @Override
            public void onAnimationRepeat(@NonNull Animator animator) {

            }
        });

        fadeOut.start();
    }


    private void resultSettings(int number){
        int imageResID = ERROR_CODE;
        int stringResId = ERROR_CODE;
        switch(number){
            case 0: //大吉
                imageResID = R.drawable.great_blessing;
                stringResId = R.string.great_blessing;
                break;
            case  1: //中吉
                imageResID = R.drawable.middle_blessing;
                stringResId = R.string.middle_blessing;
                break;
            case 2: //小吉
                imageResID = R.drawable.small_blessing;
                stringResId = R.string.small_blessing;
                break;
            case 3: //吉
                imageResID = R.drawable.blessing;
                stringResId = R.string.blessing;
                break;
            case 4: //末吉
                imageResID = R.drawable.uncertain_luck;
                stringResId = R.string.uncertain_luck;
                break;
            case 5: //凶
                imageResID = R.drawable.curse;
                stringResId = R.string.curse;
                break;
            case 6: //大凶
                imageResID = R.drawable.great_curse;
                stringResId = R.string.great_curse;
                break;
            default: //エラーコード
                throw new RuntimeException("error: number is not found.");

        }

        omikuji.setImageResource(imageResID);
        omikuji2.setText(stringResId);
    }
}