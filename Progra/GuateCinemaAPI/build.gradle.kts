plugins {
	id("org.springframework.boot") version "2.5.4"  // Cambia a la versión más reciente de Spring Boot
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.9.0" // Para Kotlin DSL
	kotlin("plugin.spring") version "1.5.21"  // Necesario para que Spring funcione con Kotlin
}

group = "org.example"
version = "0.0.1-SNAPSHOT"

repositories {
	mavenCentral()  // Repositorio Maven Central para las dependencias
}

dependencies {
	// Spring Boot dependencies
	implementation("org.springframework.boot:spring-boot-starter-web")   // API REST
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")  // JPA y MySQL
	runtimeOnly("mysql:mysql-connector-java")  // Driver MySQL
	implementation("org.springframework.boot:spring-boot-starter-security")  // Seguridad y BCrypt
	implementation("org.springframework.boot:spring-boot-starter-validation")  // Validación

	// JWT Dependencies
	implementation("io.jsonwebtoken:jjwt-api:0.11.5")  // API JWT
	implementation("io.jsonwebtoken:jjwt-impl:0.11.5")  // Implementación JWT
	implementation("io.jsonwebtoken:jjwt-jackson:0.11.5")  // Jackson para JSON en JWT

	// Testing dependencies
	testImplementation("org.springframework.boot:spring-boot-starter-test")  // Para realizar pruebas
}

tasks.withType<Test> {
	useJUnitPlatform()  // Plataforma de pruebas para JUnit
}

java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(11))  // Usar Java 11
	}
}

tasks.withType<JavaCompile> {
	sourceCompatibility = "11"
	targetCompatibility = "11"
}
