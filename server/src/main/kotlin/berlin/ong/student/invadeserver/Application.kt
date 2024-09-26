package berlin.ong.student.invadeserver

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install(WebSockets) {
        maxFrameSize = Long.MAX_VALUE
        masking = false
        pingPeriodMillis = 0
    }

    routing {
        val messageResponseFlow = MutableSharedFlow<String>()
        val sharedFlow = messageResponseFlow.asSharedFlow()

        webSocket("/ws") {
            val job = launch {
                sharedFlow.collect { send(it) }
            }

            runCatching {
                incoming.consumeEach { frame ->
                    if (frame !is Frame.Text) return@consumeEach
                    val text = frame.readText()

                    if (text.split(";").size != 3) return@consumeEach

                    messageResponseFlow.emit(text)
                }
            }.onFailure { exception ->
                println("WebSocket exception: ${exception.localizedMessage}")
            }.also {
                job.cancel()
            }
        }
    }
}