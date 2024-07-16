package com.edwin.sekai.ui.designsystem.component

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusRestorer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Tab
import androidx.tv.material3.TabRow
import androidx.tv.material3.TabRowScope
import androidx.tv.material3.Text
import com.edwin.sekai.ui.designsystem.theme.SekaiTheme
import com.edwin.sekai.ui.feature.home.model.TabNavOption

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    selectedTabIndex: Int,
    content: @Composable TopBarScope.() -> Unit
) {
    TabRow(
        modifier = modifier.focusRestorer(),
        selectedTabIndex = selectedTabIndex
    ) {
        with(TopBarScope(this)) {
            content()
        }
    }
}

class TopBarScope(private val tabRowScope: TabRowScope) {

    @Composable
    fun TextItem(
        key: Int,
        @StringRes labelResId: Int,
        focusRequester: FocusRequester,
        selected: Boolean,
        onSelectOption: () -> Unit,
        focusManager: FocusManager
    ) = with(tabRowScope) {
        key(key) {
            Tab(
                modifier = Modifier
                    .padding(vertical = 6.dp, horizontal = 12.dp)
                    .focusRequester(focusRequester),
                selected = selected,
                onFocus = onSelectOption,
                onClick = { focusManager.moveFocus(FocusDirection.Down) },
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = stringResource(labelResId),
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}

@Preview(
    device = "id:tv_1080p",
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_TELEVISION
)
@Composable
fun DashboardTopBarPreview() {
    SekaiTheme {
        val (currentIndex, setIndex) = remember { mutableIntStateOf(0) }
        val focusRequesters = remember { List(TabNavOption.entries.size) { FocusRequester() } }

        Box(Modifier.fillMaxSize()) {
            // TODO ::
        }
    }
}