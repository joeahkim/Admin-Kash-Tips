package com.joeahkim.admin_kashtips

import android.app.DatePickerDialog
import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.firebase.database.FirebaseDatabase
import java.util.*

@Composable
fun UpdatePrevious() {
    var homeTeam by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var awayTeam by remember { mutableStateOf("") }
    var prediction by remember { mutableStateOf("") }
    var scores by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    // Context for showing the DatePickerDialog
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(value = homeTeam, onValueChange = { homeTeam = it }, label = { Text("Home Team") })

        TextField(
            value = date,
            onValueChange = { },
            label = { Text("Date") },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = {
                    showDatePicker(context) { selectedDate ->
                        date = selectedDate
                    }
                }) {
                    Icon(imageVector = Icons.Default.DateRange, contentDescription = "Select date")
                }
            }
        )

        TextField(value = awayTeam, onValueChange = { awayTeam = it }, label = { Text("Away Team") })
        TextField(value = prediction, onValueChange = { prediction = it }, label = { Text("Prediction") })
        TextField(value = scores, onValueChange = { scores = it }, label = { Text("Scores") })
        TextField(value = result, onValueChange = { result = it }, label = { Text("Result") })

        Button(onClick = {
            addResultToFirebase(Tip2(homeTeam, date, awayTeam, prediction, scores, result))
        },
            modifier = Modifier.fillMaxWidth()) {
            Text("Submit Tip")
        }
    }
}

fun showDatePicker(context: android.content.Context, onDateSelected: (String) -> Unit) {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDay ->
            // Convert the selected date to "MM/dd/yy" format
            val selectedCalendar = Calendar.getInstance()
            selectedCalendar.set(selectedYear, selectedMonth, selectedDay)
            val dateFormat = SimpleDateFormat("MM/dd/yy", Locale.getDefault())
            val formattedDate = dateFormat.format(selectedCalendar.time)
            onDateSelected(formattedDate)
        },
        year, month, day
    )
    datePickerDialog.show()
}

fun addResultToFirebase(tip: Tip2) {
    val database = FirebaseDatabase.getInstance()
    val reference = database.getReference("results")

    val newTipId = reference.push().key ?: return
    reference.child(newTipId).setValue(tip)
}
