package sk.svantner.idea.doctrineplugin.util.annotation;

public class AnnotationValue {

    private String name;
    private Type type;

    public AnnotationValue(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    public AnnotationValue(String name) {
        this.name = name;
        this.type = Type.QuoteValue;
    }

    public String getName() {
        return this.name;
    }

    public Type getType() {
        return this.type;
    }

    public enum Type {
        QuoteValue, Array
    }

}
