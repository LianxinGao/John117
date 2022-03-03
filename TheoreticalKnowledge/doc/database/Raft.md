## Raft: A Consensus Algorithm
1. 算法一共三种角色：
    - Leader
    - Follower
    - Candidate
2. Leader选举（以三个节点ABC为例）
   1. 三种状态
      1. 都分别投给自己（每个节点随机休息一段时间后重新发起投票，最先恢复投票的节点将获得所有其他选票，因为其他节点还在休息，没法投自己）
      2. 都投给A，选举成功。
      3. AB都投给C，多数票在C，选举成功。
3. Leader节点对一致性的影响
   1. Leader的可用性保证集群数据的一致性。
   2. 数据的流向只能从Leader到Follower。
   3. 数据提交步骤
      1. Client向Leader发送数据，Leader收到后，Leader数据处于未提交状态（Uncommitted）
      2. Leader并发的向所有Follower节点复制数据并等待接收响应，确保多数节点已收到数据后再向Client确认数据已接收。
      3. 一旦向Client发送ACK响应后，Leader数据进入已提交状态（Committed），Leader再向所有Follower发送通告数据已提交。 
- - -
### 数据提交不同阶段保障一致性的方法
1. 数据到达Leader前，Leader挂掉：
   1. 不影响一致性。
2. 数据到达Leader，未复制到Follower，Leader挂掉：
   1. 数据处于Uncommitted状态，Client不会收到ACK响应，会认为超时失败可以安全发起重试。
   2. Follower没有数据，可以重新发起选举，重选后Client提交数据成功。
   3. 原Leader节点恢复后作为Follower加入集群，并从新Leader同步数据，强制保持数据一致。
3. 数据到达Leader，复制到**所有**Follower，但Follower未向Leader响应接收时，Leader挂掉：
   1. 虽然Leader挂了，但follower中的数据是一致的，重新选举后可以完成数据提交（重新选举时若leader恢复，但它没有最新的数据，不会获得投票）
   2. Client不知数据是否提交，发起重试提交。
   3. Raft需要执行内部去重机制（从日志中读取指令，保证按照相同顺序执行指令后达到的状态是一致的）
4. 数据到达Leader，复制到**部分**Follower，但Follower未向Leader响应接收时，Leader挂掉：
   1. Follower间进行投票，票只会落在有最新数据的follower之间。
   2. 成为新Leader的节点强制同步数据到所有节点，数据不会丢失。
5. 数据到达Leader，Leader成功复制数据到多数/所有节点，Leader数据状态变为Committed，但Follower仍然Uncommitted时，leader挂掉：
   1. 重新选举leader后，处理流程如3和4。
6. 集群数据都是committed状态，但Client未收到响应：
   1. 不影响一致性。
7. 网络分区脑裂，出现双leader，情况之一：
   1. 原leader独自一个分区，数据永远无法提交成功。
   2. Client切换leader后提交数据成功
   3. 网络分区恢复后，原leader发现有更高级的term，自动降级为follower并从新leader同步数据，达到一致性。
- - -
### 线性一致性读
在分布式系统中实现类似[Java volatile](../Java/Volatile关键字.md)的语义。
1. ReadIndex Read
   1. Leader将自己的commitIndex记录到local变量ReadIndex里面。
   2. 向所有follower发送heart beat（超过半数确认），确认自己是leader后。
   3. leader等待自己的状态机执行到ReadIndex记录的long，直到applyIndex超过ReadIndex，就能提供线性一致性读，不用管leader是否飘走。
   4. leader执行read，返回给client。
2. Follower Read
   1. follower向leader请求最新的readIndex
   2. leader走一遍【1】中的前三步（确认自己是leader）然后把readIndex发送给follower
   3. follower等待自己的状态机applyIndex超过readIndex
   4. follower执行read，返回给client。
3. Lease Read（优化ReadIndex Read的heart beat）
   1. 发生leader选举出现在election timeout的时候，所以leader在一次心态响应后的election timeout时间内会一直是leader，这段时间内可以直接进行读取。
   2. 流程
      1. leader heart beat获取多数响应
      2. 租约有效期内，认为是唯一leader，忽略ReadIndex Read中的第二步心跳检测
      3. Leader 等待自己的状态机执行，直到 applyIndex 超过 ReadIndex，这样就能够安全的提供线性一致性读。