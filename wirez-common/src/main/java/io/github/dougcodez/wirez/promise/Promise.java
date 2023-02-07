/**
 * MIT License
 *
 * Copyright (c) 2022-2023 Douglas (dougcodez)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package io.github.dougcodez.wirez.promise;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Function;

public class Promise<T> extends PromiseSupport<T> {

    private Runnable fulfillmentAction;
    private Consumer<? super Throwable> exceptionHandler;

    public Promise() {
    }

    public static <T> Promise<T> createNew() {
        return new Promise<>();
    }

    @Override
    public void fulfill(T value) {
        super.fulfill(value);
        postFulfillment();
    }

    @Override
    public void fulfillExceptionally(Exception exception) {
        super.fulfillExceptionally(exception);
        handleException(exception);
        postFulfillment();
    }

    private void handleException(Exception exception) {
        if (exceptionHandler == null) {
            return;
        }
        exceptionHandler.accept(exception);
    }

    private void postFulfillment() {
        if (fulfillmentAction == null) {
            return;
        }
        fulfillmentAction.run();
    }

    public Promise<T> fulfillInAsync(final Callable<T> task, Executor executor) {
        executor.execute(() -> {
            try {
                fulfill(task.call());
            } catch (Exception ex) {
                fulfillExceptionally(ex);
            }
        });
        return this;
    }

    public Promise<T> fulfillInAsync(final T value, Executor executor) {
        executor.execute(() -> {
            try {
                fulfill(value);
            } catch (Exception ex) {
                fulfillExceptionally(ex);
            }
        });
        return this;
    }

    public Promise<Void> thenAccept(Consumer<? super T> action) {
        var dest = new Promise<Void>();
        fulfillmentAction = new ConsumeAction(this, dest, action);
        return dest;
    }

    public Promise<T> onError(Consumer<? super Throwable> exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
        return this;
    }

    public <V> Promise<V> thenApply(Function<? super T, V> func) {
        Promise<V> dest = new Promise<>();
        fulfillmentAction = new TransformAction<>(this, dest, func);
        return dest;
    }

    private class ConsumeAction implements Runnable {

        private final Promise<T> src;
        private final Promise<Void> dest;
        private final Consumer<? super T> action;

        private ConsumeAction(Promise<T> src, Promise<Void> dest, Consumer<? super T> action) {
            this.src = src;
            this.dest = dest;
            this.action = action;
        }

        @Override
        public void run() {
            try {
                action.accept(src.get());
                dest.fulfill(null);
            } catch (Throwable throwable) {
                dest.fulfillExceptionally((Exception) throwable.getCause());
            }
        }
    }

    private class TransformAction<V> implements Runnable {

        private final Promise<T> src;
        private final Promise<V> dest;
        private final Function<? super T, V> func;

        private TransformAction(Promise<T> src, Promise<V> dest, Function<? super T, V> func) {
            this.src = src;
            this.dest = dest;
            this.func = func;
        }

        @Override
        public void run() {
            try {
                dest.fulfill(func.apply(src.get()));
            } catch (Throwable throwable) {
                dest.fulfillExceptionally((Exception) throwable.getCause());
            }
        }
    }
}