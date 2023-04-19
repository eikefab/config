package com.eikefab.config.bukkit.autoupdater.github;

import com.eikefab.config.bukkit.autoupdater.github.json.AssetDeserializer;
import com.eikefab.config.bukkit.autoupdater.github.json.ReleaseDeserializer;
import com.eikefab.config.bukkit.autoupdater.github.json.RepositoryDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.util.List;

public class Repository {

    private static final String REPOSITORY_RELEASES_URL = "https://api.github.com/repos/%s/releases/";
    private static final OkHttpClient HTTP_CLIENT = new OkHttpClient();
    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(Repository.class, new RepositoryDeserializer())
            .registerTypeAdapter(Release.class, new ReleaseDeserializer())
            .registerTypeAdapter(Asset.class, new AssetDeserializer())
            .create();

    private final List<Release> releases;

    public Repository(List<Release> releases) {
        this.releases = releases;
    }

    public List<Release> getReleases() {
        return releases;
    }

    public Release getLatestRelease() {
        return getReleases().get(0);
    }

    public static Repository query(String fullName, String token) {
        final Request.Builder builder = new Request.Builder()
                .url(String.format(REPOSITORY_RELEASES_URL, fullName))
                .get();

        if (token != null) {
            builder.addHeader("Authorization", "token " + token);
        }

        final Request request = builder.build();

        try (Response response = HTTP_CLIENT.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                return null;
            }

            final ResponseBody body = response.body();

            if (body == null) {
                return null;
            }

            return GSON.fromJson(body.string(), Repository.class);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return null;
    }

}
