package org.glx.designpattern.strategy.unchange

import org.glx.designpattern.strategy.change.action.{FlyBehavior, QuackBehavior}

/**
 * @program: John117
 * @description:
 * @author: LiamGao
 * @create: 2022-02-24 18:52
 */
class Duck() {
  var flyBehavior: FlyBehavior = _
  var quackBehavior: QuackBehavior = _
  def performanceFly(): Unit ={
    flyBehavior.fly()
  }
  def performanceQuack(): Unit ={
    quackBehavior.quack()
  }
  def swim(): Unit ={
    println("swimming......")
  }
  def display(): Unit ={
    println("duck...")
  }
}

class MallardDuck(flyBehavior: FlyBehavior, quackBehavior: QuackBehavior) extends Duck{

}