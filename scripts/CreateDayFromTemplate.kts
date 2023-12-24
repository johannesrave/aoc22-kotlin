import java.io.File
import java.nio.file.Paths
import kotlin.system.exitProcess

// No main-function is needed as this is a Kotlin-Script
// which automatically executes the top-level-code.

if (args.size != 1 || args[0].length != 2 || args[0].toIntOrNull() == null) {
    println("Please enter a two-digit number for the day to create: script <two-digit-number>")
    exitProcess(1)
}

val dayNumber = args[0]
val currentWorkingDir = Paths.get("").toAbsolutePath().toString()
val scriptDir = "$currentWorkingDir/scripts"
val templateDir = "$scriptDir/templates"

val placeholder = "DAY_PLACEHOLDER"

val templatesToRender = mapOf(
    "$templateDir/DayTemplate.kt" to "src/main/kotlin/Day${dayNumber}.kt",
    "$templateDir/DayTemplateTest.kt" to "src/test/kotlin/Day${dayNumber}Test.kt",
)

templatesToRender
    .mapKeys { (templateFileName, _) -> File(templateFileName).readText().replace(placeholder, dayNumber) }
    .forEach { (template, fileName) -> File(fileName).writeText(template) }

println("Templates rendered successfully.")

val filesToCreate = listOf(
    "input/${dayNumber}.txt",
    "input/${dayNumber}.test.txt",
)

filesToCreate.forEach { fileName ->
    File(fileName).writeText("")
}

println("Files created successfully.")