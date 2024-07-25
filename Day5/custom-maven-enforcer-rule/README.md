# Content Enforcer

`Content Enforcer` is a rule for [https://maven.apache.org/enforcer/maven-enforcer-plugin/](https://maven.apache.org/enforcer/maven-enforcer-plugin/) which:

 - checks if the project generates a file with a specific content

This rule is useful when to guarantee the build to produce a certain type of JAR (e.g. a valud Java Module), or to prevent the build to emit invalid contents into the resulting artifacts (JAR/WAR/...).

## Usage

To run the integration tests run `mvn verify -Prun-its`
