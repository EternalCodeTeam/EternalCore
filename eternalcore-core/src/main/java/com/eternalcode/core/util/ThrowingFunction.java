package com.eternalcode.core.util;

/*
 * Copyright (c) 2021 dzikoysk
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.function.Function;

@FunctionalInterface
public interface ThrowingFunction<T, R, E extends Throwable> {

    static <T, S extends Exception> ThrowingFunction<T, T, S> identity() {
        return t -> t;
    }
    /**
     * Map throwing function in default stream api with exception consume support
     *
     * @param function         the function to apply
     * @param exceptionHandler the exception consumer
     * @param <T>              type of function parameter
     * @param <R>              type of function result
     * @param <E>              type of exception
     * @return a new function
     */
    @SuppressWarnings("unchecked")
    static <T, R, E extends Throwable> Function<T, R> asFunction(
        ThrowingFunction<T, R, E> function,
        Function<E, R> exceptionHandler) {
        return value -> {
            try {
                return function.apply(value);
            }
            catch (Throwable exception) {
                return exceptionHandler.apply((E) exception);
            }
        };
    }
    R apply(T t) throws E;
}
