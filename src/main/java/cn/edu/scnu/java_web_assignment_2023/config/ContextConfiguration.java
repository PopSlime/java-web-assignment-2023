package cn.edu.scnu.java_web_assignment_2023.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.lang.Nullable;

import java.text.MessageFormat;
import java.util.Locale;

@Configuration
public class ContextConfiguration {
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new FallbackResourceBundleMessageSource();
        messageSource.setBasename("messages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setDefaultLocale(Locale.US);
        return messageSource;
    }

    private static class FallbackResourceBundleMessageSource extends ResourceBundleMessageSource {
        @Override
        @Nullable
        protected String resolveCodeWithoutArguments(String code, Locale locale) {
            String ret = super.resolveCodeWithoutArguments(code, locale);
            if (ret != null) {
                return ret;
            }
            Locale defaultLocale = getDefaultLocale();
            if (locale != defaultLocale && defaultLocale != null) {
                return super.resolveCodeWithoutArguments(code, defaultLocale);
            }
            return null;
        }

        @Override
        @Nullable
        protected MessageFormat resolveCode(String code, Locale locale) {
            MessageFormat ret = super.resolveCode(code, locale);
            if (ret != null) {
                return ret;
            }
            Locale defaultLocale = getDefaultLocale();
            if (locale != defaultLocale && defaultLocale != null) {
                return super.resolveCode(code, defaultLocale);
            }
            return null;
        }
    }
}
