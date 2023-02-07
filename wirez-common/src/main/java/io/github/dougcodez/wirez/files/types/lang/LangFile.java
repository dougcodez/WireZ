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
package io.github.dougcodez.wirez.files.types.lang;

import dev.dejvokep.boostedyaml.YamlDocument;
import io.github.dougcodez.wirez.files.AbstractConfigFile;

import java.io.File;

public class LangFile extends AbstractConfigFile {

    private static LangFile instance = null;

    public static LangFile getInstance() {
        if (instance == null) {
            instance = new LangFile();
        }
        return instance;
    }

    public void initLangFile(File destinationFile) {
        initFile(destinationFile, "messages.yml");
    }

    @Override
    public void initData() {
        YamlDocument config = getConfigFile();
        if (config == null) return;
        if (config.getKeys().size() != 0) return;
        for (Lang item : Lang.CACHE) {
            config.set(item.getKey(), item.getValue());
        }
    }
}
