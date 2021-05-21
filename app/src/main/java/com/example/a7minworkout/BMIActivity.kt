package com.example.a7minworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import com.google.android.material.textfield.TextInputLayout
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {


    private lateinit var llUsUnitHeight: LinearLayout
    private lateinit var llDisplayBmiResuls: LinearLayout
    private lateinit var toolbar_bmi_activity: Toolbar
    private lateinit var tilMetricWeight: TextInputLayout
    private lateinit var tilMetricHeight: TextInputLayout
    private lateinit var etMetricUnitsHeight: EditText
    private lateinit var etMetricUnitsWeight: EditText
    private lateinit var tvYourBMI: TextView
    private lateinit var tvBMIValue: TextView
    private lateinit var tvBMIType: TextView
    private lateinit var tvBMIDescription: TextView
    private lateinit var btnCalculate: Button
    private lateinit var etUsUnitsWeight: EditText
    private lateinit var etUsUnitsHeightFeet: EditText
    private lateinit var etUsUnitsHeightInch: EditText
    private lateinit var tilUsWeight: TextInputLayout
    private lateinit var tilUsHeightFeet: TextInputLayout
    private lateinit var tilUsHeightInch: TextInputLayout
    private lateinit var rgUnits: RadioGroup
    private lateinit var rbUsUnits: RadioButton
    private lateinit var rbMetricUnits: RadioButton

    val METRICS_UNIT_VIEW= "METRIC_UNIT_VIEW"
    val US_UNITS_VIEW= "US_UNIT_VIEW"

    var currentVisibleView: String= METRICS_UNIT_VIEW


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmiactivity)

        //initialise stuff
        toolbar_bmi_activity=findViewById(R.id.toolbar_bmi_activity)

        etMetricUnitsHeight=findViewById(R.id.etMetricUnitHeight)
        etMetricUnitsWeight=findViewById(R.id.etMetricUnitWeight)

        tvYourBMI=findViewById(R.id.tvYourBMI)
        tvBMIValue=findViewById(R.id.tvBMIValue)
        tvBMIType=findViewById(R.id.tvBMIType)
        tvBMIDescription=findViewById(R.id.tvBMIDescription)

        btnCalculate=findViewById(R.id.btnCalculateUnits)

        etUsUnitsHeightFeet=findViewById(R.id.etUsUnitHeightFeet)
        etUsUnitsHeightInch=findViewById(R.id.etUsUnitHeightInch)
        etUsUnitsWeight=findViewById(R.id.etUsUnitWeight)

        rgUnits=findViewById(R.id.rgUnits)

        rbMetricUnits=findViewById(R.id.rbMetricUnits)
        rbUsUnits=findViewById(R.id.rbUsUnits)

        tilMetricHeight=findViewById(R.id.tilMetricUnitHeight)
        tilMetricWeight=findViewById(R.id.tilMetricUnitWeight)
        tilUsWeight=findViewById(R.id.tilUSUnitWeight)
        tilUsHeightFeet=findViewById(R.id.tilUsUnitHeightFeet)
        tilUsHeightInch=findViewById(R.id.tilUsUnitHeightInch)

        llUsUnitHeight=findViewById(R.id.llUsUnitsHeight)
        llDisplayBmiResuls=findViewById(R.id.llDiplayBMIResult)


        setSupportActionBar(toolbar_bmi_activity)
        val actionBar=supportActionBar

        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title="Calculate BMI"
        }

        toolbar_bmi_activity.setNavigationOnClickListener {
            onBackPressed()
        }

        btnCalculate.setOnClickListener {
            if(currentVisibleView.equals(METRICS_UNIT_VIEW)){
                if(validateMetricUnits()){
                    val heightvalue: Float=etMetricUnitsHeight.text.toString().toFloat()/100
                    val weightvalue: Float=etMetricUnitsWeight.text.toString().toFloat()

                    val bmi=weightvalue/(heightvalue*heightvalue)

                    displayBMIResult(bmi)
                }else{
                    Toast.makeText(this@BMIActivity, "Enter Details", Toast.LENGTH_SHORT).show()
                }
            } else{
                if(validateUsUnits()){
                    val usUnitHeightFeet: String= etUsUnitsHeightFeet.text.toString()
                    val usUnitHeightInch: String= etUsUnitsHeightInch.text.toString()
                    val usUnitWeight: Float= etUsUnitsWeight.text.toString().toFloat()

                    val heightValue=usUnitHeightInch.toFloat()+ usUnitHeightFeet.toFloat()*12

                    val bmi= 703* (usUnitWeight/ (heightValue*heightValue))
                    displayBMIResult(bmi)


                }else{
                    Toast.makeText(this@BMIActivity, "Enter Details", Toast.LENGTH_SHORT).show()
                }
            }
        }

        makeVisibleMetricUnitsView()
        rgUnits.setOnCheckedChangeListener { group, checkedId ->

            if(checkedId==R.id.rbMetricUnits){
                makeVisibleMetricUnitsView()
            }else{
                makeVisibleUsUnitsView()
            }
        }
    }


    private fun displayBMIResult(bmi: Float) {

        val bmiLabel: String
        val bmiDescription: String

        if (bmi.compareTo(15f) <= 0) {
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0
        ) {
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops!You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0
        ) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0
        ) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        } else if (bmi.compareTo(25f)> 0 && bmi.compareTo(30f) <= 0) {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0
        ) {
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0
        ) {
            bmiLabel = "Obese Class || (Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        } else {
            bmiLabel = "Obese Class ||| (Very Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }

        llDisplayBmiResuls.visibility=View.VISIBLE/*
        tvYourBMI.visibility = View.VISIBLE
        tvBMIValue.visibility = View.VISIBLE
        tvBMIType.visibility = View.VISIBLE
        tvBMIDescription.visibility = View.VISIBLE*/

        // This is used to round the result value to 2 decimal values after "."
        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        tvBMIValue.text = bmiValue // Value is set to TextView
        tvBMIType.text = bmiLabel // Label is set to TextView
        tvBMIDescription.text = bmiDescription // Description is set to TextView
    }

    private fun validateMetricUnits(): Boolean{
        var isvalid=true

        if(etMetricUnitsWeight.text.toString().isEmpty()){
            isvalid=false
        }else if(etMetricUnitsHeight.text.toString().isEmpty()){
            isvalid=false
        }

            return isvalid
    }

    private fun validateUsUnits(): Boolean{
        var isvalid = true
        if(etUsUnitsHeightFeet.text.toString().isEmpty()){ isvalid=false}
        else if(etUsUnitsHeightInch.text.toString().isEmpty()) { isvalid=false}
        else if(etUsUnitsWeight.text.toString().isEmpty()){ isvalid=false}

        return isvalid
    }

    private fun makeVisibleMetricUnitsView(){
        currentVisibleView=METRICS_UNIT_VIEW
        tilMetricWeight.visibility=View.VISIBLE
        tilMetricHeight.visibility=View.VISIBLE

        etMetricUnitsHeight.text!!.clear()
        etMetricUnitsWeight.text!!.clear()

        tilUsWeight.visibility=View.GONE
        llUsUnitHeight.visibility=View.GONE

        llDisplayBmiResuls.visibility=View.GONE

    }

    private fun makeVisibleUsUnitsView(){
        currentVisibleView=US_UNITS_VIEW
        tilMetricWeight.visibility=View.GONE
        tilMetricHeight.visibility=View.GONE

        etUsUnitsWeight.text!!.clear()
        etUsUnitsHeightInch.text!!.clear()
        etUsUnitsHeightFeet.text!!.clear()


        tilUsWeight.visibility=View.VISIBLE
        llUsUnitHeight.visibility=View.VISIBLE

        llDisplayBmiResuls.visibility=View.GONE

    }
}