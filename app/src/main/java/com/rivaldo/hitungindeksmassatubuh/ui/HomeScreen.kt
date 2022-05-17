package com.rivaldo.hitungindeksmassatubuh.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rivaldo.hitungindeksmassatubuh.R
import com.rivaldo.hitungindeksmassatubuh.ui.theme.*
import com.rivaldo.hitungindeksmassatubuh.ui.widgets.*
import com.rivaldo.hitungindeksmassatubuh.util.BmiCalculator

@Composable
fun HomeScreen(
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            Toolbar(
                title = stringResource(R.string.app_name),
//                navigationIcon = {
//                    RoundIconButton(
//                        imageVector = Icons.Outlined.Notifications,
//                        onClick = { navigateTo(Screen.Tips) }
//                    )
//                },
//                actions = {
//                    RoundIconButton(imageVector = Icons.Outlined.Person, onClick = { })
//                }
            )
        },
        content = {
            Content()
        }
    )
}

@Composable
private fun Content() {
    var classification by remember { mutableStateOf("") }
    var calculation by remember { mutableStateOf(0.0f) }
    var progress by remember { mutableStateOf(0.0f) }
    var classificationColor by remember {
        mutableStateOf(Color.White)
    }
    val heightState = remember { mutableStateOf(170) }
    val weightState: MutableState<Int> = remember { mutableStateOf(62) }
    val ageState: MutableState<Int> = remember { mutableStateOf(20) }
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            val maleState = mutableStateOf(true)
            val femaleState = mutableStateOf(false)
            RoundedToggleButton(
                state = maleState,
                text = stringResource(R.string.male),
                onClick = {
                    maleState.value = true
                    femaleState.value = false
                },
                modifier = Modifier
                    .padding(end = 8.dp)
                    .weight(1f)
            )
            RoundedToggleButton(
                state = femaleState,
                text = stringResource(R.string.female),
                onClick = {
                    femaleState.value = true
                    maleState.value = false
                },
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(1f)
            )
        }
        PickerView(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(top = 16.dp),
            heightState = heightState,
            weightState = weightState,
            ageState = ageState
        )

        Box(
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .padding(top = 16.dp, bottom = 8.dp),
        ) {
            Text(
                text = classification,
                color = classificationColor,
                fontSize = 36.sp,
                fontWeight = FontWeight.Medium
            )
        }
        // Circular Progress bar with text in the center Custom Box
        Box(
            modifier = Modifier
                .size(200.dp)
                .fillMaxWidth()
                .align(alignment = Alignment.CenterHorizontally)
        ) {
            Text(
                modifier = Modifier.align(alignment = Alignment.Center),
                text = String.format("%.2f", calculation), // Format Result
                color = Black,
                fontSize = 46.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium
            )
            CircularProgressIndicator(
                progress = progress,
                color = classificationColor,
                strokeWidth = 16.dp,
                modifier = Modifier.fillMaxSize()
            )
        }

        RoundedButton(
            text = stringResource(R.string.calculate),
            onClick = {
                val bmi = BmiCalculator(
                    heightState.value,
                    weightState.value
                )
                calculation = bmi.bmiString.toFloat()
                classification = classifyBMI(bmi.bmiString.toFloat())
                progress = getProgress(bmi.bmiString.toFloat())
                classificationColor = getClassificationColor(bmi.bmiString.toFloat())
//                navigateTo(Screen.Result(bmi))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )
    }
}

@Composable
private fun PickerView(
    modifier: Modifier = Modifier,
    heightState: MutableState<Int>,
    weightState: MutableState<Int>,
    ageState: MutableState<Int>
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        HeightSelector(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 8.dp)
                .fillMaxHeight(0.5f),
            heightState = heightState
        )
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(1f)
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            NumberPicker(
                label = "Berat",
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
                    .fillMaxHeight(1f),
                pickerState = weightState
            )
            NumberPicker(
                label = "Umur",
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
                    .fillMaxHeight(1f),
                pickerState = ageState
            )
        }
    }
}

@Composable
private fun HeightSelector(
    modifier: Modifier = Modifier,
    heightState: MutableState<Int>
) {
    val height = buildAnnotatedString {
        withStyle(
            style = SpanStyle(fontSize = 32.sp)
        ) { append(heightState.value.toString()) }
        append(" cm")
    }
    RoundedCard(modifier = modifier) {
        Column(
            modifier = Modifier
                .wrapContentHeight(),
            //.gravity(Alignment.CenterHorizontally),
//            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.tinggi),
                modifier = ColumnChildModifier,
                style = LabelStyle
            )
            Slider(
                value = heightState.value.toFloat(),
                onValueChange = { heightState.value = it.toInt() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                valueRange = (1f..272f),
                colors = SliderDefaults.colors(
                    activeTrackColor = accentColor
                )
            )
            Text(
                text = height,
                modifier = ColumnChildModifier,
                fontSize = 16.sp,
                color = Color.Black.copy(alpha = 0.8f)
            )
        }
    }
}

@Composable
private fun NumberPicker(
    label: String,
    modifier: Modifier = Modifier,
    pickerState: MutableState<Int>,
    range: IntRange = 1..100
) {
    RoundedCard(modifier = modifier) {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = label,
                style = LabelStyle,
                modifier = ColumnChildModifier
            )
            Text(
                text = pickerState.value.toString(),
                style = ValueStyle,
                modifier = ColumnChildModifier
            )
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = ColumnChildModifier.fillMaxWidth()
            ) {
                RoundIconButton(imageVector = Icons.Default.Remove, onClick = {
                    if (pickerState.value > range.first) {
                        pickerState.value = pickerState.value - 1
                    }
                })
                RoundIconButton(imageVector = Icons.Default.Add, onClick = {
                    if (pickerState.value < range.last) {
                        pickerState.value = pickerState.value + 1
                    }
                })
            }
        }
    }
}

@Preview
@Composable
private fun ScreenPreview() {
    AppTheme {
        HomeScreen()
    }
}

private val LabelStyle = TextStyle(
    color = Color.Black.copy(alpha = 0.6f),
    fontSize = 18.sp
)

private val ValueStyle = TextStyle(
    color = Color.Black.copy(alpha = 0.9f),
    fontSize = 32.sp
)


fun classifyBMI(
    calculation: Float
): String {
    var classification = when {
        calculation < 16 -> "Makan woi, Makan!"
        calculation >= 16 && calculation < 17 -> "Kurus"
        calculation >= 17 && calculation < 18.5 -> "Langsing"
        calculation >= 18.5 && calculation < 25 -> "Sempoerna"
        calculation >= 25 && calculation < 30 -> "Montok"
        calculation >= 30 && calculation < 35 -> "Gemoy"
        calculation >= 35 && calculation < 40 -> "Diet dikit lah say"
        calculation > 40 -> "Olahraga lu, jangan malas!"
        else -> ""
    }
    return classification
}

fun getProgress(
    calculation: Float
): Float {
    return calculation/44
}

fun getClassificationColor(
    calculation: Float
): Color {
    var classificationColor = when {
        calculation < 16 -> color1
        calculation >= 16 && calculation < 17 -> color2
        calculation >= 17 && calculation < 18.5 -> color3
        calculation >= 18.5 && calculation < 25 -> color4
        calculation >= 25 && calculation < 30 -> color5
        calculation >= 30 && calculation < 35 -> color6
        calculation >= 35 && calculation < 40 -> color7
        calculation > 40 -> color8
        else -> Color.Transparent
    }
    return classificationColor
}

private val ColumnChildModifier = Modifier.padding(8.dp)    //.gravity(Alignment.CenterHorizontally)
