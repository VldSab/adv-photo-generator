import org.gradle.jvm.tasks.Jar

plugins {
    id("java")
    id("edu.sc.seis.launch4j") version "2.5.4"
}

group = "com.vovatech"
version = "1.0-SNAPSHOT"

val appMainClass = "com.vovatech.Boostrap"

repositories { mavenCentral() }

dependencies {
    implementation("com.twelvemonkeys.imageio:imageio-webp:3.10.1")
    implementation("org.slf4j:slf4j-simple:2.0.13")
    implementation("commons-io:commons-io:2.20.0")

    compileOnly("org.projectlombok:lombok:1.18.34")
    annotationProcessor("org.projectlombok:lombok:1.18.34")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.register<Copy>("bundleJre") {
    into(layout.buildDirectory.dir("launch4j"))          // корень рядом с .exe
    from(layout.projectDirectory.dir("runtime/jdk-21")) { into("jre") } // кладём как папку jre
}

tasks.jar {
    archiveFileName.set("adv-photo-generator.jar")
    manifest { attributes("Main-Class" to appMainClass) }
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.named("createExe") {
    dependsOn(tasks.jar, tasks.named("bundleJre"))
}

tasks.named("createExe") {
    dependsOn(tasks.jar, tasks.named("bundleJre"))
}

launch4j {
    mainClassName = appMainClass
    bundledJrePath = "jre"
    outfile = "adv-photo-generator.exe"
    // КЛЮЧЕВОЕ: вместо jar = "build/libs/..." - привязываем к таске jar
    jarTask = tasks.getByName("jar") as Jar
    headerType = "gui" // или "console" для вывода в консоль
}

