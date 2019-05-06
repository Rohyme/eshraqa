package com.kheer.eshraqa.presentation.ui.screens.mainScreen.splashScreen

import android.animation.Animator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.kheer.eshraqa.R
import com.kheer.eshraqa.presentation.ui.screens.mainScreen.eshraqaMainActivity.MainActivity
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        YoYo.with(Techniques.Shake)
                .withListener(object:Animator.AnimatorListener{
                    override fun onAnimationRepeat(animation: Animator?) {

                    }

                    override fun onAnimationEnd(animation: Animator?) {
                    startActivity(Intent(this@SplashScreen,MainActivity::class.java))
                    }

                    override fun onAnimationCancel(animation: Animator?) {
                    }

                    override fun onAnimationStart(animation: Animator?) {
                    }

                })
                .duration(1000)
                .repeat(3)
                .playOn(logo)

    }
}
