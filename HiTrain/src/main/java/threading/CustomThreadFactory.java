/*
 * Copyright (c) 2020.  Kumaran Devaneson
 * Created : 2/ 3/2020
 * All rights reserved
 */

package threading;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomThreadFactory {
    private String namePrefix = null;
    private boolean daemon = false;
    private int priority = Thread.NORM_PRIORITY;

    public CustomThreadFactory setNamePrefix(String namePrefix) {
        this.namePrefix = namePrefix;
        return this;
    }

    public CustomThreadFactory setDaemon(boolean daemon) {
        this.daemon = daemon;
        return this;
    }

    public CustomThreadFactory setPriority(int priority) {
        this.priority = priority;
        return this;
    }

    public ThreadFactory build() {
        return build(this);
    }

    private static ThreadFactory build(CustomThreadFactory builder) {
        final String namePrefix = builder.namePrefix;
        final Boolean daemon = builder.daemon;
        final Integer priority = builder.priority;
        final AtomicInteger count = new AtomicInteger(1);
        return new ThreadFactory() {
            @Override
            public Thread newThread(Runnable runnable) {
                Thread thread = new Thread(runnable);
                if (namePrefix != null) {
                    if (namePrefix.equalsIgnoreCase("WorkerThread"))
                        thread.setName(namePrefix + "-" + count.getAndIncrement());
                    else
                        thread.setName(namePrefix + "-" + count.getAndIncrement());
                }
                if (daemon != null) {
                    thread.setDaemon(daemon);
                }
                if (priority != null) {
                    thread.setPriority(priority);
                }
                return thread;
            }
        };
    }
}
