package strategypattern

trait QuackBehavior {
  def quack()
}

class Quack() extends QuackBehavior{
  override def quack(): Unit = println("鸭子呱呱叫")
}

class Squeak() extends QuackBehavior{
  override def quack(): Unit = println("鸭子吱吱叫")
}

class MuteQuack() extends QuackBehavior{
  override def quack(): Unit = {}
}