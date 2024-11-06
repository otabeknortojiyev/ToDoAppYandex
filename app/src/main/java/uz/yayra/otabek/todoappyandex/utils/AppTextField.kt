package uz.yayra.otabek.todoappyandex.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.yayra.otabek.todoappyandex.R

@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = TextStyle(
        color = MaterialTheme.colorScheme.primaryContainer, fontFamily = FontFamily(Font(R.font.roboto_regular)), fontSize = 16.sp
    ),
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    hint: String = "",
    errorText: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    BasicTextField(value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = false,
        visualTransformation = visualTransformation,
        cursorBrush = SolidColor(Color.DarkGray),
        decorationBox = { innerTextField ->
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colorScheme.background, shape = RoundedCornerShape(10.dp))
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                        .defaultMinSize(minHeight = 50.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    leadingIcon?.invoke()
                    Box {
                        if (value.isEmpty()) {
                            Text(text = hint, style = textStyle.copy(color = MaterialTheme.colorScheme.inversePrimary))
                        }
                        innerTextField()
                    }
                    trailingIcon?.invoke()
                }

                errorText?.let {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = errorText, style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.roboto_regular)), fontWeight = FontWeight.Medium, color = Color.Red
                        ), modifier = Modifier.padding(start = 16.dp), maxLines = 1
                    )
                }
            }
        })
}