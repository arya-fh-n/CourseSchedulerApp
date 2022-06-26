package com.dicoding.courseschedule.ui.add

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.ui.list.ListActivity
import com.dicoding.courseschedule.util.TimePickerFragment
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*

class AddCourseActivity : AppCompatActivity(), TimePickerFragment.DialogTimeListener {

    private var startTime: String = ""
    private var endTime: String = ""
    private lateinit var addCourseViewModel: AddCourseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)

        supportActionBar?.title = getString(R.string.add_course)

        val factory = AddCourseViewModelFactory.createFactory(this)
        addCourseViewModel = ViewModelProvider(this, factory).get(AddCourseViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_insert -> {
                val courseName = findViewById<TextInputEditText>(R.id.add_ed_course_name).text.toString()
                val day = findViewById<Spinner>(R.id.add_day_spinner).selectedItemPosition
                val lecturer = findViewById<TextInputEditText>(R.id.add_ed_lecturer).text.toString()
                val note = findViewById<TextInputEditText>(R.id.add_ed_note).text.toString()

                addCourseViewModel.insertCourse(
                    courseName,
                    day,
                    startTime,
                    endTime,
                    lecturer,
                    note
                )
                startActivity(Intent(this@AddCourseActivity, ListActivity::class.java))
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun showTimePicker(view: View) {
        val timePickerFragment = TimePickerFragment()
        when (view.id) {
            R.id.add_ib_start_time -> {
                timePickerFragment.show(supportFragmentManager, "startTimePicker")
            }
            R.id.add_ib_end_time -> {
                timePickerFragment.show(supportFragmentManager, "endTimePicker")
            }
            else -> {
            }
        }
    }

    override fun onDialogTimeSet(tag: String?, hour: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)

        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        when (tag) {
            "startTimePicker" -> {
                startTime = timeFormat.format(calendar.time)
                findViewById<TextView>(R.id.add_tv_start_time).text = startTime
            }
            "endTimePicker" -> {
                endTime = timeFormat.format(calendar.time)
                findViewById<TextView>(R.id.add_tv_end_time).text = endTime
            }
            else -> {
                Log.e("TimePicker Tag Error:", tag.toString())
            }
        }
    }
}