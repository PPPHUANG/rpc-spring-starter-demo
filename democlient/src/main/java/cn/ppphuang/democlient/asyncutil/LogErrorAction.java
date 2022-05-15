package cn.ppphuang.democlient.asyncutil;

import java.util.function.BiConsumer;

public class LogErrorAction<R> extends AbstractLogAction<R> implements BiConsumer<R, Throwable> {
    public LogErrorAction(String methodName, Object... args) {
        super(methodName, args);
    }

    @Override
    public void accept(R result, Throwable throwable) {
        logResult(result, throwable);
    }
}
