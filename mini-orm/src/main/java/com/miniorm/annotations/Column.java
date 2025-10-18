package com.miniorm.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD) // Indica que esta anotación se aplica a campos (atributos)
public @interface Column {
    String name();
}
