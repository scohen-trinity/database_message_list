package actors

import akka.actor.Actor
import akka.actor.ActorRef

class CanvasManager extends Actor {
    private var chatters = List.empty[ActorRef]
    private var rectList: Map[String, (Int, Int)] = Map();

    import CanvasManager._
    
    def receive = {
        case NewChatter(chatter) => 
            chatters ::= chatter
            val initialStateMessage = s"initial state: ${rectList.map {case (id, (x, y)) => s"$id,$x,$y"}.mkString(";")}"
            // chatter ! Message(initialStateMessage)
        case Message(msg) => for(c <- chatters) c ! CanvasActor.SendMessage(msg)
        // case DrawRect(x, y) => 
        //     for(c <- chatters) c ! CanvasActor.DrawRect(x, y)
        //     val senderUserId = sender().path.name
        //     rectList += (senderUserId -> (x,y))

        case m => println("Unhandled message in CanvasManager: " + m)
    }
}

object CanvasManager {
    case class NewChatter(chatter: ActorRef)
    case class Message(msg: String)
    case class DrawRect(x: Int, y: Int)
}