package develop.abdusamid.agecalculator

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import develop.abdusamid.agecalculator.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.ageCalc.setOnClickListener {
            val handler = Handler()
            handler.postDelayed(object : Runnable {
                override fun run() {
                    ageCalc()
                    handler.postDelayed(this, 100)
                }
            }, 100)
        }
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    fun ageCalc() {
        val today = Date()
        val dobs = binding.editTextTextPersonName.text.toString()
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm")
        val dob = sdf.parse(dobs)
        val week = (today.time - dob!!.time) / 604800000
        val days = (today.time - dob.time) / 86400000
        val hours = (today.time - dob.time) % 86400000 / 3600000
        val minutes = (today.time - dob.time) % 86400000 % 3600000 / 60000
        val seconds = (today.time - dob.time) % 86400000 % 3600000 % 60000 / 1000
        val milliseconds = (today.time - dob.time) % 86400000 % 3600000 % 60000 % 1000 / 100
        val dayLayout =
            "You Lived: \nWeeks:   $week \nDays:   $days\nHours:   $hours\nMinutes:   $minutes\nSeconds:   $seconds\nMilliseconds   $milliseconds"
        binding.textView.text = dayLayout

    }

    fun openDatePicker(view: View) {
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                var datepicker = "$dayOfMonth/${month + 1}/$year "
                TimePickerDialog(this, { _, hourOfDay, minute ->
                    datepicker += " $hourOfDay:${minute}"
                    binding.editTextTextPersonName.setText(datepicker)
                }, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), false).show()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }
}