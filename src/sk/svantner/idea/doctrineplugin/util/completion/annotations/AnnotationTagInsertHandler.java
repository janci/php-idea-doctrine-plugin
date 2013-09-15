package sk.svantner.idea.doctrineplugin.util.completion.annotations;

import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.completion.InsertionContext;
import com.intellij.codeInsight.lookup.LookupElement;
import com.jetbrains.php.completion.insert.PhpInsertHandlerUtil;
import org.jetbrains.annotations.NotNull;

public class AnnotationTagInsertHandler implements InsertHandler<LookupElement> {

    private static final AnnotationTagInsertHandler instance = new AnnotationTagInsertHandler();

    public void handleInsert(@NotNull InsertionContext context, @NotNull LookupElement lookupElement) {
        PhpInsertHandlerUtil.insertStringAtCaret(context.getEditor(), "()");
        context.getEditor().getCaretModel().moveCaretRelatively(-1, 0, false, false, true);

    }

    public static AnnotationTagInsertHandler getInstance(){
        return instance;
    }

}
