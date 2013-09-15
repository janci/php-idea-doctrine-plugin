package sk.svantner.idea.doctrineplugin.config.annotation;

import com.intellij.codeInsight.completion.*;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.psi.PsiElement;
import com.jetbrains.php.lang.PhpLanguage;
import com.jetbrains.php.lang.documentation.phpdoc.lexer.PhpDocTokenTypes;

/**
 * @author Ing. Jan Svantner <posta.janci@gmail.com>
 */
public class DoctrineAnnotationCompletionContributor extends CompletionContributor {
    private static final Logger log = Logger.getInstance(DoctrineAnnotationCompletionContributor.class);


    public DoctrineAnnotationCompletionContributor() {
        log.info("In doctrine completation");
        extend(CompletionType.BASIC, getDocPattern(), new KeywordCompletionProvider());
        // TODO: implement autocomplete for text start with @ (@ + <space>)
    }

    public PsiElementPattern.Capture<PsiElement> getDocPattern(){
        return PlatformPatterns.psiElement(PhpDocTokenTypes.DOC_IDENTIFIER).withLanguage(PhpLanguage.INSTANCE);
    }


}

