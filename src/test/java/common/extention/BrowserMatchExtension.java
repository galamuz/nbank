package common.extention;

import api.configs.Configs;
import com.codeborne.selenide.Configuration;
import common.annotation.Browsers;
import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.Arrays;

public class BrowserMatchExtension implements ExecutionCondition {

    @Override
    public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
        Browsers annotation = context.getElement()
                .map( el ->el.getAnnotation(Browsers.class)).orElse(null);

        if(annotation==null){
            return ConditionEvaluationResult.enabled("not riles for browsers");
        }
        String currentBrowser = Configuration.browser;

        boolean matcher= Arrays.asList(annotation.value()).contains(currentBrowser);

        if(matcher){
            return ConditionEvaluationResult.enabled("Current browser: "+currentBrowser+" is in available browser list" +Arrays.toString(annotation.value()));
        }else{
            return ConditionEvaluationResult.disabled("Current browser: "+currentBrowser+" is not in available browser list" +Arrays.toString(annotation.value()));
        }
    }
}
