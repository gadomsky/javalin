/*
 * Javalin - https://javalin.io
 * Copyright 2017 David Åse
 * Licensed under Apache 2.0: https://github.com/tipsy/javalin/blob/master/LICENSE
 */

package io.javalin.examples

import io.javalin.ApiBuilder.get
import io.javalin.ApiBuilder.path
import io.javalin.Javalin

fun main(args: Array<String>) {
    Javalin.create()
            .port(7070)
            .routes {
                get("/hello") { ctx -> ctx.body("Hello World") }
                path("/api") {
                    get("/test") { ctx -> ctx.body("Hello World") }
                    get("/tast") { ctx -> ctx.status(200).body("Hello world") }
                    get("/hest") { ctx -> ctx.status(200).body("Hello World") }
                    get("/hast") { ctx -> ctx.status(200).body("Hello World").header("test", "tast") }
                }
            }
}
