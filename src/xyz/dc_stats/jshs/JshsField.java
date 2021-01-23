package net.foreverdevs.jshs;

import java.lang.reflect.Field;

public class JshsField implements JshsItem {
    Field field;
    Object parent;
    public JshsField(Field field, Object parent){
        this.field = field;
        this.parent = parent;
    }
    @Override
    public Object get() {
        try {
            return field.get(parent);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }
}
