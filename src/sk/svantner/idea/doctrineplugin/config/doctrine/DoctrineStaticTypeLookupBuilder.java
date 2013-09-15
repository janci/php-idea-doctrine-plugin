package sk.svantner.idea.doctrineplugin.config.doctrine;

import com.intellij.codeInsight.lookup.LookupElement;
import sk.svantner.idea.doctrineplugin.util.completion.annotations.AnnotationMethodInsertHandler;
import sk.svantner.idea.doctrineplugin.util.completion.annotations.AnnotationTagInsertHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @link http://docs.doctrine-project.org/en/latest/reference/basic-mapping.html
 */
public class DoctrineStaticTypeLookupBuilder {

    private InsertHandler insertHandler;

    public DoctrineStaticTypeLookupBuilder() {

    }

    public DoctrineStaticTypeLookupBuilder(InsertHandler insertHandler) {
        this.insertHandler = insertHandler;
    }

    public ArrayList<LookupElement> getTypes() {
        return ListToElements(Arrays.asList("id", "string", "integer", "smallint", "bigint", "boolean", "decimal", "date", "time", "datetime", "text", "array", "float"));
    }

    public ArrayList<LookupElement> getNullAble() {
        return ListToElements(Arrays.asList("true", "false"));
    }

    public ArrayList<LookupElement> getJoinColumns() {
        return ListToElements(Arrays.asList("name", "referencedColumnName", "unique", "nullable", "onDelete", "onUpdate", "columnDefinition"));
    }

    public ArrayList<LookupElement> getRootItems() {
        return ListToElements(Arrays.asList("type", "table", "fields", "manyToOne", "manyToMany", "oneToOne", "oneToMany", "indexes", "id", "lifecycleCallbacks", "repositoryClass", "inheritanceType", "discriminatorColumn"));
    }

    public ArrayList<LookupElement> getAssociationMapping(Association type) {

        switch (type) {
            case oneToOne:
                return ListToElements(Arrays.asList("targetEntity", "inversedBy", "joinColumn"));

            case oneToMany:
                return ListToElements(Arrays.asList("targetEntity", "mappedBy"));

            case manyToOne:
                return ListToElements(Arrays.asList("targetEntity", "inversedBy", "joinColumn"));

            case manyToMany:
                return ListToElements(Arrays.asList("targetEntity", "inversedBy", "joinTable", "mappedBy"));

            default:
                return new ArrayList<LookupElement>();
        }

    }

    public static enum Association {
        oneToOne, oneToMany, manyToOne, manyToMany,
    }

    public static enum InsertHandler {
        Annotations
    }

    public DoctrineStaticTypeLookupBuilder setInsertHandlerType(InsertHandler insertHandler) {
        this.insertHandler = insertHandler;
        return this;
    }

    public ArrayList<LookupElement> getOrmFieldAnnotations() {
        return ListToElements(Arrays.asList("@ORM\\Column", "@ORM\\GeneratedValue", "@ORM\\UniqueConstraint", "@ORM\\Id", "@ORM\\JoinTable", "@ORM\\ManyToOne", "@ORM\\ManyToMany", "@ORM\\OneToOne", "@ORM\\OneToMany"));
    }

    public ArrayList<LookupElement> getPropertyMappings() {
        return ListToElements(Arrays.asList("type", "column", "length", "unique", "nullable", "precision", "scale", "columnDefinition"));
    }

    public ArrayList<LookupElement> ListToElements(List<String> items) {
            return this.ListToAnnotationsElements(items);
    }

    public ArrayList<LookupElement> ListToAnnotationsElements(List<String> items) {

        ArrayList<LookupElement> lookups = new ArrayList<LookupElement>();

        for (String item : items) {
            if(item.startsWith("@")) {
                lookups.add(new DoctrineTypeLookup(item, AnnotationTagInsertHandler.getInstance()));
            } else {
                lookups.add(new DoctrineTypeLookup(item, AnnotationMethodInsertHandler.getInstance()));
            }

        }

        return lookups;
    }


}
