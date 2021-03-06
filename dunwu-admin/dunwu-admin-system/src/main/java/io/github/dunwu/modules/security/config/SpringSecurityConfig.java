package io.github.dunwu.modules.security.config;

import io.github.dunwu.annotation.AnonymousAccess;
import io.github.dunwu.modules.security.security.AuthenticationFilter;
import io.github.dunwu.modules.security.security.JwtAccessDeniedHandler;
import io.github.dunwu.modules.security.security.JwtAuthenticationEntryPoint;
import io.github.dunwu.modules.security.security.TokenProvider;
import io.github.dunwu.modules.security.service.AuthService;
import io.github.dunwu.util.enums.RequestMethodEnum;
import io.github.dunwu.web.filter.XssFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;

/**
 * @author Zheng Jie
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint authenticationErrorHandler;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final ApplicationContext applicationContext;
    private final DunwuWebSecurityProperties securityProperties;
    private final AuthService authService;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // ?????????????????? url??? @AnonymousAccess
        RequestMappingHandlerMapping requestMappingHandlerMapping =
            (RequestMappingHandlerMapping) applicationContext.getBean("requestMappingHandlerMapping");
        Map<RequestMappingInfo, HandlerMethod> handlerMethodMap = requestMappingHandlerMapping.getHandlerMethods();
        // ??????????????????
        Map<String, Set<String>> anonymousUrls = getAnonymousUrl(handlerMethodMap);
        httpSecurity
            // ?????? CSRF
            .csrf()
            .disable()
            .addFilterBefore(corsFilter(), UsernamePasswordAuthenticationFilter.class)
            // ????????????
            .exceptionHandling()
            .authenticationEntryPoint(authenticationErrorHandler)
            .accessDeniedHandler(jwtAccessDeniedHandler)
            // ??????iframe ????????????
            .and()
            .headers()
            .frameOptions()
            .disable()
            // ???????????????
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            // ??????????????????
            .antMatchers(
                HttpMethod.GET,
                "/*.html",
                "/**/*.html",
                "/**/*.css",
                "/**/*.js",
                "/webSocket/**"
            )
            .permitAll()
            // swagger ??????
            .antMatchers("/swagger-ui.html")
            .permitAll()
            .antMatchers("/swagger-resources/**")
            .permitAll()
            .antMatchers("/webjars/**")
            .permitAll()
            .antMatchers("/*/api-docs")
            .permitAll()
            // ??????
            .antMatchers("/avatar/**")
            .permitAll()
            .antMatchers("/file/**")
            .permitAll()
            // ???????????? druid
            .antMatchers("/druid/**")
            .permitAll()
            // ??????OPTIONS??????
            .antMatchers(HttpMethod.OPTIONS, "/**")
            .permitAll()
            // ???????????????????????????url???????????????????????????Token??????????????????????????? Request ??????
            // GET
            .antMatchers(HttpMethod.GET, anonymousUrls.get(RequestMethodEnum.GET.getType()).toArray(new String[0]))
            .permitAll()
            // POST
            .antMatchers(HttpMethod.POST, anonymousUrls.get(RequestMethodEnum.POST.getType()).toArray(new String[0]))
            .permitAll()
            // PUT
            .antMatchers(HttpMethod.PUT, anonymousUrls.get(RequestMethodEnum.PUT.getType()).toArray(new String[0]))
            .permitAll()
            // PATCH
            .antMatchers(HttpMethod.PATCH, anonymousUrls.get(RequestMethodEnum.PATCH.getType()).toArray(new String[0]))
            .permitAll()
            // DELETE
            .antMatchers(HttpMethod.DELETE,
                anonymousUrls.get(RequestMethodEnum.DELETE.getType()).toArray(new String[0]))
            .permitAll()
            // ??????????????????????????????
            .antMatchers(anonymousUrls.get(RequestMethodEnum.ALL.getType()).toArray(new String[0]))
            .permitAll()
            // ???????????????????????????
            .anyRequest()
            .authenticated()
            .and()
            .addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * ?????? ROLE_ ??????
     */
    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }

    @Bean
    public AuthenticationFilter authenticationFilter() {
        return new AuthenticationFilter(tokenProvider, securityProperties, authService);
    }

    private Map<String, Set<String>> getAnonymousUrl(Map<RequestMappingInfo, HandlerMethod> handlerMethodMap) {
        Map<String, Set<String>> anonymousUrls = new HashMap<>(6);
        Set<String> get = new HashSet<>();
        Set<String> post = new HashSet<>();
        Set<String> put = new HashSet<>();
        Set<String> patch = new HashSet<>();
        Set<String> delete = new HashSet<>();
        Set<String> all = new HashSet<>();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> infoEntry : handlerMethodMap.entrySet()) {
            HandlerMethod handlerMethod = infoEntry.getValue();
            AnonymousAccess anonymousAccess = handlerMethod.getMethodAnnotation(AnonymousAccess.class);
            if (null != anonymousAccess) {
                List<RequestMethod> requestMethods = new ArrayList<>(
                    infoEntry.getKey().getMethodsCondition().getMethods());
                RequestMethodEnum request = RequestMethodEnum.find(
                    requestMethods.size() == 0 ? RequestMethodEnum.ALL.getType() : requestMethods.get(0).name());
                switch (Objects.requireNonNull(request)) {
                    case GET:
                        get.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
                        break;
                    case POST:
                        post.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
                        break;
                    case PUT:
                        put.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
                        break;
                    case PATCH:
                        patch.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
                        break;
                    case DELETE:
                        delete.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
                        break;
                    default:
                        all.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
                        break;
                }
            }
        }
        anonymousUrls.put(RequestMethodEnum.GET.getType(), get);
        anonymousUrls.put(RequestMethodEnum.POST.getType(), post);
        anonymousUrls.put(RequestMethodEnum.PUT.getType(), put);
        anonymousUrls.put(RequestMethodEnum.PATCH.getType(), patch);
        anonymousUrls.put(RequestMethodEnum.DELETE.getType(), delete);
        anonymousUrls.put(RequestMethodEnum.ALL.getType(), all);
        return anonymousUrls;
    }

    // ------------------------------------------------------------------------------------
    // ???????????????????????????
    // ------------------------------------------------------------------------------------

    /**
     * ???????????????
     */
    @Bean
    @ConditionalOnProperty(name = "dunwu.web.security.corsEnabled", havingValue = "true")
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(securityProperties.getCorsPath(), corsConfiguration);
        return new CorsFilter(source);
    }

    /**
     * XSS ?????????
     */
    @Bean
    @ConditionalOnProperty(name = "dunwu.web.security.xssEnabled", havingValue = "true", matchIfMissing = true)
    public FilterRegistrationBean<XssFilter> xssFilterRegistrationBean() {
        FilterRegistrationBean<XssFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new XssFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.setEnabled(true);
        filterRegistrationBean.addUrlPatterns("/*");
        Map<String, String> initParameters = new HashMap<>(2);
        initParameters.put("excludes", securityProperties.getXssExcludePath());
        initParameters.put("isIncludeRichText", "true");
        filterRegistrationBean.setInitParameters(initParameters);
        return filterRegistrationBean;
    }

}
