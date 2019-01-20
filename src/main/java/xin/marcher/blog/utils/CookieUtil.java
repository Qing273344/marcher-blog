package xin.marcher.blog.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * cookie 工具类
 *
 * @author marcher
 */
public class CookieUtil {

    public static final String COOKIE_DOMAIN = "localhost";

    /** cookie超时时间(单位:秒) */
    private static final int MAX_AGE = 60 * 60;

    /**
     * 添加新Cookie
     *
     * @param response  响应
     * @param cookie    新cookie
     */
    public static void addCookie(HttpServletResponse response, Cookie cookie) {
        if (EmptyUtil.isNotEmpty(cookie)) {
            response.addCookie(cookie);
        }
    }

    /**
     * 添加新Cookie
     *
     * @param response      响应
     * @param cookieName    cookie名
     * @param cookieValue   cookie值
     * @param domain        所属域
     * @param httpOnly      是否将cookie设置成httpOnly
     * @param maxAge        设置cookie最大生存时间
     * @param path          设置cookie路径
     * @param secure        是否值允许HTTPS访问
     */
    public static void addCookie(HttpServletResponse response, String cookieName, String cookieValue, String domain,
                                 boolean httpOnly, int maxAge, String path, boolean secure) {
        if (EmptyUtil.isNotEmpty(cookieName)) {
            cookieValue = EmptyUtil.isEmpty(cookieValue) ? "" : cookieValue;

            Cookie newCookie = new Cookie(cookieName, cookieValue);
            newCookie.setHttpOnly(httpOnly);
            newCookie.setSecure(secure);
            if (EmptyUtil.isNotEmpty(domain)) {
                newCookie.setDomain(domain);
            }
            if (maxAge > 0) {
                newCookie.setMaxAge(maxAge);
            }
            if (EmptyUtil.isEmpty(path)) {
                newCookie.setPath("/");
            } else {
                newCookie.setPath(path);
            }
            addCookie(response, newCookie);
        }

    }


    /**
     * 添加新Cookie
     *
     * @param response      响应
     * @param cookieName    cookie名
     * @param cookieValue   cookie值
     * @param domain        所属域
     */
    public static void addCookie(HttpServletResponse response, String cookieName, String cookieValue, String domain) {
        addCookie(response, cookieName, cookieValue, domain, true, MAX_AGE, "/", true);
    }

    /**
     * 通过Cookie名获取Cookie
     *
     * @param request       请求
     * @param cookieName    cookie名
     * @return
     *      Cookie
     */
    public static Cookie getCookie(HttpServletRequest request, String cookieName) {
        if (EmptyUtil.isEmpty(cookieName)) {
            return null;
        }

        Cookie[] cookies = request.getCookies();
        if (EmptyUtil.isEmpty(cookies)) {
            return null;
        }

        for (Cookie cookie : cookies) {
            if (cookieName.equals(cookie.getName())) {
                return cookie;
            }
        }
        return null;
    }

    /**
     * 通过Cookie名获取对应的Cookie值
     *
     * @param request       请求
     * @param cookieName    cookie名
     * @return
     *      Cookie值
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie cookie = getCookie(request, cookieName);
        if (EmptyUtil.isEmpty(cookieName)) {
            return "";
        }
        return cookie.getValue();
    }

    /**
     * 删除指定Cookie
     *
     * @param response  响应
     * @param cookie    cookie
     */
    public static void delCookie(HttpServletResponse response, Cookie cookie) {
        if (EmptyUtil.isNotEmpty(cookie)) {
            cookie.setPath("/");
            cookie.setMaxAge(0);
            cookie.setValue(null);
            response.addCookie(cookie);
        }
    }

    /**
     * 通过Cookie名删除指定Cookie
     *
     * @param request       请求
     * @param response      响应
     * @param cookieName    待删除cookie名
     */
    public static void delCookie(HttpServletRequest request, HttpServletResponse response, String cookieName) {
        Cookie cookie = getCookie(request, cookieName);
        if (EmptyUtil.isNotEmpty(cookieName) && cookieName.equals(cookie.getName())) {
            delCookie(response, cookie);
        }
    }

    /**
     * 通过Cookie名修改指定cookie
     *
     * @param request       请求
     * @param response      响应
     * @param cookieName    cookie名
     * @param cookieValue   修改后的cookie值
     * @param domain        修改后的domain值
     */
    public static void updateCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
                                    String cookieValue, String domain) {
        Cookie cookie = getCookie(request, cookieName);
        if (EmptyUtil.isNotEmpty(cookie) && EmptyUtil.isNotEmpty(cookieName) && cookieName.equals(cookie.getName())) {
            addCookie(response, cookieName, cookieValue, domain);
        }
    }
}
