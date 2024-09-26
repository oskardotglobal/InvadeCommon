package berlin.ong.student.invadeclient

import com.jme3.math.Vector3f

object L : InvadeControllerReceiver.Listener {
    override fun onRotationChange(rotation: Vector3f) {
        println(rotation)
    }

    override fun onError() {
       //
    }

}

fun main() {
    val client = InvadeControllerReceiver("127.0.0.1", 8080, "/ws")
    client.listen(L)

    while (true) {

    }

    client.disconnect()
}