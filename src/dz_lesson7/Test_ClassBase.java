package dz_lesson7;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class Test_ClassBase {
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface beforeSuite{
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface afterSuite{
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface Testing{
        int level() default 5;
    }

    @beforeSuite
    public void befor() {
        System.out.println("Подготовка к тестированию");
    }

    @afterSuite
    public void after() {
        System.out.println("Тестирование закончено");
    }

}
