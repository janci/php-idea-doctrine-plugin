package sk.svantner.idea.doctrineplugin.config.doctrine;

import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.completion.InsertionContext;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementPresentation;

import sk.svantner.idea.doctrineplugin.DoctrineIcons;
import org.jetbrains.annotations.NotNull;

public class DoctrineTypeLookup extends LookupElement {

    private String name;
    private InsertHandler<LookupElement> insertHandler = null;

    public DoctrineTypeLookup(String typeName) {
        this.name = typeName;
    }

    public DoctrineTypeLookup(String typeName, InsertHandler<LookupElement> insertHandler) {
        this.name = typeName;
        this.insertHandler = insertHandler;
    }

    @NotNull
    @Override
    public String getLookupString() {
        return name;
    }

    public void handleInsert(InsertionContext context) {
        if (this.insertHandler != null) {
            this.insertHandler.handleInsert(context, this);
        }
    }

    public void renderElement(LookupElementPresentation presentation) {
        presentation.setItemText(getLookupString());
        presentation.setIcon(DoctrineIcons.DOCTRINE);
        presentation.setTypeText("Doctrine");
        presentation.setTypeGrayed(true);
    }

}