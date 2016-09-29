[![](https://jitpack.io/v/Jackoder/progress-dispatcher.svg)](https://jitpack.io/#Jackoder/progress-dispatcher)
[![](https://travis-ci.org/Jackoder/progress-dispatcher.svg?branch=master)](https://travis-ci.org/Jackoder/progress-dispatcher.svg?branch=master)

# ProgressDispatcher
进度分发器

特性
-------

* 监听指定ID任务的进度事件和异常事件
* 支持Listener与Observable的监听方式
* 待补充

添加依赖
-------
 
配置仓库
```gradle
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```

配置依赖
```gradle
dependencies {
    compile 'com.github.Jackoder:progress-dispatcher:1.0'
}
```

混淆配置
-------

```proguard
# RxJava
-dontwarn sun.misc.**

-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
   long producerIndex;
   long consumerIndex;
}

-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}

-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}
```

使用
-------

其它
-------

**要点一**

说明

**要点二**

说明

问题
-------

**问题一**

解答

**问题二**

解答

**Bug与建议**

欢迎提交提交Bug与建议到 [Issues](https://github.com/Jackoder/ProjectTemplate/issues) 中。

License
-------

    Copyright 2016 Jackoder

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
