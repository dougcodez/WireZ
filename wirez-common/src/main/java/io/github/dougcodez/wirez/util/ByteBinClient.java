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
package io.github.dougcodez.wirez.util;

import com.google.gson.JsonParser;
import lombok.experimental.UtilityClass;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@UtilityClass
public class ByteBinClient {

    private AtomicReference<String> keyFetcher = new AtomicReference<>();

    public void postRequest(List<String> dataList, Runnable runnable) {
        try {
            URL url = new URL("https://bytebin.lucko.me/post");
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "text/plain");
            connection.setDoOutput(true);
            try (OutputStream stream = connection.getOutputStream()) {
                for (String postData : dataList) {
                    stream.write(postData.getBytes());
                }

                stream.flush();
            }

            BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String key = (new JsonParser()).parse(input).getAsJsonObject().get("key").getAsString();
            keyFetcher.set("https://bytebin.lucko.me/" + key);
            runnable.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getKey() {
        return keyFetcher.get();
    }
}
