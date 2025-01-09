package com.example.dgenlibrary

import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp



@Composable
fun <T> DgenDropdownSlideList(
    modifier: Modifier = Modifier,
    preSelectedList: SnapshotStateList<Int>? = null,
    items: List<T>,
    maxSelections: Int = 2,
    itemContent: @Composable (T, Int, Boolean, Boolean, () -> Unit) -> Unit,
    screenContent: @Composable (SnapshotStateList<Int>, (Int?) -> Unit, () -> Unit) -> Unit
) {
    var drawerState by remember { mutableStateOf(false) }
    val translateContent: Dp by animateDpAsState(if (drawerState) 200.dp else 0.dp, label = "translateContent")
    val translateList: Dp by animateDpAsState(if (drawerState) 0.dp else 200.dp, label = "translateList")


    var selectedIndicesList = remember { mutableStateListOf<Int>() }
    if(preSelectedList != null){
        selectedIndicesList = preSelectedList
    }

    var activeSlot by remember { mutableStateOf<Int?>(null) }

    var dropdownModifier = when(drawerState){
        true -> {
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(Color.Blue)
                .graphicsLayer {
                    //this.translationX = -200.dp.toPx() // open
                    this.translationX = -translateContent.toPx() //closed
                }
                .clickable {
                    if (drawerState) {
                        drawerState = false
                    }
                }
        }
        false -> {
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(Color.Blue)
                .graphicsLayer {
                    //this.translationX = -200.dp.toPx() // open
                    this.translationX = -translateContent.toPx() //closed
                }

        }
    }

    Box(contentAlignment = Alignment.TopEnd) {
        Column(
            modifier = dropdownModifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            screenContent(selectedIndicesList, {
                activeSlot = it
                Log.d("SLOT screenContent","$activeSlot - $it ")
            }, {
                drawerState = !drawerState
            })

        }


        LazyColumn(
            modifier = Modifier
                .graphicsLayer { this.translationX = translateList.toPx() }
                .width(200.dp)
                .fillMaxHeight()
                .background(Color.Red)
        ) {
            itemsIndexed(items) { index, item ->
                val slot = selectedIndicesList.indexOf(index) // -1 if not selected
                val isSelected = slot != -1
                val isActive = activeSlot != null && slot == (activeSlot!! - 1)
                //og.d("SLOT","$activeSlot - $slot - $isActive")

                itemContent(
                    item,
                    slot,
                    isSelected,
                    isActive
                ) {
                    if (isSelected) {
                        if (selectedIndicesList.size == maxSelections) {
//                            selectedIndicesList.remove(index)
//                            selectedIndicesList.add(0, index)

                            if (selectedIndicesList.contains(index)){
                                val firstIndex = selectedIndicesList.indexOf(index)
                                val secondIndex = activeSlot!!-1
                                if (firstIndex in selectedIndicesList.indices && secondIndex in selectedIndicesList.indices) {
                                    val temp = selectedIndicesList[firstIndex]
                                    selectedIndicesList[firstIndex] = selectedIndicesList[secondIndex]
                                    selectedIndicesList[secondIndex] = temp
                                }
                            }else {
                                Log.d("SLOT","Replace ${activeSlot!! - 1} - $index")
                                selectedIndicesList[activeSlot!!-1] = index
                            }

                        } else {
                            Log.d("SLOT","Remove ${activeSlot!! - 1} - $index")

                            selectedIndicesList.remove(index)
                        }
                    } else {
                        if (selectedIndicesList.size < maxSelections) {
                            Log.d("SLOT","Add ${activeSlot!! - 1} - $index")

                            selectedIndicesList.add(index)
                        } else {


                            Log.d("SLOT","Non Select ${activeSlot!! - 1} - $index")

                            selectedIndicesList[activeSlot!!-1] = index
                        }
                    }
                }
            }
        }
    }
}

// Example usage
data class ExampleItem(val name: String, val description: String, val value: String)

@Composable
fun ExampleDropdownSlideList() {
    val items = (1..20).map {
        ExampleItem(
            name = "name $it",
            description = "description $it",
            value = "value $it"
        )
    }

    val preselectedIndex = remember { mutableStateListOf<Int>() }
    preselectedIndex.add(1)

    DgenDropdownSlideList(
        items = items,
        //preSelectedList = preselectedIndex,
        maxSelections = 2,
        itemContent = { item, slot, isSelected, isActive, onClick ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClick() }
                    .background(
                        if (isSelected) Color.Green else Color.Gray
                    )
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(text = item.name, color = if (isSelected) Color.Black else Color.White)
                    Text(text = item.description, color = if (isSelected) Color.Black else Color.White)

                    if (isActive){
                        Text(text = "is Active", color =  Color.Black )
                    }
                    if (slot != -1) {
                        Text(text = "Slot: ${slot + 1}", color = if (isSelected) Color.Black else Color.White)
                    }
                }
                Text(text = item.value, color = if (isSelected) Color.Black else Color.White)
            }
        }
    ) { selectedIndicesList, activeSlot, toggleDrawer ->

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Selected Items1:")
            val firstSlot = selectedIndicesList.getOrNull(0)
            val value1 = if (firstSlot != null) { items[firstSlot].name } else { "Default 1" }
            Text("Slot ${1}: Item ${value1}")

            Button(onClick = {
                activeSlot(1)
                toggleDrawer() // Drawer öffnen oder schließen
            }) {
                Text("Toggle Drawer Intern 1")
            }

            val secondSlot = selectedIndicesList.getOrNull(1)
            val value2 = if (secondSlot != null) { items[secondSlot].name } else { "Default 2" }
            Text("Slot ${1}: Item ${value2}")
            Button(onClick = {
                activeSlot(2)
                toggleDrawer() // Drawer öffnen oder schließen
            }) {
                Text("Toggle Drawer Intern 2")
            }
        }
    }
}
