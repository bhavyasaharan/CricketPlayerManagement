package com.example.demo.general;

import java.lang.reflect.Field;

public class GeneralMethods {


        public static <T> T copyObject(Object source, T target) {
            if (source == null || target == null) return target;

            Class<?> sourceClass = source.getClass();
            Class<?> targetClass = target.getClass();

            try {
                while (sourceClass != null) {
                    for (Field sourceField : sourceClass.getDeclaredFields()) {
                        sourceField.setAccessible(true);

                        Class<?> tempTargetClass = targetClass;
                        while (tempTargetClass != null) {
                            try {
                                Field targetField = tempTargetClass.getDeclaredField(sourceField.getName());
                                targetField.setAccessible(true);

                                if (targetField.getType().equals(sourceField.getType())) {
                                    targetField.set(target, sourceField.get(source));
                                }

                                break; // Stop after setting
                            } catch (NoSuchFieldException e) {
                                tempTargetClass = tempTargetClass.getSuperclass();
                            }
                        }
                    }
                    sourceClass = sourceClass.getSuperclass();
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            return target;
        }
    }



