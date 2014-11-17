package eu.matejkormuth.pexel.commons;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Class that conitains JSON provider.
 */
public class Providers {
    /**
     * Global network JSON provider.
     */
    public static final Gson JSON = new GsonBuilder().setPrettyPrinting().create();
}
