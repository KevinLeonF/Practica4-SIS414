package com.miniorm.mini_orm_spring.annotations;

import com.miniorm.mini_orm_spring.enums.GenerationType;

import  java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface GeneratedValue {
    GenerationType strategy() default GenerationType.AUTO_INCREMENT;
}