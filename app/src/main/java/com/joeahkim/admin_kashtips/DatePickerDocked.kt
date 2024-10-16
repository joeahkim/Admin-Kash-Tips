package com.joeahkim.admin_kashtips

import android.app.DatePickerDialog
import android.content.Context
import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDocked() {
    var showDatePicker by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf("") }
    val context = LocalContext.current

    OutlinedTextField(
        value = selectedDate,
        onValueChange = { },
        label = { Text("DOB") },
        readOnly = true,
        trailingIcon = {
            IconButton(onClick = { showDatePicker = true }) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Select date"
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
    )

    if (showDatePicker) {
        showDatePickerDialog(context) { date ->
            selectedDate = date
            showDatePicker = false
        }
    }
}

@Composable
fun showDatePickerDialog(context: Context, onDateSelected: (String) -> Unit) {
    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            // Format date as MM/DD/YY
            val formattedDate = SimpleDateFormat("MM/dd/yy", Locale.getDefault()).format(
                GregorianCalendar(year, month, dayOfMonth).time
            )
            onDateSelected(formattedDate)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    // Show the dialog
    datePickerDialog.show()
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("MM/dd/yy", Locale.getDefault())
    return formatter.format(Date(millis))
}
