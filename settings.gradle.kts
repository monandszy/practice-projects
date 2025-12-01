plugins {
  id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "practice-projects"

fun includeModules(dir: File) {
  if (dir.exists() && dir.isDirectory) {
    dir.listFiles()?.filter { it.isDirectory }?.forEach { subDir ->
      if (subDir.name.startsWith("_")) {
        includeModules(subDir)
      } else {
        include(":${subDir.name}") // Include other directories directly
        project(":${subDir.name}").projectDir = subDir
      }
    }
  }
}

val modulesDir = file("_modules")
includeModules(modulesDir)