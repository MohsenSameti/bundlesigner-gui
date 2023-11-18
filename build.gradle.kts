import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import java.nio.file.Paths

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
}

group = "com.samentic"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    // FIXME: Can this be better?
    implementation(files("./bundle-signer/target/bundlesigner-0.1.13.jar"))
    // Note, if you develop a library, you should use compose.desktop.common.
    // compose.desktop.currentOs should be used in launcher-sourceSet
    // (in a separate module for demo project and in testMain).
    // With compose.desktop.common you will also lose @Preview functionality
    implementation(compose.desktop.currentOs)

    val voyagerVersion = "1.0.0-rc05"
    implementation("cafe.adriel.voyager:voyager-navigator:$voyagerVersion")
    implementation("cafe.adriel.voyager:voyager-transitions:$voyagerVersion")
    implementation("cafe.adriel.voyager:voyager-bottom-sheet-navigator:$voyagerVersion")
    implementation("cafe.adriel.voyager:voyager-tab-navigator:$voyagerVersion")
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "bundlesigner-gui"
            packageVersion = "1.0.0"
        }
    }
}

tasks.register<Exec>("buildBundleSigner") {
    // TODO: use installed mvn on system!
    val command = if (System.getProperty("os.name").toLowerCase().contains("win")) "mvn.cmd" else "mvn"
    executable = Paths.get(rootProject.projectDir.absolutePath, "apps", "apache-maven", "bin", command).toFile().absolutePath
    setArgs("package -DskipTests -f ./bundle-signer/pom.xml".split(" "))
}

tasks.named("compileKotlin") {
    dependsOn += "buildBundleSigner"
}
