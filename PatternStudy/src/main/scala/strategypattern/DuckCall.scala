package strategypattern

// 针对不同的鸭子，产生不同的鸭鸣器
class DuckCall extends QuackBehavior {
  private var duckType: Duck = _

  override def quack(): Unit = duckType.performanceQuack()

  def setQuackType(duck: Duck) {
    duckType = duck
  }
}
