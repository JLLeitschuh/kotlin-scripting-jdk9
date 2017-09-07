@file:JvmName("Main")
package compiler

import api.DefaultGradleProject
import api.GradleProject

import org.jetbrains.kotlin.script.KotlinScriptDefinitionFromAnnotatedTemplate
import org.jetbrains.kotlin.utils.PathUtil

import org.slf4j.LoggerFactory

import java.io.File

import kotlin.script.dependencies.Environment
import kotlin.script.dependencies.ScriptContents
import kotlin.script.experimental.dependencies.DependenciesResolver
import kotlin.script.experimental.dependencies.ScriptDependencies
import kotlin.script.extensions.SamWithReceiverAnnotations
import kotlin.script.templates.ScriptTemplateDefinition

@SamWithReceiverAnnotations("api.ParameterExtension")
@ScriptTemplateDefinition(resolver = Resolver::class, scriptFilePattern = """.+\.gradle\.kts""")
abstract class BuildScript(project: GradleProject) : GradleProject by project


fun main(vararg args: String) {

    // I've commented out this example as it is more complicated.
    // We should get the simple case working first
//    val buildscript = """
//         println(copySpec {
//            from("src")
//            into("build")
//         })
//    """
    val buildscript = """
        println("working under JDK 9!!!")
    """

    val outputDirectory = File("build/classes")
    val scriptFile = File("build/build.gradle.kts").apply {
        parentFile.mkdirs()
        writeText(buildscript)
    }

    val classLoader = BuildScript::class.java.classLoader
    val scriptClass = compileKotlinScriptToDirectory(
        outputDirectory,
        scriptFile,
        KotlinScriptDefinitionFromAnnotatedTemplate(BuildScript::class),
        classLoader,
        LoggerFactory.getLogger("main"))

    scriptClass.getConstructor(GradleProject::class.java).newInstance(DefaultGradleProject())
}


class Resolver : DependenciesResolver {

    override fun resolve(scriptContents: ScriptContents, environment: Environment): DependenciesResolver.ResolveResult =
        DependenciesResolver.ResolveResult.Success(
            ScriptDependencies(classpath = classPath, imports = listOf("api.*", "compiler.*")))

    private
    val classPath: List<File>
        get() {
            println("Getting Classpath")
            val classPath = listOf(
                api.Action::class,
                kotlin.PublishedApi::class,
                kotlin.reflect.KClass::class,
                Resolver::class
            ).map {
                it.java.protectionDomain.codeSource.location.toURI()
            }.map {
                File(it)
            } + PathUtil.getJdkClassesRootsFromCurrentJre()

            println("Got classpath: $classPath")
            return classPath
        }
}