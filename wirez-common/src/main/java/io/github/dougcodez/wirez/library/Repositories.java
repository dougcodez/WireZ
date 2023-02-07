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
package io.github.dougcodez.wirez.library;

public class Repositories {

    /**
     * Maven Central repository URL.
     */
    public static final String MAVEN_CENTRAL = "https://repo1.maven.org/maven2/";

    /**
     * Sonatype OSS repository URL.
     */
    public static final String SONATYPE = "https://oss.sonatype.org/content/groups/public/";

    /**
     * Bintray JCenter repository URL.
     */
    public static final String JCENTER = "https://jcenter.bintray.com/";

    /**
     * JitPack repository URL.
     */
    public static final String JITPACK = "https://jitpack.io/";

    private Repositories() {
        throw new UnsupportedOperationException("Private constructor");
    }
}
