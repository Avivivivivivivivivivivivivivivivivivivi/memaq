import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id("org.springframework.boot") version "2.7.0"
  id("io.spring.dependency-management") version "1.0.11.RELEASE"
  kotlin("jvm") version "1.6.21"
  kotlin("plugin.spring") version "1.6.21"
  kotlin("plugin.jpa") version "1.6.21"
}

group = "com.avi"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
  mavenCentral()
}

dependencies {
  implementation("org.springframework.boot:spring-boot-starter")
  implementation("org.jetbrains.kotlin:kotlin-reflect")
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
  implementation("org.springframework.boot:spring-boot-starter-web:2.6.7")
  implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.6.7")
  runtimeOnly("org.postgresql:postgresql")
  testImplementation("org.springframework.boot:spring-boot-starter-test:2.6.7")
  testImplementation("com.h2database:h2:2.1.212")
  testImplementation("org.assertj:assertj-core:3.22.0")
}

tasks.withType<KotlinCompile> {
  kotlinOptions {
    freeCompilerArgs = listOf("-Xjsr305=strict")
    jvmTarget = "17"
  }
}

tasks.withType<Test> {
  useJUnitPlatform()
}

tasks.named<Test>("test") {
  useJUnitPlatform()
  maxParallelForks = 1
  maxHeapSize = "4G"
  outputs.upToDateWhen { false }

  testLogging {
    showStandardStreams = true
    showExceptions = true
    showCauses = true
    showStackTraces = true
    events = setOf(
      TestLogEvent.STARTED,
      TestLogEvent.PASSED,
      TestLogEvent.STANDARD_ERROR,
      TestLogEvent.SKIPPED,
      TestLogEvent.FAILED,
    )
    addTestListener(object : TestListener {
      override fun beforeSuite(suite: TestDescriptor) {}
      override fun beforeTest(testDescriptor: TestDescriptor) {}
      override fun afterTest(testDescriptor: TestDescriptor, result: TestResult) {}
      override fun afterSuite(descriptor: TestDescriptor, result: TestResult) {
        if(descriptor.parent == null){
          val output = "Results: ${result.resultType} (${result.testCount} tests, ${result.successfulTestCount} passed, ${result.failedTestCount} failed, ${result.skippedTestCount} skipped)"
          val startItem = "|  "
          val endItem = "  |"
          val repeatLength = startItem.length + output.length + endItem.length
          println("""\n${"-".repeat(repeatLength)}\n$startItem$output$endItem\n${"-".repeat(repeatLength)}""")
        }
      }
    })
  }
}