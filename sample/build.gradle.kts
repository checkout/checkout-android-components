// Top-level build file where you can add configuration options common to all sub-projects/modules.
import com.diffplug.gradle.spotless.SpotlessExtension
import com.diffplug.spotless.LineEnding
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.spotless) apply false
}

allprojects {
    pluginManager.apply("com.diffplug.spotless")

    configure<SpotlessExtension> {
        setLineEndings(LineEnding.GIT_ATTRIBUTES_FAST_ALLSAME)
        format("misc") {
            target("*.gradle", "*.md", ".gitignore")
            trimTrailingWhitespace()
            leadingSpacesToTabs(2)
            endWithNewline()
        }

        kotlin {
            target("**/*.kt")
            targetExclude("**/build/**/*.kt")
            ktlint().editorConfigOverride(
                mapOf(
                    "indent_size" to "2",
                    "continuation_indent_size" to "2",
                    "ktlint_function_naming_ignore_when_annotated_with" to "Composable,Test",
                    "standard:function_naming" to "disabled",
                    "ktlint_standard_package-name" to "disabled",
                    "standard:filename" to "disabled",
                    "standard:no_empty_file" to "disabled",
                    "ktlint_experimental" to "enabled",
                )
            )
        }

        format("kts") {
            target("**/*.kts")
            targetExclude("**/build/**/*.kts")

        }

        format("xml") {
            target("**/*.xml")
            targetExclude("**/build/**/*.xml")
        }
    }
}
