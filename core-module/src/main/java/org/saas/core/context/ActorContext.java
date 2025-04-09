package org.saas.core.context;

public class ActorContext {
    private static final ThreadLocal<String> currentActor = new ThreadLocal<>();

    public static void setActor(String actor) {
        System.out.println("🟢 Actor set: " + actor);
        currentActor.set(actor);
    }

    public static String getActor() {
        return currentActor.get();
    }

    public static void clear() {
        System.out.println("🔴 Actor cleared");
        currentActor.remove();
    }
}