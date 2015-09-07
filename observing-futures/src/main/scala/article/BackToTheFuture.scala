package article

import java.util.concurrent.CountDownLatch

import rx.lang.scala.Observable

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._
import scala.concurrent.duration._

/**
 * @author Foat Akhmadeev
 *         07/09/15
 */
object BackToTheFuture extends App {
  val intervals: Observable[Long] = Observable.interval(100 millis).take(10)

  intervals subscribe {
    v => println(s"value = $v")
  }
  Await.result(Future { Thread.sleep(1500) }, 2 seconds)

  Observable.just(5, 4, 2).subscribe(print(_))
  List(5, 4, 2).foreach(print(_))

  println()

  val asyncEmulation = Observable
    .just(1, 2, 3)
    .map(e => e + 1)
    .flatMap(e => Observable.from(Future { Thread.sleep(400 - 100 * e); e }))

  val cd = new CountDownLatch(3)
  asyncEmulation subscribe {
    v => println(s"received = $v"); cd.countDown()
  }
  cd.await()
}

