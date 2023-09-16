package myplayground.example.jakpost.utils

import android.content.Context
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class JsonHelper {
    companion object {
        inline fun <reified T> decodeFromFile(context: Context, resourceId: Int): T? {
            return try {
                val inputStream = context.resources.openRawResource(resourceId)

                val jsonText = inputStream.bufferedReader().use { it.readText() }

                Json.decodeFromString(jsonText)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

        inline fun <reified T> mustDecodeFromFile(context: Context, resourceId: Int): T {
            return decodeFromFile<T>(context, resourceId) ?: throw Error("Error parsing content")
        }
    }
}