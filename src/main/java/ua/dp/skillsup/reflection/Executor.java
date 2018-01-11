package ua.dp.skillsup.reflection;

import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.*;
import java.util.Map;
import java.util.Set;


public class Executor {

    public static void execute(String packageName) {
        Reflections ref = new Reflections(packageName, new MethodAnnotationsScanner());
        Set<Method> annotated = ref.getMethodsAnnotatedWith(Execute.class);

        for(Method method:annotated){
            try {
                method.invoke(method.getDeclaringClass().newInstance(), getSystemParamLabeledAnnotationEvn(method));
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException();
            }
        }
    }

    public static String getSystemParamLabeledAnnotationEvn (Method method){
        Annotation annotations[][] = method.getParameterAnnotations();

        for (Annotation annotation[] : annotations) {
            for (Annotation parameterAnnotation : annotation) {
                if (parameterAnnotation instanceof Evn){
                    Evn evn = (Evn) parameterAnnotation;
                    Map<String, String> environment = System.getenv();
                    return environment.get(evn.value());
                }
            }
        }
        return "";
    }
}
