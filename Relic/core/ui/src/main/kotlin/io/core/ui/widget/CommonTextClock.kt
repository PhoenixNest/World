package io.core.ui.widget

import android.os.CountDownTimer
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import io.common.util.LogUtil
import io.common.util.TimeUtil
import io.core.ui.theme.RelicFontFamily.googleSans
import java.time.LocalDateTime

@Composable
@Preview(showBackground = true)
fun CommonTextClock(
    isShowCalendar: Boolean = true,
    isShowAMPM: Boolean = true,
    calendarTextColor: Color = MaterialTheme.colorScheme.onSurface,
    clockBlockContainerColor: Color = MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.3F),
    clockTextColor: Color = MaterialTheme.colorScheme.onSurface,
    modifier: Modifier = Modifier
) {

    var year by remember {
        mutableIntStateOf(LocalDateTime.now().year)
    }

    var month by remember {
        mutableIntStateOf(LocalDateTime.now().monthValue)
    }

    var dayOfMonth by remember {
        mutableIntStateOf(LocalDateTime.now().dayOfMonth)
    }

    var hour by remember {
        mutableIntStateOf(LocalDateTime.now().hour)
    }

    var minute by remember {
        mutableIntStateOf(LocalDateTime.now().minute)
    }

    var second by remember {
        mutableIntStateOf(LocalDateTime.now().second)
    }

    var isBlink by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        startClock(
            onTick = { model ->
                LogUtil.d("CommonTextClock", "[onTick - Calendar] $year : $month : $dayOfMonth")
                LogUtil.d("CommonTextClock", "[onTick - Clock] $hour : $minute : $second")
                year = model.year
                month = model.month
                dayOfMonth = model.dayOfMonth
                hour = model.hour
                minute = model.minute
                second = model.second
                isBlink = (second % 2 == 0)
            }
        )
    }

    DisposableEffect(Unit) {
        onDispose {
            LogUtil.d("CommonTextClock", "[onDispose] Free memory.")
            cancelClock()
        }
    }

    ConstraintLayout {

        val (calendar, clock) = createRefs()

        if (isShowCalendar) {
            CommonCalendarPanel(
                year = year,
                month = month,
                dayOfMonth = dayOfMonth,
                textColor = calendarTextColor,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .constrainAs(
                        ref = calendar,
                        constrainBlock = {
                            start.linkTo(clock.start)
                            end.linkTo(clock.end)
                            bottom.linkTo(clock.top)
                        }
                    )
            )
        }
        CommonTextClockPanel(
            hour = hour,
            minute = minute,
            second = second,
            isBlink = isBlink,
            isShowAMPM = isShowAMPM,
            containerColor = clockBlockContainerColor,
            contentColor = clockTextColor,
            modifier = Modifier.constrainAs(
                ref = clock,
                constrainBlock = {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(calendar.bottom)
                }
            )
        )
    }
}

@Composable
private fun CommonCalendarPanel(
    year: Int,
    month: Int,
    dayOfMonth: Int,
    textColor: Color = MaterialTheme.colorScheme.primary,
    modifier: Modifier = Modifier
) {
    Text(
        text = "${TimeUtil.getCurrentFormattedMonth(month)} ${dayOfMonth}, $year",
        modifier = modifier,
        color = textColor,
        fontFamily = googleSans,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.titleLarge
    )
}

@Composable
private fun CommonTextClockPanel(
    hour: Int,
    minute: Int,
    second: Int,
    isBlink: Boolean,
    isShowAMPM: Boolean,
    containerColor: Color = MaterialTheme.colorScheme.primaryContainer,
    contentColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(
            space = 6.dp,
            alignment = Alignment.CenterHorizontally
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CommonTextClockBlock(
            clockString = clockValueStringConvertor(value = hour),
            containerColor = containerColor,
            contentColor = contentColor
        )
        CommonTextClockDivider(isBlink = isBlink)
        CommonTextClockBlock(
            clockString = clockValueStringConvertor(value = minute),
            containerColor = containerColor,
            contentColor = contentColor
        )
        CommonTextClockDivider(isBlink = isBlink)
        CommonTextClockBlock(
            clockString = clockValueStringConvertor(value = second),
            containerColor = containerColor,
            contentColor = contentColor
        )
        if (isShowAMPM) {
            CommonTextClockDivider(isBlink = isBlink)
            CommonTextClockBlock(
                clockString = clockAMPMConvertor(hour = hour),
                containerColor = containerColor,
                contentColor = contentColor
            )
        }
    }
}

@Composable
private fun CommonTextClockBlock(
    clockString: String,
    containerColor: Color = MaterialTheme.colorScheme.primaryContainer,
    contentColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    blockSize: Dp = 52.dp
) {
    Surface(
        modifier = Modifier.size(blockSize),
        color = containerColor,
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = clockString,
                modifier = Modifier.padding(8.dp),
                color = contentColor,
                fontFamily = googleSans,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
private fun CommonTextClockDivider(isBlink: Boolean) {
    val textContent = if (isBlink) {
        ":"
    } else {
        " "
    }

    Text(
        text = textContent,
        modifier = Modifier.width(8.dp),
        fontWeight = FontWeight.Bold,
        fontFamily = googleSans,
        textAlign = TextAlign.Center
    )
}

/* ======================== Tools ======================== */

private const val millsInFuture = Long.MAX_VALUE
private const val countdownInterval = 1 * 1000L
private var countDownTimer: CountDownTimer? = null

data class CommonClockDateModel(
    val year: Int = LocalDateTime.now().year,
    val month: Int = LocalDateTime.now().monthValue,
    val dayOfMonth: Int = LocalDateTime.now().dayOfMonth,
    val hour: Int = LocalDateTime.now().hour,
    val minute: Int = LocalDateTime.now().minute,
    val second: Int = LocalDateTime.now().second
)

private fun startClock(onTick: (model: CommonClockDateModel) -> Unit) {
    countDownTimer = object : CountDownTimer(
        /* millisInFuture = */ millsInFuture,
        /* countDownInterval = */ countdownInterval
    ) {
        override fun onTick(millisUntilFinished: Long) {
            val year = LocalDateTime.now().year
            val month = LocalDateTime.now().monthValue
            val dayOfMonth = LocalDateTime.now().dayOfMonth
            val hour = LocalDateTime.now().hour
            val minute = LocalDateTime.now().minute
            val second = LocalDateTime.now().second

            onTick.invoke(
                CommonClockDateModel(
                    year = year,
                    month = month,
                    dayOfMonth = dayOfMonth,
                    hour = hour,
                    minute = minute,
                    second = second
                )
            )
        }

        override fun onFinish() {
            //
        }
    }

    countDownTimer?.start()
}

private fun cancelClock() {
    countDownTimer?.cancel()
    countDownTimer = null
}

private fun clockAMPMConvertor(hour: Int): String {
    return if (hour < 12) {
        "AM"
    } else {
        "PM"
    }
}

private fun clockValueStringConvertor(value: Int): String {
    return if (value < 10) {
        "0$value"
    } else {
        value.toString()
    }
}