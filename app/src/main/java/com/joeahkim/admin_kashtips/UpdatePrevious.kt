package com.joeahkim.admin_kashtips

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.database.FirebaseDatabase

@Composable
fun UpdatePrevious() {
    var homeTeam by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var awayTeam by remember { mutableStateOf("") }
    var prediction by remember { mutableStateOf("") }
    var scores by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
       TextField(value = homeTeam, onValueChange = { homeTeam = it }, label = { Text("Home Team") })
        TextField(value = date, onValueChange = { date = it }, label = { Text("Date") })
        TextField(value = awayTeam, onValueChange = { awayTeam = it }, label = { Text("Away Team") })
        TextField(value = prediction, onValueChange = { prediction = it }, label = { Text("Prediction") })
        TextField(value = scores, onValueChange = { scores = it }, label = { Text("Scores") })
        TextField(value = result, onValueChange = { result = it }, label = { Text("Result") })

        Button(onClick = {
            addResultToFirebase(Tip2(homeTeam, date, awayTeam, prediction, scores, result))
        }) {
            Text("Submit Tip")
        }
    }
}

fun addResultToFirebase(tip: Tip2) {
    val database = FirebaseDatabase.getInstance()
    val reference = database.getReference("results")

    val newTipId = reference.push().key ?: return
    reference.child(newTipId).setValue(tip)
}
