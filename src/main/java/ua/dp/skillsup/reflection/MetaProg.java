package ua.dp.skillsup.reflection;

import java.lang.reflect.InvocationTargetException;

public class MetaProg {
    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Executor.execute("ua.dp.skillsup.reflection");
    }

    @Override
    @Deprecated
    public String toString() {
        return "MetaProg{}";
    }
}
