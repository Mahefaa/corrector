plugins {
    id("java")
}

group = "com.corrector"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Jackson for JSON parsing
    implementation("com.fasterxml.jackson.core:jackson-databind:2.16.0")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.16.0")

    // Apache Commons CSV for CSV parsing
    implementation("org.apache.commons:commons-csv:1.10.0")

    // Lombok for easy POJOs (optional)
    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}