package cn.edu.scnu.java_web_assignment_2023.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.SimpleLocaleContext;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.LocaleContextResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang");
        return interceptor;
    }

    @Bean
    public LocaleResolver localeResolver() {
        ChainedLocaleResolver resolver = new ChainedLocaleResolver();

        CookieLocaleResolver cookieResolver = new CookieLocaleResolver();
        cookieResolver.setCookieName("lang");
        cookieResolver.setDefaultLocale(ChainedLocaleResolver.FALLBACK);
        resolver.add(cookieResolver);

        AcceptHeaderLocaleResolver acceptHeaderResolver = new AcceptHeaderLocaleResolver();
        acceptHeaderResolver.setDefaultLocale(ChainedLocaleResolver.FALLBACK);
        resolver.add(acceptHeaderResolver);

        resolver.add(new FixedLocaleResolver(Locale.US));

        return resolver;
    }

    static class ChainedLocaleResolver implements LocaleContextResolver {
        public static final Locale FALLBACK = new Locale("und");
        public static final LocaleContext FALLBACK_CONTEXT = new SimpleLocaleContext(FALLBACK);

        private final List<LocaleResolver> resolvers = new ArrayList<>();
        @Nullable
        private LocaleResolver targetResolver;

        public void add(LocaleResolver resolver) {
            resolvers.add(resolver);
            if (targetResolver == null) targetResolver = resolver;
        }

        @Override
        public LocaleContext resolveLocaleContext(HttpServletRequest request) {
            for (LocaleResolver resolver : resolvers) {
                if (resolver instanceof LocaleContextResolver) {
                    LocaleContext localeContext = ((LocaleContextResolver)resolver).resolveLocaleContext(request);
                    Locale locale = localeContext.getLocale();
                    if (locale != null && !locale.equals(FALLBACK)) return localeContext;
                }
                else {
                    Locale locale = resolver.resolveLocale(request);
                    if (!locale.equals(FALLBACK)) return new SimpleLocaleContext(locale);
                }
            }
            return FALLBACK_CONTEXT;
        }

        @Override
        public void setLocaleContext(HttpServletRequest request, HttpServletResponse response, LocaleContext localeContext) {
            if (targetResolver == null) return;
            if (targetResolver instanceof LocaleContextResolver)
                ((LocaleContextResolver)targetResolver).setLocaleContext(request, response, localeContext);
            else
                targetResolver.setLocale(request, response, localeContext.getLocale());
        }

        @Override
        public Locale resolveLocale(HttpServletRequest request) {
            for (LocaleResolver resolver : resolvers) {
                Locale locale = resolver.resolveLocale(request);
                if (!locale.equals(FALLBACK)) return locale;
            }
            return FALLBACK;
        }

        @Override
        public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
            if (targetResolver == null) return;
            targetResolver.setLocale(request, response, locale);
        }
    }
}
