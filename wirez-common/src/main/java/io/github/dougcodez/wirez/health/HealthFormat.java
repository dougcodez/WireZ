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
package io.github.dougcodez.wirez.health;

import lombok.experimental.UtilityClass;

import java.util.concurrent.atomic.AtomicReference;

@UtilityClass
public class HealthFormat {


    public String format(Number valueType) {
        int dataPoint = valueType.intValue();
        AtomicReference<StringBuilder> dataText = new AtomicReference<>();
        if (dataPoint >= 0 && dataPoint <= 10) {
            dataText.set(new StringBuilder().append("&a⚫").append("&7").append("⚫".repeat(9)));
        } else if (dataPoint >= 11 && dataPoint <= 20) {
            dataText.set(new StringBuilder().append("&a").append("⚫".repeat(2)).append("&7").append("⚫".repeat(8)));
        } else if (dataPoint >= 21 && dataPoint <= 30) {
            dataText.set(new StringBuilder().append("&a").append("⚫".repeat(3)).append("&7").append("⚫".repeat(7)));
        } else if (dataPoint >= 31 && dataPoint <= 40) {
            dataText.set(new StringBuilder().append("&e").append("⚫".repeat(4)).append("&7").append("⚫".repeat(6)));
        } else if (dataPoint >= 41 && dataPoint <= 50) {
            dataText.set(new StringBuilder().append("&e").append("⚫".repeat(5)).append("&7").append("⚫".repeat(5)));
        } else if (dataPoint >= 51 && dataPoint <= 60) {
            dataText.set(new StringBuilder().append("&e").append("⚫".repeat(6)).append("&7").append("⚫".repeat(4)));
        } else if (dataPoint >= 61 && dataPoint <= 70) {
            dataText.set(new StringBuilder().append("&e").append("⚫".repeat(7)).append("&7").append("⚫".repeat(3)));
        } else if (dataPoint >= 71 && dataPoint <= 80) {
            dataText.set(new StringBuilder().append("&c").append("⚫".repeat(8)).append("&7").append("⚫".repeat(2)));
        } else if (dataPoint >= 81 && dataPoint <= 90) {
            dataText.set(new StringBuilder().append("&4").append("⚫".repeat(9)).append("&7").append("⚫"));
        } else if (dataPoint >= 91 && dataPoint <= 100) {
            dataText.set(new StringBuilder().append("&4").append("⚫".repeat(10)));
        }

        return "&8&l|" + dataText + "&8&l|";
    }
}
