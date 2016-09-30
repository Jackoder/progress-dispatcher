[![JitPack](https://jitpack.io/v/Jackoder/progress-dispatcher.svg)](https://jitpack.io/#Jackoder/progress-dispatcher)
[![Travis CI Status](https://travis-ci.org/Jackoder/progress-dispatcher.svg?branch=master)](https://travis-ci.org/Jackoder/progress-dispatcher.svg?branch=master)
[![Coverage Status](https://coveralls.io/repos/github/Jackoder/progress-dispatcher/badge.svg?branch=master)](https://coveralls.io/github/Jackoder/progress-dispatcher?branch=master)

# ProgressDispatcher
进度分发器

特性
-------

* 监听指定ID对象的进度事件与异常事件
* 支持Listener与Observable的监听方式

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
### 初始化

`init` 方法的入参用于指定 `OnProgressListener` 执行所在的线程，默认为主线程。该方法非必须，调用 `ProgressDispatcher.getInstance()` 时会自动初始化。

```java
ProgressDispatcher.init(@Nullable android.os.Handler listenerHandler);
```

**注意** 多次调用无效，仅第一次调用有效，或调用 `release` 方法之后可再次初始化。

### Listener 监听

```java
OnProgressListener mOnProgressListener = new OnProgressListener() {

	@Override
	public void onProgress(String id, int progress, Object context) {

	}

	@Override
	public void onError(String id, Throwable throwable) {

	}
};

//add listener
ProgressDispatcher.getInstance().addOnProgressListener(id, mOnProgressListener);
//remove listener
ProgressDispatcher.getInstance().removeOnProgressListener(id, mOnProgressListener));
```

**注意** 监听会在 `release` 时清空，但也建议在不需要时手动移除监听。

### Observable 监听

```java
ProgressDispatcher.getInstance().getProgressObservable(id)
	.subscribe(new Action1<Progress>() {
		@Override
		public void call(Progress progress) {
			//do something
		}
	}, new Action1<Throwable>() {
		@Override
		public void call(Throwable throwable) {
			//do something
		}
	});
```

### Observer 通知

```java
ProgressDispatcher.getInstance().getProgressObserver(id).onNext(new Progress());
ProgressDispatcher.getInstance().getProgressObserver(id).onError(new Exception());
```
**注意** Observer执行 `unsubscribe` 之后 Observable 便无法再接收到事件，而Listener仍然可以。若需要继续监听可重新获取 Observable 对象。

### 释放

释放后所有 Listener 被移除，Observable 也无法接收到事件。

```java
ProgressDispatcher.getInstance().release();
```

其它
-------

**适用场景**

- 业务中存在进度上报模块，需要接收各模块的进度事件。为了解耦，通过监听抽象化的进度事件进行上报处理。
- 下载器中需要监听下载任务的进度事件，通过统一的通知和监听，开发起来更简便。

问题
-------

**单例设计是否合理**

目前单例设计为了方便各模块统一调度，更加简便。后续会考虑支持非单例的调度模式。

**跨进程**

目前还不支持，后续考虑添加。

**Bug与建议**

欢迎提交提交Bug与建议到 [Issues](https://github.com/Jackoder/progress-dispatcher/issues) 中。

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
