package com.fxlabs.fxt.cli;


import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

/**
 * @author Intesar Shannan Mohammed
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class FxPromptProvider implements PromptProvider {

    public AttributedString getPrompt() {
        return new AttributedString("FX-cli > ", AttributedStyle.BOLD);
    }
}

