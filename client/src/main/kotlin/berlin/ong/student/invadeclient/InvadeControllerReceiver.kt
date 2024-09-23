package berlin.ong.student.invadeclient

import com.jme3.math.Vector3f
import io.ktor.client.*
import io.ktor.client.features.websocket.*
import io.ktor.http.cio.websocket.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Receive a `Vector3f` every time the client sends a rotation update.
 * Run `InvadeControllerReceiver::listen` with a `InvadeControllerReceiver.Listener` as the argument.
 *
 * @see InvadeControllerReceiver.Listener
 * @see listen
 */
class InvadeControllerReceiver(private val url: String) {
    private val client = HttpClient {
        install(WebSockets)
    }

    /**
     * Start listening for rotation updates.
     * This is *not* a blocking operation and runs asynchronously.
     *
     * @param listener the listener the updates are sent to
     * @see Listener
     */
    @OptIn(DelicateCoroutinesApi::class)
    fun listen(listener: Listener) {
        GlobalScope.launch {
            client.ws(url) {
                try {
                    for (message in incoming) {
                        if (message !is Frame.Text) continue

                        val floats = message.readText()
                            .split(";")
                            .map { v -> v.toFloat() }

                        assert(floats.size == 3)

                        val vec = Vector3f(floats[0], floats[1], floats[2])
                        listener.onRotationChange(vec)
                    }
                } catch (e: Exception) {
                    listener.onError()
                }
            }
        }
    }

    /**
     * Disconnect the client, updates will no longer be sent.
     */
    fun disconnect() {
        client.close()
    }

    interface Listener {
        /**
         * This is called every time the client sends an update
         */
        fun onRotationChange(rotation: Vector3f)

        /**
         * This is called should there be an error of any kind.
         * This should probably call `InvadeControllerReceiver::disconnect` if it ends the program.
         * @see disconnect
         */
        fun onError()
    }
}
