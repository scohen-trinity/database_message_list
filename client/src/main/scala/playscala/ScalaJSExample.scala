package playscala

import shared.SharedMessages
import org.scalajs.dom

import slinky.core._
import slinky.web.ReactDOM
import slinky.web.html._

import org.scalajs.dom.document
import scalajs.js
import scala.scalajs.js.annotation.JSExportTopLevel
import org.scalajs.dom.html

import scala.util.Random

object ScalaJSExample {
  

  def main(args: Array[String]): Unit = {
    // initializing the canvas
    val width = 500
    val height = 350
    var gameOver = false
    var x = width / 2
    var y = height / 2
    var fruit_x = Random.nextInt(width)
    var fruit_y = Random.nextInt(height)
    var direction = ""
    var score = 0
    var x_direction = 0;
    var y_direction = 0;
    var tail_x = new Array[Int](100)
    var tail_y = new Array[Int](100)
    var n_tail = 0
    var prev_x = 0;
    var prev_y = 0;

    val canvas = dom.document.createElement("canvas").asInstanceOf[html.Canvas]
    val target = document.getElementById("score")
    canvas.width = width
    canvas.height = height
    canvas.style.border = "1px solid black"
    canvas.id = "board"

    dom.document.body.appendChild(canvas)
    val p = document.createElement("p")
    val textNode = document.createTextNode(s"Score: $score")
    p.appendChild(textNode)
    target.appendChild(p)
    
    // initializing the game logic

    val context = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]

    def drawSnake(): Unit = {
      context.clearRect(0, 0, width, height)
      context.fillStyle = "black"
      context.fillRect(x, y, 20, 20)
      context.fillStyle = "green"
      for(i <- 1 to n_tail) {
        context.fillRect(tail_x(i), tail_y(i), 20, 20)
      }
      
      prev_x = tail_x(0)
      prev_y = tail_y(0)
      var prev_2_x = 0 
      var prev_2_y = 0
      tail_x(0) = x
      tail_y(0) = y
      for(i <- 1 to n_tail) {
        prev_2_x = tail_x(i)
        prev_2_y = tail_y(i)
        tail_x(i) = prev_x
        tail_y(i) = prev_y
        prev_x = prev_2_x
        prev_y = prev_2_y
      }
    }

    def drawFruit(): Unit = {
      context.fillStyle = "red"
      context.fillRect(fruit_x, fruit_y, 20, 20)
    }

    def updatePosition(): Unit = {
        x+=x_direction
        y+=y_direction
        if(x+20>= fruit_x && x<=fruit_x+20 && y+20>=fruit_y && y<=fruit_y+20) {
          fruit_x = Random.nextInt(width)
          fruit_y = Random.nextInt(height)
          score+=10
          n_tail+=1;
          target.innerHTML = s"Score: $score"
        }
        drawSnake()
        drawFruit()
        if(x>width-20||x<0||y>height-20||y<0) {
          gameOver = true
        }
    }

    drawSnake()
    drawFruit()
    
    dom.window.addEventListener("keydown", (event: dom.KeyboardEvent) => {
      event.keyCode match {
        case 37 => 
          x_direction = -5
          y_direction = 0
          
        case 38 => 
          x_direction = 0
          y_direction = -5
          
        case 39 => 
          x_direction = 5
          y_direction = 0
          
        case 40 => 
          x_direction = 0
          y_direction = 5
          
        case _ => gameOver = true
      }
      if(gameOver==true) {
        println("Game Over!")
      }

      updatePosition()
      
    })
    dom.window.setInterval(() => updatePosition(), 100)
  }
}
