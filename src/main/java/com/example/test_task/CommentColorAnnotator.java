package com.example.test_task;

import com.intellij.lang.annotation.Annotator;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.psi.PsiComment;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommentColorAnnotator implements Annotator {
    private static final Pattern COLOR_PATTERN = Pattern.compile("//.*\\|\\s*(\\w+)$");
    private static final Map<String, Color> COLOR_MAP = new HashMap<>();

    static {
        COLOR_MAP.put("red", Color.RED);       // some comment | red
        COLOR_MAP.put("blue", Color.BLUE);     // some comment | blue
        COLOR_MAP.put("green", Color.GREEN);   // some comment | green
        COLOR_MAP.put("yellow", Color.YELLOW); // some comment | yellow
    }

    @Override
    public void annotate(@NotNull PsiElement element, @NotNull AnnotationHolder holder) {
        if (!(element instanceof PsiComment)) return;

        String commentText = element.getText();
        Matcher matcher = COLOR_PATTERN.matcher(commentText);

        if (matcher.find()) {
            String colorName = matcher.group(1).toLowerCase();
            Color color = COLOR_MAP.getOrDefault(colorName, Color.GRAY);

            TextAttributes attributes = new TextAttributes();
            attributes.setForegroundColor(color);
            holder.createInfoAnnotation(element, null).setEnforcedTextAttributes(attributes);
        }
    }
}
