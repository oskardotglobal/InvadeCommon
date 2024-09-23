package berlin.ong.student.invadeclient

import com.jme3.math.Vector3f
import io.ktor.client.*
import io.ktor.client.features.websocket.*
import io.ktor.http.cio.websocket.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Queue
import java.util.concurrent.ArrayBlockingQueue

class InvadeControllerSender(private val url: String) {
    private val queue: Queue<String> = ArrayBlockingQueue(1024)
    private val client = HttpClient {
        install(WebSockets)
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun send() {
        GlobalScope.launch {
            client.ws(url) {
                while (true) {
                    val message = queue.poll() ?: continue
                    send(Frame.Text(message))
                }
            }
        }
    }

    fun disconnect() {
        client.close()
    }

    fun onRotationChanged(x: Float, y: Float, z: Float) {
        val success = queue.offer("${x};${y};${z}")
        if (!success) queue.clear()
    }
}
