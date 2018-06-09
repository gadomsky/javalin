package io.javalin.core.util

import io.javalin.Context
import io.javalin.core.HandlerType
import io.javalin.core.PathMatcher

private data class JsonMethodNotAllowed(val availableMethods: List<HandlerType>)

object MethodNotAllowedUtil {

    @JvmStatic
    fun findAvailableHttpHandlerTypes(matcher: PathMatcher, requestUri: String): List<HandlerType> {
        val availableHandlerTypes = ArrayList<HandlerType>()

        enumValues<HandlerType>().forEach {

            if (!it.isHttpMethod())
                return@forEach

            val entries = matcher.findEntries(it, requestUri)

            if (!entries.isEmpty()) {
                availableHandlerTypes.add(it)
            }
        }
        return availableHandlerTypes
    }

    fun getAvailableHandlerTypes(ctx: Context, availableHandlerTypes: List<HandlerType>): String {
        val acceptableReturnTypes = ctx.header("Accept")
        return if (acceptableReturnTypes != null && acceptableReturnTypes.contains("application/json")) {
            MethodNotAllowedUtil.createJsonMethodNotAllowed(availableHandlerTypes)
        } else {
            MethodNotAllowedUtil.createHtmlMethodNotAllowed(availableHandlerTypes)
        }
    }

    private fun createJsonMethodNotAllowed(availableHandlerTypes: List<HandlerType>): String {

        return """{"availableMethods":${availableHandlerTypes.joinToString(separator = "\", \"", prefix = "[\"", postfix = "\"]")}}"""
    }

    private fun createHtmlMethodNotAllowed(availableHandlerTypes: List<HandlerType>): String {
        return """
                    <!DOCTYPE html>
                    <html lang="en">
                        <head>
                            <meta charset="UTF-8">
                            <title>Method Not Allowed</title>
                        </head>
                        <body>
                            <h1>405 - Method Not Allowed</h1>
                            <p>
                                Available Methods: <strong>${availableHandlerTypes.joinToString(", ")}</strong>
                            </p>
                        </body>
                    </html>
                """
    }
}
