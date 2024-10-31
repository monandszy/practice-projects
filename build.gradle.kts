import nebula.plugin.contacts.Contact
import org.gradle.internal.impldep.org.codehaus.plexus.util.FileUtils.loadFile


plugins {
  id("nebula.contacts") version "6.0.0"
}

contacts {
  addPerson("monandszy@tuta.io", delegateClosureOf<Contact> {
    moniker = "Szymon Andrzejewski"
    roles("developer", "maintainer")
  })
}

tasks {
  fun composeUp(projectName: Any) {
    exec {
      workingDir("./docker/")
      commandLine(
        "docker", "compose",
        "-p", projectName,
        "-f", "compose-$projectName.yml",
        "up",
        "-d",
        "--no-recreate",
      )
    }
  }

  fun composeDown(projectName: Any) {
    exec {
      commandLine("docker", "compose", "-p", projectName, "down")
    }
  }

  register("composeDataUp") {
    doLast {
      composeDown("data")
      composeUp("data")
    }
  }


  register("composeDataDown") {
    doLast {
      composeDown("data")
    }
  }
}