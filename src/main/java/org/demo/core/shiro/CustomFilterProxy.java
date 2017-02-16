package org.demo.core.shiro;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.demo.common.util.LoggerUtils;
import org.springframework.web.filter.DelegatingFilterProxy;

/**
 * 自定义 filter 代理, 支持设置不拦截的url<br>
 * excludeUrls 包含不过滤的 url ,支持 '**'方式和正则表达式方式  ,'**' == ([\\s\\S]*)
 * 
 * @author NSTL
 * @Created by zyj on 2017年1月18日 下午3:26:42
 */
public class CustomFilterProxy extends DelegatingFilterProxy {

    private static List<String> excludeUrls;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest r = (HttpServletRequest) request;
        String uri = r.getRequestURI();
        LoggerUtils.debug(this.getClass(), "进入自定义过滤器代理(CustomFilterProxy)......请求url:" + uri);
        if (excludeUrls != null && !excludeUrls.isEmpty()) {

            String basePath = r.getContextPath();

            if (basePath != null && !StringUtils.isBlank(basePath) && uri.startsWith(basePath)) {
                uri = uri.replace(basePath, "");
            }

            for (String path : excludeUrls) {
                path = path.replace("**", "([\\s\\S]*)");
                if (uri.matches(path)) {
                    LoggerUtils.debug(this.getClass(), "跳过ShiroFilter,直接返回请求数据......");
                    filterChain.doFilter(request, response);
                    break;
                }
            }
        }
        LoggerUtils.debug(this.getClass(), "进入 shiroFilter 代理器(DelegatingFilterProxy)......");
        super.doFilter(request, response, filterChain);

    }

    /**
     * @return the excludeUrls
     */
    public List<String> getExcludeUrls() {
        return excludeUrls;
    }

    /**
     * @param excludeUrls the excludeUrls to set
     */
    public void setExcludeUrls(List<String> excludeUrls) {
        this.excludeUrls = excludeUrls;
    }

    public static void main(String[] args) {
        String uri = "/r/xxx/sss/xx";
        String regex1 = "/r/([\\s\\S]*)/sss/([\\s\\S]*)";

        String regex = "\\w+@\\w+\\.\\w{2,3}";

        System.out.println(Pattern.matches(regex1, uri));

    }

}
