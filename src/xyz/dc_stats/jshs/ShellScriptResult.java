package xyz.dc_stats.jshs;

public class ShellScriptResult {
    private Class<?>[] cls;
    private Type type;
    private Exception exception;
    public ShellScriptResult(Class[] cls, Type type){
        switch(type){
            case IMPORT_SUCCESSFUL:
            case IMPORT_FAILED_MORE_CLASSES:
                break;
            default:
                throw new IllegalStateException("Type "+type.toString()+" isn't allowed to use at this Constructor.");
        }
        this.cls = cls;
        this.type = type;
    }
    public ShellScriptResult(Exception exception, Type type){
        this.exception = exception;
        this.type = type;
    }
    public ShellScriptResult(Type type){
        this.type = type;
        switch(type){
            case COMMAND_EMPTY:
            case IMPORT_FAILED_NO_CLASS:
            case UNKNOWN_SYNTAX:
                break;
            default:
                throw new IllegalStateException("Type "+type.toString()+" isn't allowed to use at this Constructor.");
        }
    }
    public boolean isType(Type type){
        return this.type == type;
    }
    public Class<?>[] getClasses() {
        switch (type){
            case IMPORT_FAILED_MORE_CLASSES:
                break;
            default:
                throw new IllegalStateException();
        }
        return cls;
    }
    public Class<?> getClazz(){
        switch (type){
            case IMPORT_SUCCESSFUL:
                break;
            default:
                throw new IllegalStateException();
        }
        return cls[0];
    }

    public Exception getException() {
        switch (type){
            case EXCEPTION_THROWN:
                break;
            default:
                throw new IllegalStateException();
        }
        return exception;
    }

    public enum Type{
        COMMAND_EMPTY,
        UNKNOWN_SYNTAX,
        IMPORT_SUCCESSFUL,
        IMPORT_FAILED_MORE_CLASSES,
        IMPORT_FAILED_NO_CLASS,
        EXCEPTION_THROWN;
    }
}
