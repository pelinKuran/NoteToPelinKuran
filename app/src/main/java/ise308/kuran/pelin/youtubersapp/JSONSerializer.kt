package ise308.kuran.pelin.youtubersapp

import android.content.Context
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONTokener
import java.io.*
import java.lang.StringBuilder

// saving application data to a JSON file and retrieving it and adding
//support to different languages.
class JSONSerializer(private val filename: String, private val context: Context) {
    @Throws(IOException::class, JSONException::class)
    fun save(noteList: List<Note>) {
        val jsonArray = JSONArray()
        for (note in noteList) {
            jsonArray.put(note.convertToJSON())
        }
        var writer: Writer? = null
        try {
            val outFile = context.openFileOutput(
                filename,
                Context.MODE_PRIVATE
            ) //can only be accessed by my this application
            writer = OutputStreamWriter(outFile)
            writer.write(jsonArray.toString())
        } finally {
            if (writer != null) {
                writer.close()
            }
        }
    }

    @Throws(IOException::class, JSONException::class)
    fun load(): ArrayList<Note> {
        val noteList = ArrayList<Note>()
        var reader: BufferedReader? = null
        try {
            val inFile = context.openFileInput(filename)
            reader = BufferedReader(InputStreamReader(inFile))
            val jsonString = StringBuilder()
            for (line in reader.readLine()) {
                jsonString.append(line)
            }
            val jsonArray = JSONTokener(jsonString.toString())
                .nextValue() as JSONArray
            for (i in 0 until jsonArray.length()) {
                noteList.add(Note(jsonArray.getJSONObject(i)))
            }
        } catch (e: FileNotFoundException) {
            //ignore it when start fresh.
        } finally {
            reader!!.close()
        }
        return noteList
    }
}
//I could not find a chance to properly test this class,
// because of my low storage.
// I know that when I leave the app data are kept,
// but I couldn't test whether if they lost when I kill it.
