package com.biyonikhamza.catchtheminnos

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import com.biyonikhamza.catchtheminnos.databinding.ActivityMainBinding
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.yield
import kotlin.random.Random
import kotlin.random.nextInt

@SuppressLint("SetTextI18n")
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    private lateinit var sharedPreferences: SharedPreferences

    var runnable : Runnable = Runnable {}
    var handler : Handler = Handler(Looper.getMainLooper())

    var score = 0

    var random = Random

    var myCatArrayList = ArrayList<ImageView>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        startAlertdialog()

        with(binding){
            myCatArrayList.add(cat1)
            myCatArrayList.add(cat2)
            myCatArrayList.add(cat3)
            myCatArrayList.add(cat4)
            myCatArrayList.add(cat5)
            myCatArrayList.add(cat6)
            myCatArrayList.add(cat7)
            myCatArrayList.add(cat8)
            myCatArrayList.add(cat9)
            myCatArrayList.add(cat10)
            myCatArrayList.add(cat11)
            myCatArrayList.add(cat12)
        }
        sharedPreferences = this.getSharedPreferences("com.biyonikhamza.catchtheminnos" , Context.MODE_PRIVATE)

        val myLastScore = sharedPreferences.getInt("Score" , 0)
        binding.saveText.setText("Last Score : $myLastScore")
        hideImages()
    }

    fun hideImages(){
        for(imageViews in myCatArrayList){
            imageViews.visibility = View.INVISIBLE
        }
    }
    fun whenClickCat(view : View){
        score += 1
        binding.scoreText.setText("Score : $score")
        sharedPreferences.edit().putInt("Score" , score).apply()
    }
    fun showCat(){
        runnable = Runnable {
            hideImages()
            var randomInt = random.nextInt(12)
            myCatArrayList[randomInt].visibility = View.VISIBLE
            handler.postDelayed(runnable , 400)

        }
        handler.post(runnable)
    }

    fun countDownTimer(){
        object : CountDownTimer(10000 , 1000){
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                binding.timeText.setText("Time : ${+ millisUntilFinished / 1000}" )
            }

            override fun onFinish() {
                binding.timeText.setText("Bittii!!! :)")
                handler.removeCallbacks(runnable)
                hideImages()
                finishAlertdialog()
            }

        }.start()
    }



    fun finishAlertdialog(){
        val alertDialog = AlertDialog.Builder(this@MainActivity)
            alertDialog.setTitle("Nasıldı Eğlendin mi ?")
            alertDialog.setMessage("Tekrar yakalayıp sevmek ister misin ?")
            alertDialog.setCancelable(false)
            alertDialog.setPositiveButton(" Hadi başlayalım") { dialog, which ->
                // Oyun tekrar başlatılacak
                val mainIntent = intent
                finish()
                startActivity(mainIntent)

            }
            alertDialog.setNegativeButton("Başka zamana InşaAllah") { dialog , which ->
                // Uygulama kapatılacak || Finish edilecek
                finish()
            }
        alertDialog.show()
    }

    fun startAlertdialog(){
        val alertDialog = AlertDialog.Builder(this@MainActivity)
            alertDialog.setTitle("Minnoşu Yakalamak")
            alertDialog.setMessage("Minnoşları yakalayıp sevmek için güzel bir gün değil mi ?")
            alertDialog.setCancelable(false)
            alertDialog.setPositiveButton(" Hadi başlayalım") { dialog, which ->
                // Oyun tekrar başlatılacak
                countDownTimer()
                showCat()

            }
            alertDialog.setNegativeButton("Başka zamana InşaAllah") { dialog, which ->
                // Uygulama kapatılacak || Finish edilecek
                finish()
            }
        alertDialog.show()
    }

}