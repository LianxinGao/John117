package org.glx.designpattern.strategy.change.action

/**
 * @program: John117
 * @description:
 * @author: LiamGao
 * @create: 2022-02-24 18:54
 */
trait FlyBehavior {
  def fly()
}
class FlyWithWings extends FlyBehavior{
  override def fly(): Unit = println("fly with wings ! ")
}
class FlyNoWay extends FlyBehavior{
  override def fly(): Unit = println("can't fly ! ")
}
///////////////////////////////////////////////////////////////////////////
trait QuackBehavior {
  def quack()
}
class Quack extends QuackBehavior{
  override def quack(): Unit = println("quack quack quack...")
}
class Squeak extends QuackBehavior{
  override def quack(): Unit = println("squeak squeak squeak...")
}
class MuteQuack extends QuackBehavior{
  override def quack(): Unit = println("mute quack ......")
}
