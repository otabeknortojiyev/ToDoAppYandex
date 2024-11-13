package uz.yayra.otabek.todoappyandex.screens.addTodo

import android.app.DatePickerDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.orbitmvi.orbit.compose.collectAsState
import uz.yayra.otabek.common.TodoEntity
import uz.yayra.otabek.presenter.addTodo.AddTaskContract
import uz.yayra.otabek.presenter.addTodo.AddTaskViewModel
import uz.yayra.otabek.todoappyandex.R
import uz.yayra.otabek.todoappyandex.screens.activity.darkTheme
import uz.yayra.otabek.todoappyandex.utils.AppTextField
import uz.yayra.otabek.todoappyandex.utils.formatLongToDateString
import java.util.Calendar

/**
Developed by Otabek Nortojiyev
 **/

class AddTaskScreen(val data: TodoEntity? = null) : Screen {
    @Composable
    override fun Content() {
        val viewModel: AddTaskContract.ViewModel = getViewModel<AddTaskViewModel>()
        val uiState = viewModel.collectAsState()
        AddTaskScreenContent(uiState, viewModel::onEventDispatcher, data)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddTaskScreenContent(
    uiState: State<AddTaskContract.UiState>, onEventDispatcher: (AddTaskContract.Intent) -> Unit, data: TodoEntity?
) {
    LaunchedEffect(Unit) {
        if (data != null) {
            onEventDispatcher(AddTaskContract.Intent.Init(data.deadLine))
        }
    }
    val focusRequester = FocusRequester()
    val text = remember {
        mutableStateOf(
            if (data != null) {
                data.text
            } else {
                ""
            }
        )
    }
    var selectedIconIndex = remember {
        mutableIntStateOf(
            if (data != null) {
                if (data.importance == "basic") {
                    2
                } else if (data.importance == "low") {
                    1
                } else {
                    3
                }
            } else {
                2
            }
        )
    }
    val isShow = remember { mutableStateOf(false) }
    if (isShow.value) {
        val context = LocalContext.current
        val calendar = remember { Calendar.getInstance() }
        val dateState = remember { mutableLongStateOf(0L) }
        DatePickerDialog(
            context, { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                dateState.longValue = calendar.timeInMillis
                isShow.value = false
                onEventDispatcher(AddTaskContract.Intent.Date(dateState.longValue))
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("") }, navigationIcon = {
                IconButton({
                    onEventDispatcher(AddTaskContract.Intent.Back)
                }) {
                    Image(
                        painter = painterResource(R.drawable.close), contentDescription = stringResource(R.string.add_task_screen_exit)
                    )
                }
            }, actions = {
                TextButton({
                    if (text.value.isNotEmpty()) {
                        if (data != null) {
                            onEventDispatcher(
                                AddTaskContract.Intent.Update(
                                    TodoEntity(
                                        data.id, text.value, if (selectedIconIndex.intValue == 2) {
                                            "basic"
                                        } else if (selectedIconIndex.intValue == 1) {
                                            "low"
                                        } else {
                                            "important"
                                        }, false, 0L, 0L, uiState.value.date, isOffline = true, isInsert = false, isUpdate = true, isDelete = false
                                    )
                                )
                            )
                        } else {
                            onEventDispatcher(
                                AddTaskContract.Intent.Save(
                                    TodoEntity(
                                        0, text.value, if (selectedIconIndex.intValue == 2) {
                                            "basic"
                                        } else if (selectedIconIndex.intValue == 1) {
                                            "low"
                                        } else {
                                            "important"
                                        }, false, 0L, 0L, uiState.value.date, isOffline = true, isInsert = true, isUpdate = false, isDelete = false
                                    )
                                )
                            )
                        }
                    }
                }) {
                    Text(
                        text = stringResource(R.string.add_task_screen_save), modifier = Modifier.padding(end = 16.dp), color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }, colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.onTertiaryContainer))
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(MaterialTheme.colorScheme.onTertiaryContainer)
                .verticalScroll(rememberScrollState())
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .heightIn(min = 100.dp)
                        .clickable {
                            focusRequester.requestFocus()
                        }, elevation = CardDefaults.cardElevation(2.dp), colors = CardColors(
                        contentColor = MaterialTheme.colorScheme.background,
                        containerColor = MaterialTheme.colorScheme.background,
                        disabledContentColor = MaterialTheme.colorScheme.background,
                        disabledContainerColor = MaterialTheme.colorScheme.background
                    )
                ) {
                    AppTextField(
                        value = text.value,
                        onValueChange = {
                            text.value = it
                        },
                        modifier = Modifier.focusRequester(focusRequester),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text, imeAction = ImeAction.Unspecified),
                        hint = stringResource(R.string.add_task_screen_hint)
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 20.dp, bottom = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.add_task_screen_importance),
                        fontFamily = FontFamily(Font(R.font.roboto_regular)),
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.primaryContainer
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                            .clip(shape = RoundedCornerShape(10.dp))
                            .background(color = MaterialTheme.colorScheme.onPrimary)
                    ) {
                        Image(painter = painterResource(R.drawable.priority_low),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(top = 2.dp, bottom = 2.dp, start = 2.dp)
                                .clip(shape = RoundedCornerShape(10.dp))
                                .background(
                                    color = if (selectedIconIndex.intValue == 1) {
                                        MaterialTheme.colorScheme.onBackground
                                    } else {
                                        Color.Transparent
                                    }
                                )
                                .clickable(
                                    enabled = selectedIconIndex.intValue != 1
                                ) {
                                    selectedIconIndex.intValue = 1
                                }
                                .padding(vertical = 10.dp, horizontal = 16.dp),
                            contentScale = ContentScale.Crop)
                        Box(
                            modifier = Modifier
                                .size(width = 1.dp, height = 24.dp)
                                .background(
                                    color = if (selectedIconIndex.intValue == 3 || selectedIconIndex.intValue == -1) {
                                        MaterialTheme.colorScheme.tertiary
                                    } else {
                                        Color.Transparent
                                    }
                                )
                        )
                        Text(text = stringResource(R.string.add_task_screen_no), modifier = Modifier
                            .padding(vertical = 2.dp)
                            .clip(shape = RoundedCornerShape(10.dp))
                            .background(
                                color = if (selectedIconIndex.intValue == 2) {
                                    MaterialTheme.colorScheme.onBackground
                                } else {
                                    Color.Transparent
                                }
                            )
                            .clickable(
                                enabled = selectedIconIndex.intValue != 2
                            ) {
                                selectedIconIndex.intValue = 2
                            }
                            .padding(vertical = 10.dp, horizontal = 16.dp), color = MaterialTheme.colorScheme.primaryContainer)
                        Box(
                            modifier = Modifier
                                .size(width = 1.dp, height = 24.dp)
                                .background(
                                    color = if (selectedIconIndex.intValue == 1 || selectedIconIndex.intValue == -1) {
                                        MaterialTheme.colorScheme.tertiary
                                    } else {
                                        Color.Transparent
                                    }
                                )
                        )
                        Image(painter = painterResource(R.drawable.priority_high),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(top = 2.dp, bottom = 2.dp, end = 2.dp)
                                .clip(shape = RoundedCornerShape(10.dp))
                                .background(
                                    color = if (selectedIconIndex.intValue == 3) {
                                        MaterialTheme.colorScheme.onBackground
                                    } else {
                                        Color.Transparent
                                    }
                                )
                                .clickable(
                                    enabled = selectedIconIndex.intValue != 3
                                ) {
                                    selectedIconIndex.intValue = 3
                                }
                                .padding(vertical = 10.dp, horizontal = 16.dp))
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(0.5.dp)
                        .background(color = MaterialTheme.colorScheme.primary)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 20.dp, bottom = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    val date = uiState.value.date
                    Column {
                        Text(
                            text = stringResource(R.string.add_task_screen_until),
                            fontFamily = FontFamily(Font(R.font.roboto_regular)),
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.primaryContainer
                        )
                        if (date != 0L) {
                            Text(
                                text = formatLongToDateString(date), fontFamily = FontFamily(Font(R.font.roboto_regular)), fontSize = 14.sp, color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }
                    }
                    val checked = remember {
                        mutableStateOf(
                            if (data != null) {
                                data.deadLine != 0L
                            } else {
                                false
                            }
                        )
                    }
                    Switch(
                        checked = checked.value, onCheckedChange = {
                            checked.value = it
                            if (it) {
                                isShow.value = true
                            } else {
                                isShow.value = false
                                onEventDispatcher(AddTaskContract.Intent.Date(0))
                            }
                        }, colors = SwitchColors(
                            checkedThumbColor = MaterialTheme.colorScheme.tertiaryContainer,
                            checkedTrackColor = MaterialTheme.colorScheme.secondaryContainer,
                            checkedBorderColor = Color.Transparent,
                            checkedIconColor = Color.Transparent,
                            uncheckedThumbColor = MaterialTheme.colorScheme.tertiaryContainer,
                            uncheckedTrackColor = MaterialTheme.colorScheme.onSurface,
                            uncheckedBorderColor = Color.Transparent,
                            uncheckedIconColor = Color.Transparent,
                            disabledCheckedThumbColor = Color.Transparent,
                            disabledCheckedTrackColor = Color.Transparent,
                            disabledCheckedBorderColor = Color.Transparent,
                            disabledCheckedIconColor = Color.Transparent,
                            disabledUncheckedThumbColor = Color.Transparent,
                            disabledUncheckedTrackColor = Color.Transparent,
                            disabledUncheckedBorderColor = Color.Transparent,
                            disabledUncheckedIconColor = Color.Transparent
                        )
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(0.5.dp)
                        .background(color = MaterialTheme.colorScheme.primary)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 20.dp, bottom = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = stringResource(R.string.add_task_screen_change_theme),
                            fontFamily = FontFamily(Font(R.font.roboto_regular)),
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.primaryContainer
                        )
                    }
                    val checked = remember {
                        mutableStateOf(darkTheme.value)
                    }
                    Switch(
                        checked = checked.value, onCheckedChange = {
                            checked.value = it
                            darkTheme.value = !darkTheme.value
                            onEventDispatcher(AddTaskContract.Intent.SetTheme)
                        }, colors = SwitchColors(
                            checkedThumbColor = MaterialTheme.colorScheme.tertiaryContainer,
                            checkedTrackColor = MaterialTheme.colorScheme.secondaryContainer,
                            checkedBorderColor = Color.Transparent,
                            checkedIconColor = Color.Transparent,
                            uncheckedThumbColor = MaterialTheme.colorScheme.tertiaryContainer,
                            uncheckedTrackColor = MaterialTheme.colorScheme.onSurface,
                            uncheckedBorderColor = Color.Transparent,
                            uncheckedIconColor = Color.Transparent,
                            disabledCheckedThumbColor = Color.Transparent,
                            disabledCheckedTrackColor = Color.Transparent,
                            disabledCheckedBorderColor = Color.Transparent,
                            disabledCheckedIconColor = Color.Transparent,
                            disabledUncheckedThumbColor = Color.Transparent,
                            disabledUncheckedTrackColor = Color.Transparent,
                            disabledUncheckedBorderColor = Color.Transparent,
                            disabledUncheckedIconColor = Color.Transparent
                        )
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(0.5.dp)
                        .background(color = MaterialTheme.colorScheme.primary)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 20.dp, bottom = 16.dp)
                        .clickable {
                            if (data != null) {
                                onEventDispatcher(AddTaskContract.Intent.Delete(data.copy(isOffline = true, isInsert = false, isUpdate = false, isDelete = true)))
                            } else {
                                onEventDispatcher(AddTaskContract.Intent.Back)
                            }
                        }, verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete, contentDescription = stringResource(R.string.add_task_screen_trash), tint = if (text.value.isNotEmpty()) {
                            MaterialTheme.colorScheme.onSecondary
                        } else {
                            MaterialTheme.colorScheme.secondary
                        }
                    )
                    Text(
                        text = stringResource(R.string.add_task_screen_delete), fontFamily = FontFamily(Font(R.font.roboto_regular)), fontSize = 16.sp, color = if (text.value.isNotEmpty()) {
                            MaterialTheme.colorScheme.onSecondary
                        } else {
                            MaterialTheme.colorScheme.secondary
                        }, modifier = Modifier.padding(start = 10.dp)
                    )
                }
            }
        }
    }
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(color = MaterialTheme.colorScheme.onTertiaryContainer)
    systemUiController.setNavigationBarColor(color = MaterialTheme.colorScheme.onTertiaryContainer)
}