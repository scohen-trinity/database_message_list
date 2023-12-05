package actors

import akka.actor.Actor
import akka.actor.Props
import akka.actor.ActorRef

class CanvasActor(out: ActorRef, manager: ActorRef) extends Actor {
    manager ! CanvasManager.NewChatter(self)
    
    import CanvasActor._

    var spritePositions: Map[String, (Int, Int)] = Map()

    def receive = {
        case s:String =>
            println("Got sprite: " + s) 
            manager ! CanvasManager.Message(s)
        case SendMessage(msg) => out ! msg
        case m => println("Unhandled message in CanvasActor: " + m)
    }

    out ! "Connected"
}

object CanvasActor {
    def props(out: ActorRef, manager: ActorRef) = Props(new CanvasActor(out, manager))

    case class Message(msg: String)
    case class SendMessage(msg: String)
    case class DrawRect(x: Int, y: Int)
}