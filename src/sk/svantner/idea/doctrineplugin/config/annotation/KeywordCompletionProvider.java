package sk.svantner.idea.doctrineplugin.config.annotation;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;
import com.jetbrains.php.lang.documentation.phpdoc.parser.PhpDocElementTypes;
import com.jetbrains.php.lang.parser.PhpElementTypes;
import com.jetbrains.php.lang.psi.elements.PhpPsiElement;
import org.jetbrains.annotations.NotNull;
import sk.svantner.idea.doctrineplugin.config.doctrine.DoctrineStaticTypeLookupBuilder;

/**
 * Provides keywords to be auto-completed
 * @author Ing. Jan Svantner <posta.janci@gmail.com>
 */
public class KeywordCompletionProvider extends CompletionProvider<CompletionParameters> {

    @Override
    protected void addCompletions(@NotNull CompletionParameters completionParameters, ProcessingContext processingContext, @NotNull CompletionResultSet completionResultSet) {

        PsiElement element = completionParameters.getPosition().getOriginalElement();
        PsiElement nextSibling = element.getParent().getNextSibling();

        PhpPsiElement nextNextSibling = null;
        if(nextSibling != null && nextSibling.getNextSibling() != null){
            nextNextSibling = (PhpPsiElement) nextSibling.getNextSibling();
            ASTNode siblingNode = (nextNextSibling !=null)? nextNextSibling.getNode():null;

            if ( siblingNode!=null && nextNextSibling.getNode().getElementType() == PhpElementTypes.CLASS_FIELDS ){
                handleFieldComment(element, completionResultSet);
            }

            if ( siblingNode!=null && nextNextSibling.getNode().getElementType() == PhpDocElementTypes.phpDocTagValue){
                handleFieldCommentValue(element.getParent().getParent(), completionResultSet);
            }

        }
    }

    private void handleFieldComment(PsiElement element, CompletionResultSet completionResultSet){
        completionResultSet.addAllElements(
            new DoctrineStaticTypeLookupBuilder(DoctrineStaticTypeLookupBuilder.InsertHandler.Annotations).getOrmFieldAnnotations());
    }

    private void handleFieldCommentValue(PsiElement element, CompletionResultSet completionResultSet){
        String text = element.getNode().getText();

        if(text.startsWith("@ORM\\OneToOne"))
            completionResultSet.addAllElements( new DoctrineStaticTypeLookupBuilder(DoctrineStaticTypeLookupBuilder.InsertHandler.Annotations)
                    .getAssociationMapping(DoctrineStaticTypeLookupBuilder.Association.oneToOne));
        if(text.startsWith("@ORM\\OneToMany"))
            completionResultSet.addAllElements( new DoctrineStaticTypeLookupBuilder(DoctrineStaticTypeLookupBuilder.InsertHandler.Annotations)
                    .getAssociationMapping(DoctrineStaticTypeLookupBuilder.Association.oneToMany));
        if(text.startsWith("@ORM\\ManyToOne"))
            completionResultSet.addAllElements( new DoctrineStaticTypeLookupBuilder(DoctrineStaticTypeLookupBuilder.InsertHandler.Annotations)
                    .getAssociationMapping(DoctrineStaticTypeLookupBuilder.Association.manyToOne));
        if(text.startsWith("@ORM\\ManyToMany"))
            completionResultSet.addAllElements( new DoctrineStaticTypeLookupBuilder(DoctrineStaticTypeLookupBuilder.InsertHandler.Annotations)
                    .getAssociationMapping(DoctrineStaticTypeLookupBuilder.Association.manyToMany));
        if(text.startsWith("@ORM\\Column"))
            completionResultSet.addAllElements( new DoctrineStaticTypeLookupBuilder(DoctrineStaticTypeLookupBuilder.InsertHandler.Annotations)
                    .getPropertyMappings());
    }
}
