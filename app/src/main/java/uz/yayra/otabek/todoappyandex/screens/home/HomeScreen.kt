@file:OptIn(ExperimentalMaterialApi::class)

package uz.yayra.otabek.todoappyandex.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissState
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay
import org.orbitmvi.orbit.compose.collectAsState
import uz.yayra.otabek.common.TodoEntity
import uz.yayra.otabek.presenter.home.HomeContract
import uz.yayra.otabek.presenter.home.HomeViewModel
import uz.yayra.otabek.todoappyandex.R
import uz.yayra.otabek.todoappyandex.utils.formatLongToDateString

/**
Developed by Otabek Nortojiyev
 **/

object HomeScreen : Screen {
    private fun readResolve(): Any = HomeScreen

    @Composable
    override fun Content() {
        val viewModel: HomeContract.ViewModel = getViewModel<HomeViewModel>()
        val uiState = viewModel.collectAsState()
        HomeScreenContent(uiState, viewModel::onEventDispatcher)
    }
}

var move = mutableStateOf(true)

@Composable
private fun HomeScreenContent(uiState: State<HomeContract.UiState>, onEventDispatcher: (HomeContract.Intent) -> Unit) {
    LaunchedEffect(Unit) { onEventDispatcher(HomeContract.Intent.Init) }
    val pullRefreshState = rememberPullRefreshState(refreshing = uiState.value.isLoading, onRefresh = {
        onEventDispatcher(HomeContract.Intent.GetAll)
    })
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onEventDispatcher(HomeContract.Intent.OpenAddScreen())
                }, shape = CircleShape, contentColor = MaterialTheme.colorScheme.onSecondaryContainer, containerColor = MaterialTheme.colorScheme.tertiaryContainer
            ) {
                Image(painter = painterResource(R.drawable.add), contentDescription = stringResource(R.string.home_screen_add_task))
            }
        }, containerColor = MaterialTheme.colorScheme.onTertiaryContainer
    ) {
        if (!uiState.value.internet) {
            Snackbar(containerColor = MaterialTheme.colorScheme.onSecondary) { Text(text = stringResource(R.string.home_screen_snackbar_text), fontSize = 22.sp) }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pullRefresh(pullRefreshState)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Column(modifier = Modifier.padding(start = 60.dp, top = 60.dp, end = 20.dp)) {
                    Text(
                        text = stringResource(R.string.home_screen_title),
                        fontSize = 32.sp,
                        modifier = Modifier.padding(it),
                        fontFamily = FontFamily(Font(R.font.roboto_medium)),
                        color = MaterialTheme.colorScheme.primaryContainer
                    )
                    Row {
                        Text(
                            text = "${stringResource(R.string.home_screen_done)} - ${uiState.value.done}", color = MaterialTheme.colorScheme.inversePrimary, fontFamily = FontFamily(
                                Font(
                                    R.font.roboto_regular
                                )
                            ), fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Image(painter = if (uiState.value.eye) {
                            painterResource(R.drawable.eye_visible)
                        } else {
                            painterResource(R.drawable.eye_invisible)
                        }, contentDescription = stringResource(R.string.home_screen_hide_done), modifier = Modifier
                            .clip(CircleShape)
                            .clickable {
                                onEventDispatcher(HomeContract.Intent.ChangeEye)
                                onEventDispatcher(HomeContract.Intent.GetAll)
                            })
                    }
                }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 20.dp)
                        .clip(shape = RoundedCornerShape(10.dp))
                        .background(color = MaterialTheme.colorScheme.background)
                ) {
                    items(uiState.value.todos, key = { it.hashCode() }) { item ->
                        SwipeToDeleteContainer(item = item, onDelete = {
                            onEventDispatcher(HomeContract.Intent.Delete(it.copy(isOffline = true, isInsert = false, isUpdate = false, isDelete = true)))
                        }, onComplete = {
                            onEventDispatcher(
                                HomeContract.Intent.Update(it.copy(isCompleted = true, isOffline = true, isInsert = false, isUpdate = true, isDelete = false))
                            )
                        }) { data ->
                            Item(data, onEventDispatcher)
                        }
                    }
                    item {
                        Text(text = stringResource(R.string.home_screen_new_todo),
                            color = MaterialTheme.colorScheme.inversePrimary,
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.roboto_regular)),
                            modifier = Modifier
                                .padding(start = 40.dp, top = 10.dp, bottom = 10.dp)
                                .clickable {
                                    onEventDispatcher(HomeContract.Intent.OpenAddScreen())
                                })
                    }
                }
            }
            PullRefreshIndicator(
                refreshing = uiState.value.isLoading,
                state = pullRefreshState,
                modifier = Modifier.align(alignment = Alignment.TopCenter),
                backgroundColor = if (uiState.value.isLoading) Color.Green else Color.Red
            )
        }
    }
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(color = MaterialTheme.colorScheme.onTertiaryContainer)
    systemUiController.setNavigationBarColor(color = MaterialTheme.colorScheme.onTertiaryContainer)
}

@Composable
fun DeleteBackGround(
    swipeDismissState: DismissState
) {
    val color = if (swipeDismissState.dismissDirection == DismissDirection.EndToStart) {
        MaterialTheme.colorScheme.onSecondary
    } else {
        Color.Transparent
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
            .padding(16.dp), contentAlignment = Alignment.CenterEnd
    ) {
        Icon(imageVector = Icons.Default.Delete, contentDescription = null, tint = MaterialTheme.colorScheme.tertiaryContainer)
    }
}

@Composable
fun CompleteBackGround(
    swipeDismissState: DismissState
) {
    val color = if (swipeDismissState.dismissDirection == DismissDirection.StartToEnd) {
        MaterialTheme.colorScheme.secondaryContainer
    } else {
        Color.Transparent
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
            .padding(16.dp), contentAlignment = Alignment.CenterStart
    ) {
        Icon(imageVector = Icons.Default.Done, contentDescription = null, tint = MaterialTheme.colorScheme.tertiaryContainer)
    }
}

@Composable
fun SwipeToDeleteContainer(
    item: TodoEntity, onDelete: (TodoEntity) -> Unit, onComplete: (TodoEntity) -> Unit, animationDuration: Int = 500, content: @Composable (TodoEntity) -> Unit
) {
    var isRemoved = remember { mutableStateOf(false) }
    var isCompleted = remember { mutableStateOf(false) }

    val swipeDismissState = rememberDismissState { dismissValue ->
        when (dismissValue) {
            DismissValue.DismissedToStart -> {
                isRemoved.value = true
                true
            }

            DismissValue.DismissedToEnd -> {
                if (!item.isCompleted) {
                    isCompleted.value = true
                    true
                } else {
                    false
                }
            }

            else -> false
        }
    }

    LaunchedEffect(isRemoved.value) {
        if (isRemoved.value) {
            if (move.value) {
                move.value = false
                delay(animationDuration.toLong())
                onDelete(item)
                move.value = true
            }
        }
    }

    LaunchedEffect(isCompleted.value) {
        if (isCompleted.value) {
            if (move.value) {
                move.value = false
                delay(animationDuration.toLong())
                onComplete(item)
                move.value = true
            }
        }
    }

    AnimatedVisibility(
        visible = !isRemoved.value && !isCompleted.value, exit = shrinkVertically(animationSpec = tween(durationMillis = animationDuration), shrinkTowards = Alignment.Top) + fadeOut()
    ) {
        SwipeToDismiss(state = swipeDismissState, background = {
            if (swipeDismissState.dismissDirection == DismissDirection.EndToStart && move.value) {
                DeleteBackGround(swipeDismissState)
            } else if (swipeDismissState.dismissDirection == DismissDirection.StartToEnd && move.value) {
                CompleteBackGround(swipeDismissState)
            } else {
                Color.Transparent
            }
        }, dismissContent = { content(item) }, directions = setOf(DismissDirection.EndToStart, DismissDirection.StartToEnd)
        )
    }
}

@Composable
private fun Item(data: TodoEntity, onEventDispatcher: (HomeContract.Intent) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.background)
            .clickable {
                if (!data.isCompleted) {
                    onEventDispatcher(HomeContract.Intent.OpenAddScreen(data))
                }
            }, verticalAlignment = Alignment.Top
    ) {
        Checkbox(
            checked = data.isCompleted, onCheckedChange = {
                if (!data.isCompleted) {
                    onEventDispatcher(
                        HomeContract.Intent.Update(data.copy(isCompleted = true, isOffline = true, isInsert = false, isUpdate = true, isDelete = false))
                    )
                }
            }, enabled = !data.isCompleted, colors = if (data.isCompleted) {
                CheckboxColors(
                    checkedCheckmarkColor = Color.White,
                    uncheckedCheckmarkColor = Color.Transparent,
                    checkedBoxColor = MaterialTheme.colorScheme.secondaryContainer,
                    uncheckedBoxColor = Color.Transparent,
                    disabledCheckedBoxColor = MaterialTheme.colorScheme.secondaryContainer,
                    disabledUncheckedBoxColor = Color.Transparent,
                    disabledIndeterminateBoxColor = Color.Transparent,
                    checkedBorderColor = MaterialTheme.colorScheme.secondaryContainer,
                    uncheckedBorderColor = Color.Transparent,
                    disabledBorderColor = MaterialTheme.colorScheme.secondaryContainer,
                    disabledUncheckedBorderColor = Color.Transparent,
                    disabledIndeterminateBorderColor = Color.Transparent
                )
            } else if (data.importance == "basic" || data.importance == "low") {
                CheckboxColors(
                    checkedCheckmarkColor = Color.Transparent,
                    uncheckedCheckmarkColor = Color.Transparent,
                    checkedBoxColor = Color.Transparent,
                    uncheckedBoxColor = Color.Transparent,
                    disabledCheckedBoxColor = Color.Transparent,
                    disabledUncheckedBoxColor = Color.Transparent,
                    disabledIndeterminateBoxColor = Color.Transparent,
                    checkedBorderColor = Color.Transparent,
                    uncheckedBorderColor = MaterialTheme.colorScheme.primary,
                    disabledBorderColor = Color.Transparent,
                    disabledUncheckedBorderColor = Color.Transparent,
                    disabledIndeterminateBorderColor = Color.Transparent
                )
            } else {
                CheckboxColors(
                    checkedCheckmarkColor = Color.Transparent,
                    uncheckedCheckmarkColor = Color.Transparent,
                    checkedBoxColor = Color.Transparent,
                    uncheckedBoxColor = MaterialTheme.colorScheme.surface,
                    disabledCheckedBoxColor = Color.Transparent,
                    disabledUncheckedBoxColor = Color.Transparent,
                    disabledIndeterminateBoxColor = Color.Transparent,
                    checkedBorderColor = Color.Transparent,
                    uncheckedBorderColor = MaterialTheme.colorScheme.onSecondary,
                    disabledBorderColor = Color.Transparent,
                    disabledUncheckedBorderColor = Color.Transparent,
                    disabledIndeterminateBorderColor = Color.Transparent
                )
            }
        )
        if (data.importance == "low" && !data.isCompleted) {
            Image(
                painter = painterResource(R.drawable.priority_low), contentDescription = null, modifier = Modifier.padding(top = 10.dp)
            )
        } else if (data.importance == "important" && !data.isCompleted) {
            Image(
                painter = painterResource(R.drawable.priority_high), contentDescription = null, modifier = Modifier.padding(top = 10.dp)
            )
        }
        Column(
            modifier = Modifier
                .padding(top = 10.dp)
                .weight(1f), horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = data.text, maxLines = 3, color = if (!data.isCompleted) {
                    MaterialTheme.colorScheme.primaryContainer
                } else {
                    MaterialTheme.colorScheme.inversePrimary
                }, fontFamily = FontFamily(Font(R.font.roboto_regular)), fontSize = 16.sp, overflow = TextOverflow.Ellipsis, textDecoration = if (data.isCompleted) {
                    TextDecoration.LineThrough
                } else {
                    TextDecoration.None
                }
            )
            if (data.deadLine != 0L) {
                Text(
                    text = formatLongToDateString(data.deadLine), fontFamily = FontFamily(Font(R.font.roboto_regular)), fontSize = 14.sp, color = MaterialTheme.colorScheme.inversePrimary
                )
            }
        }
        Icon(
            painter = if (data.isOffline) {
                painterResource(R.drawable.offline)
            } else {
                painterResource(R.drawable.info)
            }, contentDescription = stringResource(R.string.home_screen_task_info), modifier = Modifier.padding(10.dp)
        )
    }
}