package strategypattern

trait FlyBehavior {
  def fly()
}

class FlyWithWings() extends FlyBehavior{
  override def fly(): Unit = println("鸭子飞起来了")
}

class FlyNoWay extends FlyBehavior{
  override def fly(): Unit = {}
}