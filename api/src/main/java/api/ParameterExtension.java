package api;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a SAM interface as a target for lambda expressions / closures
 * where the single parameter is passed as the implicit receiver of the
 * invocation ({@code this} in Kotlin, {@code delegate} in Groovy) as if
 * the lambda expression was an extension method of the parameter type.
 *
 * <pre>
 *     // copySpec(Action&lt;CopySpec&gt;)
 *     copySpec {
 *         from("./sources") // the given CopySpec is the implicit receiver
 *     }
 * </pre>
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ParameterExtension {
}