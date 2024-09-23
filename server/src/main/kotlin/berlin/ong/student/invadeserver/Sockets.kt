package berlin.ong.student.invadeserver

import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import java.time.Duration

fun Application.configureSockets() {
    install(WebSockets) {
        pingPeriod = Duration.ofSeconds(15)
        timeout = Duration.ofSeconds(15)
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }

    routing {
        webSocket("/ws") {
            for (message in incoming) {
                if (message !is Frame.Text) continue
                if (message.readText().split(";").size != 3) continue

                outgoing.send(message)
            }
        }
    }
}
