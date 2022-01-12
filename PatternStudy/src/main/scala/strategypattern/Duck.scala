package strategypattern

trait Duck {
  var flyBehavior: FlyBehavior
  var quackBehavior: QuackBehavior

  def swim(): Unit ={
    println("can swim")
  }
  def display()

  def setFlyBehavior(flyBehavior: FlyBehavior)
  def setQuackBehavior(quackBehavior: QuackBehavior)

  def performanceFly()
  def performanceQuack()
}

class ModelDuck extends Duck{
  override var flyBehavior: FlyBehavior = new FlyNoWay()
  override var quackBehavior: QuackBehavior = new MuteQuack()

  override def swim(): Unit = super.swim()

  override def display(): Unit = println("model duck")

  override def setFlyBehavior(flyBehavior: FlyBehavior): Unit = {
    this.flyBehavior = flyBehavior
  }

  override def setQuackBehavior(quackBehavior: QuackBehavior): Unit = {
    this.quackBehavior = quackBehavior
  }

  override def performanceFly(): Unit =  flyBehavior.fly()

  override def performanceQuack(): Unit = quackBehavior.quack()
}

object TestDuck{
  def main(args: Array[String]): Unit = {
    val duck: Duck = new ModelDuck
    duck.performanceFly()
    duck.performanceQuack()
  }
}