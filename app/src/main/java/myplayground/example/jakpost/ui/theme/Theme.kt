package myplayground.example.jakpost.ui.theme

import android.app.Activity
import android.content.Context
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
private fun customDynamicLightColorScheme(context: Context): ColorScheme {
    val dynamicC = dynamicLightColorScheme(context)
    return remember {
        ColorScheme(
            primary = PrincetonOrange,
            secondary = YellowOrange,
            tertiary = DeepSaffron,
            background = White,
            primaryContainer = PrincetonOrange,

            onPrimary = White,
            onSecondary = White,
            onTertiary = White,
            onSurface = Black,
            onPrimaryContainer = White,
            onBackground = Black,

            error = dynamicC.error,
            errorContainer = dynamicC.errorContainer,
            inverseOnSurface = dynamicC.inverseOnSurface,
            inversePrimary = dynamicC.inversePrimary,
            inverseSurface = dynamicC.inverseSurface,
            onError = dynamicC.onError,
            onErrorContainer = dynamicC.onErrorContainer,
            onSecondaryContainer = dynamicC.onSecondaryContainer,
            onSurfaceVariant = dynamicC.onSurfaceVariant,
            onTertiaryContainer = dynamicC.onTertiaryContainer,
            outline = dynamicC.outline,
            outlineVariant = dynamicC.outlineVariant,
            scrim = dynamicC.scrim,
            secondaryContainer = dynamicC.onSecondaryContainer,
            surface = dynamicC.surface,
            surfaceTint = dynamicC.surfaceTint,
            surfaceVariant = dynamicC.onSurfaceVariant,
            tertiaryContainer = dynamicC.tertiaryContainer,
        )
    }
}

@Composable
private fun customDynamicDarkColorScheme(context: Context): ColorScheme {
    val dynamicC = dynamicDarkColorScheme(context)
    return remember {
        ColorScheme(
            primary = PrincetonOrange,
            secondary = YellowOrange,
            tertiary = DeepSaffron,
            background = Arsenic,
            primaryContainer = PrincetonOrange,

            onPrimary = White,
            onSecondary = White,
            onTertiary = White,
            onSurface = White,
            onPrimaryContainer = White,
            onBackground = White,

            error = dynamicC.error,
            errorContainer = dynamicC.errorContainer,
            inverseOnSurface = dynamicC.inverseOnSurface,
            inversePrimary = dynamicC.inversePrimary,
            inverseSurface = dynamicC.inverseSurface,
            onError = dynamicC.onError,
            onErrorContainer = dynamicC.onErrorContainer,
            onSecondaryContainer = dynamicC.onSecondaryContainer,
            onSurfaceVariant = dynamicC.onSurfaceVariant,
            onTertiaryContainer = dynamicC.onTertiaryContainer,
            outline = dynamicC.outline,
            outlineVariant = dynamicC.outlineVariant,
            scrim = dynamicC.scrim,
            secondaryContainer = dynamicC.onSecondaryContainer,
            surface = dynamicC.surface,
            surfaceTint = dynamicC.surfaceTint,
            surfaceVariant = dynamicC.onSurfaceVariant,
            tertiaryContainer = dynamicC.tertiaryContainer,
        )
    }
}

private val darkColorScheme = darkColorScheme(
    primary = PrincetonOrange,
    secondary = YellowOrange,
    tertiary = DeepSaffron,
    background = Arsenic,
    primaryContainer = PrincetonOrange,

    onPrimary = White,
    onSecondary = White,
    onTertiary = White,
    onSurface = White,
    onPrimaryContainer = White,
    onBackground = White,
)

private val lightColorScheme = lightColorScheme(
    primary = PrincetonOrange,
    secondary = YellowOrange,
    tertiary = DeepSaffron,
    background = White,
    primaryContainer = PrincetonOrange,

    onPrimary = White,
    onSecondary = White,
    onTertiary = White,
    onSurface = Black,
    onPrimaryContainer = White,
    onBackground = Black,
)

@Composable
fun JakPostTheme(
    darkTheme: Boolean = isSystemInDarkTheme(), // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true, content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme)
                customDynamicDarkColorScheme(context)
            else
                customDynamicLightColorScheme(context)
        }

        darkTheme -> darkColorScheme
        else -> lightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    val context = LocalContext.current

    MaterialTheme(
        colorScheme = colorScheme, typography = Typography, content = content
    )
}