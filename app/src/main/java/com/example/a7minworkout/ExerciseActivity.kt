package com.example.a7minworkout

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var toolbar_exercise_activity: Toolbar
    private var restTimer:CountDownTimer?= null
    private var restProgress=0
    private var restTimerDuration: Long=10
    private lateinit var progressBar:ProgressBar
    private lateinit var tv_timer:TextView
    private lateinit var ll_restView:LinearLayout

    private var exerciseTimer:CountDownTimer?= null
    private var exerciseProgress=0
    private var exerciseTimerDuration: Long=30
    private lateinit var progressBarExercise:ProgressBar
    private lateinit var tv_timer_exercise:TextView
    private lateinit var ll_exerciseView:LinearLayout

    private var exerciseList:ArrayList<ExerciseModel>?=null
    private var currentExercisePosition=-1

    private lateinit var ivImage:ImageView
    private lateinit var tvExerciseName:TextView

    private lateinit var tvUpcomingExercise: TextView

    private var tts: TextToSpeech?= null

    private var player: MediaPlayer?=null

    private var exerciseAdapter: ExerciseStatusAdapter?= null
    private lateinit var rvExerciseStatus: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)


        //initialise widgets
        toolbar_exercise_activity=findViewById(R.id.toolbar_exercise_activity)
        progressBar=findViewById(R.id.progressBar)
        tv_timer=findViewById(R.id.tv_timer)
        ll_restView=findViewById(R.id.ll_RestView)

        progressBarExercise=findViewById(R.id.progressBarExercise)
        tv_timer_exercise=findViewById(R.id.tv_timer_exercise)
        ll_exerciseView=findViewById(R.id.ll_ExerciseView)

        exerciseList=Constants.defaultExerciseList()

        ivImage=findViewById(R.id.ivImage)
        tvExerciseName=findViewById(R.id.tvExerciseName)

        tvUpcomingExercise=findViewById(R.id.tvUpcomingExercise)

        tts= TextToSpeech(this, this)

        rvExerciseStatus=findViewById(R.id.rvExerciseStatus)

        setSupportActionBar(toolbar_exercise_activity)
        val actionBar= supportActionBar
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        toolbar_exercise_activity.setNavigationOnClickListener {
            customDialogForBackButton()
        }

        setUpRestView()
        setUpExerciseStatusRecyclerView()

    }

    override fun onDestroy() {

        if(restTimer!=null){
            restTimer!!.cancel()
            restProgress=0
        }
        if(exerciseTimer!=null){
            exerciseTimer!!.cancel()
            exerciseProgress=0
        }

        if(tts!=null)
        {
            tts!!.stop()
            tts!!.shutdown()
        }

        if(player!=null){
            player!!.stop()
        }

        super.onDestroy()
    }

    private fun setRestProgressBar(){

        ll_restView.visibility=View.VISIBLE
        progressBar.progress=restProgress
        restTimer=object: CountDownTimer(restTimerDuration*1000,1000){
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                progressBar.progress=10-restProgress
                tv_timer.text = (10-restProgress).toString()

            }

            override fun onFinish() {
                currentExercisePosition++
                ll_restView.visibility= View.GONE
                exerciseList!![currentExercisePosition].setIsSelected(true)
                exerciseAdapter!!.notifyDataSetChanged()
                setUpExerciseView()
            }
        }.start()
    }


    private fun setUpRestView(){


        try {
            player = MediaPlayer.create(applicationContext, R.raw.press_start)
            player!!.isLooping = false
            player!!.start()
        }catch (e: Exception){e.printStackTrace()}

        if(restTimer!=null){
            restTimer!!.cancel()
            restProgress=0
        }

        tvUpcomingExercise.text=exerciseList!![currentExercisePosition+1].getName()
        setRestProgressBar()

    }


    private fun setExerciseProgressBar(){

        ll_exerciseView.visibility=View.VISIBLE
        progressBar.progress=exerciseProgress
        exerciseTimer=object: CountDownTimer(exerciseTimerDuration*1000,1000){
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                progressBarExercise.progress=30-exerciseProgress
                tv_timer_exercise.text = (30-exerciseProgress).toString()
            }
            override fun onFinish() {
                if(currentExercisePosition<exerciseList?.size!!-1){
                    ll_exerciseView.visibility=View.GONE

                    exerciseList!![currentExercisePosition].setIsSelected(false)
                    exerciseList!![currentExercisePosition].setIsCompleted(true)
                    exerciseAdapter!!.notifyDataSetChanged()

                    setUpRestView()
                }else{
                    finish()
                    val intent= Intent(this@ExerciseActivity, FinishActivity::class.java)
                    startActivity(intent)
                }
            }
        }.start()
    }

    private fun setUpExerciseView(){
        if(exerciseTimer!=null){
            exerciseTimer!!.cancel()
            exerciseProgress=0
        }

        speakOut(exerciseList!![currentExercisePosition].getName())

        setExerciseProgressBar()

        ivImage.setImageResource(exerciseList!![currentExercisePosition].getImage())
        tvExerciseName.text=exerciseList!![currentExercisePosition].getName()
    }

    override fun onInit(status: Int) {

        if(status==TextToSpeech.SUCCESS){

            val result=tts!!.setLanguage(Locale.US)
            if(result==TextToSpeech.LANG_MISSING_DATA || result==TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS", "Lanuage not Supported")
            }
        }else{

            Log.e("TTS", "Initialisation Failed")
        }
    }

    private fun speakOut(text:String){

        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }


    private fun setUpExerciseStatusRecyclerView(){
        rvExerciseStatus.layoutManager=LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        exerciseAdapter= ExerciseStatusAdapter(exerciseList!!, this)
        rvExerciseStatus.adapter=exerciseAdapter
    }

    private fun customDialogForBackButton(){

        val customDialog=Dialog(this)
        customDialog.setContentView(R.layout.dialog_custom_back_confirmation)

        customDialog.findViewById<Button>(R.id.btnYes).setOnClickListener {
            finish()
            customDialog.dismiss()
        }

        customDialog.findViewById<Button>(R.id.btnNo).setOnClickListener{
            customDialog.dismiss()
        }
        customDialog.show()
    }

}