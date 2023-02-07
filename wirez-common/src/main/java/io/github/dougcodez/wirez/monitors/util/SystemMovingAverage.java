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
package io.github.dougcodez.wirez.monitors.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayDeque;
import java.util.Queue;

public class SystemMovingAverage {

    private final Queue<BigDecimal> samples;
    private final int period;
    private BigDecimal sum = BigDecimal.ZERO;

    public SystemMovingAverage(int period) {
        assert period > 0 : "Period must be a positive integer";
        this.period = period;
        this.samples = new ArrayDeque<>(this.period + 1);
    }

    public void add(BigDecimal num) {
        sum = sum.add(num);
        samples.add(num);
        if (samples.size() > period) {
            sum = sum.subtract(samples.remove());
        }
    }

    public double getAverage() {
        synchronized (this) {
            if (samples.isEmpty()) return 0.0; // technically the average is undefined
            BigDecimal divisor = BigDecimal.valueOf(samples.size());
            return sum.divide(divisor, 30, RoundingMode.HALF_UP).doubleValue();
        }
    }
}
