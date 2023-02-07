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
package io.github.dougcodez.wirez.library.relocation;

import io.github.dougcodez.wirez.library.Library;
import io.github.dougcodez.wirez.library.LibraryManager;
import io.github.dougcodez.wirez.library.Repositories;
import io.github.dougcodez.wirez.library.classloader.IsolatedClassLoader;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class RelocationHelper {

    /**
     * Reflected constructor for creating new jar relocator instances
     */
    private final Constructor<?> jarRelocatorConstructor;

    /**
     * Reflected method for running a jar relocator
     */
    private final Method jarRelocatorRunMethod;

    /**
     * Reflected constructor for creating relocation instances
     */
    private final Constructor<?> relocationConstructor;

    /**
     * Creates a new relocation helper using the provided library manager to
     * download the dependencies required for runtime relocation.
     *
     * @param libraryManager the library manager used to download dependencies
     */
    public RelocationHelper(LibraryManager libraryManager) {
        requireNonNull(libraryManager, "libraryManager");

        IsolatedClassLoader classLoader = new IsolatedClassLoader();

        // ObjectWeb ASM Commons
        classLoader.addPath(libraryManager.downloadLibrary(
                Library.builder()
                        .groupId("org.ow2.asm")
                        .artifactId("asm-commons")
                        .version("9.2")
                        .checksum("vkzlMTiiOLtSLNeBz5Hzulzi9sqT7GLUahYqEnIl4KY=")
                        .repository(Repositories.MAVEN_CENTRAL)
                        .build()
        ));

        // ObjectWeb ASM
        classLoader.addPath(libraryManager.downloadLibrary(
                Library.builder()
                        .groupId("org.ow2.asm")
                        .artifactId("asm")
                        .version("9.2")
                        .checksum("udT+TXGTjfOIOfDspCqqpkz4sxPWeNoDbwyzyhmbR/U=")
                        .repository(Repositories.MAVEN_CENTRAL)
                        .build()
        ));

        // Luck's Jar Relocator
        classLoader.addPath(libraryManager.downloadLibrary(
                Library.builder()
                        .groupId("me.lucko")
                        .artifactId("jar-relocator")
                        .version("1.5")
                        .checksum("0D6eM99gKpEYFNDydgnto3Df0ygZGdRVqy5ahtj0oIs=")
                        .repository(Repositories.MAVEN_CENTRAL)
                        .build()
        ));

        try {
            Class<?> jarRelocatorClass = classLoader.loadClass("me.lucko.jarrelocator.JarRelocator");
            Class<?> relocationClass = classLoader.loadClass("me.lucko.jarrelocator.Relocation");

            // me.lucko.jarrelocator.JarRelocator(File, File, Collection)
            jarRelocatorConstructor = jarRelocatorClass.getConstructor(File.class, File.class, Collection.class);

            // me.lucko.jarrelocator.JarRelocator#run()
            jarRelocatorRunMethod = jarRelocatorClass.getMethod("run");

            // me.lucko.jarrelocator.Relocation(String, String, Collection, Collection)
            relocationConstructor = relocationClass.getConstructor(String.class, String.class, Collection.class, Collection.class);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Invokes the jar relocator to process the input jar and generate an
     * output jar with the provided relocation rules applied.
     *
     * @param in          input jar
     * @param out         output jar
     * @param relocations relocations to apply
     */
    public void relocate(Path in, Path out, Collection<Relocation> relocations) {
        requireNonNull(in, "in");
        requireNonNull(out, "out");
        requireNonNull(relocations, "relocations");

        try {
            List<Object> rules = new LinkedList<>();
            for (Relocation relocation : relocations) {
                rules.add(relocationConstructor.newInstance(
                        relocation.getPattern(),
                        relocation.getRelocatedPattern(),
                        relocation.getIncludes(),
                        relocation.getExcludes()
                ));
            }

            jarRelocatorRunMethod.invoke(jarRelocatorConstructor.newInstance(in.toFile(), out.toFile(), rules));
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }
}
