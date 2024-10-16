package com.joeahkim.admin_kashtips

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.database.FirebaseDatabase

@Composable
fun AdminScreen() {
    var country by remember { mutableStateOf("") }
    var league by remember { mutableStateOf("") }
    var homeTeam by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var awayTeam by remember { mutableStateOf("") }
    var prediction by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TextField(value = country, onValueChange = { country = it }, label = { Text("Country") })
        TextField(value = league, onValueChange = { league = it }, label = { Text("League") })
        TextField(value = homeTeam, onValueChange = { homeTeam = it }, label = { Text("Home Team") })
        TextField(value = time, onValueChange = { time = it }, label = { Text("Time") })
        TextField(value = awayTeam, onValueChange = { awayTeam = it }, label = { Text("Away Team") })
        TextField(value = prediction, onValueChange = { prediction = it }, label = { Text("Prediction") })

        Button(onClick = {
            addTipToFirebase(Tip(country, league, homeTeam, time, awayTeam, prediction))
        },
            modifier = Modifier.fillMaxWidth()) {
            Text("Submit Tip")
        }
    }
}

fun addTipToFirebase(tip: Tip) {
    val database = FirebaseDatabase.getInstance()
    val reference = database.getReference("todaysTips")

    val newTipId = reference.push().key ?: return
    reference.child(newTipId).setValue(tip)
}
