package com.github.niqdev.old.internal

// https://github.com/jdegoes/lambdaconf-2014-introgame#the-state-monad
// https://github.com/alvinj/StateMonadExample/blob/master/src/main/scala/state_monad/State.scala
// https://earldouglas.com/talks/state-monad/slides.html
case class State[S, A](run: S => (S, A)) {

  def flatMap[B](g: A => State[S, B]): State[S, B] =
    State { s0: S =>
      val (s1, a) = run(s0)
      g(a).run(s1)
    }

  def map[B](f: A => B): State[S, B] =
    flatMap(a => State.point(f(a)))
}

object State {
  def point[S, A](value: A): State[S, A] =
    State(run = s => (s, value))
}