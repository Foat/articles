package article

import scala.scalajs.js
import scala.scalajs.js.annotation.JSExport
import org.scalajs.dom.{Node, html}
import org.scalajs.dom

import rx._

import scala.language.implicitConversions

case class Point(x: Int, y: Int) {
  def +(p: Point) = Point(x + p.x, y + p.y)
  def -(p: Point) = Point(x - p.x, y - p.y)
  def /(d: Int) = Point(x / d, y / d)
}

@JSExport
object MousePosition {

  implicit def rxNode[T](r: Rx[T]): Node = {
    def rSafe: dom.Node = {
      val node = dom.document.createElement("div")
      node.textContent = r().toString
      node
    }
    var last = rSafe
    Obs(r, skipInitial = true) {
      val newLast = rSafe
      js.Dynamic.global.last = last
      last.parentNode.replaceChild(newLast, last)
      last = newLast
    }
    last
  }

  @JSExport
  def start(div: html.Div, canvas: html.Canvas): Unit = {
    val h = dom.window.innerHeight
    val w = dom.window.innerWidth
    canvas.height = h
    canvas.width = w

    // default rectangle size
    val s = 10
    val half = Point(s, s) / 2

    val ctx = canvas.getContext("2d")
      .asInstanceOf[dom.CanvasRenderingContext2D]

    val mousePos = Var(Point(0, 0))

    val center = Rx { mousePos() - half }

    Obs(center, skipInitial = true) {
      ctx.fillStyle = "gray"
      ctx.fillRect(center().x, center().y, s, s)
    }

    def clear = {
      ctx.fillStyle = "white"
      ctx.fillRect(0, 0, w, h)
    }

    div.appendChild(mousePos)
    div.appendChild(center)

    dom.window.onkeypress = { e: dom.KeyboardEvent => if (e.keyCode == 27) clear }
    dom.window.onmousemove = { e: dom.MouseEvent => mousePos() = Point(e.pageX.toInt, e.pageY.toInt) }
  }
}