package de.darkmodz.gastroapp.api;

import java.util.HashMap;

public interface RepositoryCallback<T> {
    void onComplete(HashMap<String, String> result);
}
