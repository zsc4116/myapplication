package com.example.zhoushicheng.myapplication.AnnotationSample;

import java.lang.reflect.Field;

/**
 * Created by zhoushicheng on 2017/11/7.
 */
@MyAnnotation(metaAnnotation = @MetaAnnotation(birthday = "1987"), color = "red", array = {23, 26})
public class AnnotationTest {
    @MyAnnotation(number = "123456")
    private String mynumber;

    public void setMynumber(String mynumber) {
        this.mynumber = mynumber;
    }

    public static void main(String[] args) {
        AnnotationTest annotationTest = new AnnotationTest();
        annotationTest.setMynumber("987");
        if (AnnotationTest.class.isAnnotationPresent(MyAnnotation.class)) {
            MyAnnotation annotation = (MyAnnotation) AnnotationTest.class.getAnnotation(MyAnnotation.class);
            System.out.println(annotation.color());
            System.out.println(annotation.value());
            int[] arrs = annotation.array();
            for (int arr : arrs) {
                System.out.println(arr);
            }
            Gender gender = annotation.gender();
            System.out.println(gender);
            MetaAnnotation meta = annotation.metaAnnotation();
            System.out.println(meta.birthday());
            System.out.println(annotation.number());

            Field[] fields = AnnotationTest.class.getDeclaredFields();
            if (fields != null && fields.length != 0) {
                for (Field field : fields) {
                    if (field.isAnnotationPresent(MyAnnotation.class)) {
                        field.setAccessible(true);
                        try {
                            System.out.println("field : " + field.getName() + " , " + field.get(annotationTest).toString());
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }


}
