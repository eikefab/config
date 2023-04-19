package com.eikefab.config.bukkit.autoupdater.github;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.Okio;

import java.io.File;

public class Asset {

    private static final OkHttpClient HTTP_CLIENT = new OkHttpClient();

    private final String id;
    private final String name;
    private final String author;
    private final String url;

    public Asset(String id, String name, String author, String url) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getUrl() {
        return url;
    }

    public File download(File folder, String token) {
        final File file = new File(folder, name);

        if (file.exists()) {
            return file;
        }

        final Request.Builder requestBuilder = new Request.Builder()
                .url(url)
                .get();

        if (token != null) {
            requestBuilder.addHeader("Authorization", "token " + token);
        }

        try (Response response = HTTP_CLIENT.newCall(requestBuilder.build()).execute()) {
            if (!response.isSuccessful()) return file;

            final ResponseBody body = response.body();
            if (body == null) {
                return file;
            }

            try (BufferedSink sink = Okio.buffer(Okio.sink(file))) {
                sink.writeAll(body.source());
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return file;
    }

}
