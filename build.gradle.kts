plugins {
    java
    id("io.quarkus")
}

repositories {
    mavenCentral()
    mavenLocal()
}

val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project

dependencies {
    implementation(enforcedPlatform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:${quarkusPlatformVersion}"))
    implementation("io.quarkus:quarkus-resteasy-reactive-jackson")
    implementation("io.quarkus:quarkus-resteasy-reactive")
    implementation("io.quarkus:quarkus-resteasy-reactive-links")
    implementation("io.quarkus:quarkus-arc")
    implementation("io.quarkus:quarkus-smallrye-openapi")
    // https://mvnrepository.com/artifact/com.orientechnologies/orientdb-graphdb
    // https://mvnrepository.com/artifact/com.orientechnologies/orientdb-gremlin
    implementation("com.orientechnologies:orientdb-gremlin:3.2.9")
    implementation("com.orientechnologies:orientdb-client:3.2.9")
    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation("io.rest-assured:rest-assured")
    // https://mvnrepository.com/artifact/com.syncleus.ferma/ferma
    implementation("com.syncleus.ferma:ferma:3.3.2")
    implementation("com.syncleus.ferma:ferma-orientdb:3.0.1")
    // https://mvnrepository.com/artifact/org.reflections/reflections
    implementation ("org.reflections:reflections:0.10.2")

}

group = "com.vibeconn"
version = "1.0.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-parameters")
}
