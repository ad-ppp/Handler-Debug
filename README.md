## Handler
1. 主要演示了MessageQueue的消息屏障作用
2. 演示异步消息的行为模式。

---

log,Android-9.0
```
2020-05-08 15:12:29.676 20741-20768/com.jacky.handler.debug D/HandlerDemo: handle=>1
2020-05-08 15:12:29.877 20741-20768/com.jacky.handler.debug D/HandlerDemo: handle=>2
2020-05-08 15:12:30.077 20741-20768/com.jacky.handler.debug D/HandlerDemo: handle=>3
2020-05-08 15:12:30.276 20741-20769/com.jacky.handler.debug W/y.handler.debu: Accessing hidden method Landroid/os/MessageQueue;->postSyncBarrier()I (light greylist, reflection)
2020-05-08 15:12:30.276 20741-20769/com.jacky.handler.debug D/HandlerDemo: invoke sync barrier success
2020-05-08 15:12:30.277 20741-20768/com.jacky.handler.debug D/HandlerDemo: handle=>4
2020-05-08 15:12:32.279 20741-20770/com.jacky.handler.debug W/y.handler.debu: Accessing hidden method Landroid/os/MessageQueue;->removeSyncBarrier(I)V (light greylist, reflection)
2020-05-08 15:12:32.280 20741-20770/com.jacky.handler.debug D/HandlerDemo: invoke remove syncBarrier success
2020-05-08 15:12:32.281 20741-20768/com.jacky.handler.debug D/HandlerDemo: handle=>6
2020-05-08 15:12:32.281 20741-20768/com.jacky.handler.debug D/HandlerDemo: handle=>7
2020-05-08 15:12:32.281 20741-20768/com.jacky.handler.debug D/HandlerDemo: handle=>8
2020-05-08 15:12:32.282 20741-20768/com.jacky.handler.debug D/HandlerDemo: handle=>9
2020-05-08 15:12:32.282 20741-20768/com.jacky.handler.debug D/HandlerDemo: handle=>10
2020-05-08 15:12:32.282 20741-20768/com.jacky.handler.debug D/HandlerDemo: handle=>11
2020-05-08 15:12:32.282 20741-20768/com.jacky.handler.debug D/HandlerDemo: handle=>12
2020-05-08 15:12:32.282 20741-20768/com.jacky.handler.debug D/HandlerDemo: handle=>13
2020-05-08 15:12:32.282 20741-20768/com.jacky.handler.debug D/HandlerDemo: handle=>14
2020-05-08 15:12:32.477 20741-20768/com.jacky.handler.debug D/HandlerDemo: handle=>15
2020-05-08 15:12:32.678 20741-20768/com.jacky.handler.debug D/HandlerDemo: handle=>16
2020-05-08 15:12:32.878 20741-20768/com.jacky.handler.debug D/HandlerDemo: handle=>17
2020-05-08 15:12:33.078 20741-20768/com.jacky.handler.debug D/HandlerDemo: handle=>18
2020-05-08 15:12:33.278 20741-20768/com.jacky.handler.debug D/HandlerDemo: handle=>19
2020-05-08 15:12:39.283 20741-20768/com.jacky.handler.debug D/HandlerDemo: handle=>5
```