import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar
import java.awt.Desktop
import java.io.FileNotFoundException
import java.io.InputStream
import java.net.URI

fun getdate(): String? {
    val date = Calendar.getInstance().time
    val formatter = SimpleDateFormat.getDateTimeInstance() //or use getDateInstance()
    return formatter.format(date)
}


fun getdesc(game:Any): String?{
    val inputStream: InputStream = File("$game.txt").inputStream()
    val gamedesc = inputStream.bufferedReader().use { it.readText() }
    val lines = gamedesc.lines()
    lines.forEach {println(it)}
    return gamedesc
}

fun appendtext(formattedDate:String?, game:Any,gamedesc:String?): Boolean{
    File("result.txt").appendText("$formattedDate \n Game was: $game \n Description: $gamedesc\n")
    println("result.txt appended successfully...")
    return true
}

fun gamefaqssearch(gamesearch:String): Int {
    if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
        Desktop.getDesktop().browse(URI("https://gamefaqs.gamespot.com/search?game=$gamesearch"))
    }
    return 1
}


fun mygithub() {
    if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
        Desktop.getDesktop().browse(URI("http://www.github.com/markmental"))
    }
}


    fun main() {
        var gamefaqsran = 0
        var running = 1

        while (running == 1) {
            println("Enter A Sega Saturn Game to look up")
            //Elvis operator(?:) is used as shortcuts for null exception
            val game = readLine()?:0

            try {
                val formatteddate = getdate()
                val gamedesc = getdesc(game)
                val gamelist = arrayOf(
                    "Dragon Force", "Daytona USA", "Sega Rally Championship", "Sonic R",
                    "Sonic Jam")
                var i = 0

                do {
                    var found = false

                    // checks if game is in the gamelist array
                    if (game == gamelist[i]) {
                        //found becomes true when appendtext runs since it returns true
                        found = appendtext(formatteddate, game, gamedesc)
                    }
                    i++

                }while (!found)

            } catch (e: FileNotFoundException) {
                println("$game was not found")
            }

            println("Search for more info about the game on GameFAQS?(y/n)")
            val gamefaqs = readLine()?:0

            if (gamefaqs == "y" || gamefaqs == "Y") {
                var gamesearch = game.toString()
                //replacing spaces in game name with "+" so GameFAQS can use the input
                gamesearch = gamesearch.replace(" ", "+")
                gamefaqsran = gamefaqssearch(gamesearch)
            }

            //triggers when gamefaqssearch doesn't run
           if (gamefaqsran == 0) {
                println("Do another search? (y/n)")
                val restart = readLine()?:0

                 //running stays with the value of 1 and program restarts if restart !="n"
                if (restart == "n" || restart == "N") {
                    println("Check out my Github?(y/n)")
                    val github = readLine()?:0

                    if (github == "Y" || github =="y") {
                        println("Opening my Github...")
                        mygithub()
                    }
                    running = 0
                }
            }

            //else to close if gamefaqssearch runs
            else{
                println("Searching for: $game on Gamefaqs...")
                running = 0
            }
        }
    }
