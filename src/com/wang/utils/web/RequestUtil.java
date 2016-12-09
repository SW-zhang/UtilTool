package com.wang.utils.web;

import com.wang.utils.object.FieldUtil;
import com.wang.utils.param.Param;
import com.wang.utils.string.StringUtil;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * HttpRequest对象的工具集
 */
@SuppressWarnings("unchecked")
public class RequestUtil {

    /**
     * 打印头列表
     *
     * @param request
     */
    public static void printHeaders(HttpServletRequest request) {
        Enumeration<String> enus = request.getHeaderNames();
        System.out.println("Header {");
        while (enus.hasMoreElements()) {
            String name = enus.nextElement();
            System.out.println("	" + name + "=" + request.getHeader(name));
        }
        System.out.println("}");
    }

    /**
     * 打印参数列表
     *
     * @param request
     */
    public static void printParams(HttpServletRequest request) {
        Map<String, String[]> map = request.getParameterMap();
        System.out.println("Parameter {");
        for (Entry<String, String[]> ent : map.entrySet()) {
            System.out.println("	" + ent.getKey() + "=" + StringUtil.linkString(ent.getValue(), ", "));
        }
        System.out.println("}");
    }

    /**
     * 得到一个参数名称列表
     *
     * @param request
     * @return
     */
    public static List<String> getParameterNameList(HttpServletRequest request) {
        Enumeration<String> enums = request.getParameterNames();
        List<String> list = new LinkedList<String>();
        while (enums.hasMoreElements()) {
            list.add(enums.nextElement());
        }
        return list;
    }

    /**
     * 从WEB的各个域中取得对象值（支持对象路径 ，如request.xxx, session.xxx, application.xxx）
     *
     * @param request
     * @param fullname
     * @return
     */
    public static Object getWebAttribute(HttpServletRequest request, String fullname) {
        String first = StringUtil.getLeft(fullname, ".");
        if (null != first) {
            if (first.equals("request")) {
                return getAttribute(request, fullname);
            } else if (first.equals("session")) {
                return getAttribute(request.getSession(), fullname);
            } else if (first.equals("application")) {
                return getAttribute(request.getSession().getServletContext(), fullname);
            }
        }
        Object o = null;
        if (null == fullname//
                || null != (o = getAttribute(request, fullname)) //
                || null != (o = getAttribute(request.getSession(), fullname))//
                || null != (o = getAttribute(request.getSession().getServletContext(), fullname))) ;

        return o;
    }

    /**
     * 从HttpServletRequest中取得对象值（支持对象路径）
     *
     * @param request
     * @param fullname
     * @return
     */
    public static Object getAttribute(HttpServletRequest request, String fullname) {
        if (null == fullname) {
            return null;
        }
        Object o = request.getAttribute(fullname);
        if (null == o) {
            if (fullname.indexOf('.') > -1) {
                Object obj = getAttribute(request, StringUtil.getLeft(fullname, "."));
                if (null == obj) {
                    return null;
                }
                return FieldUtil.get(obj, StringUtil.getRightOuter(fullname, "."));
            }
        }
        return o;
    }

    /**
     * 从HttpSession中取得对象值（支持对象路径）
     *
     * @param session
     * @param fullname
     * @return
     */
    public static Object getAttribute(HttpSession session, String fullname) {
        if (null == fullname) {
            return null;
        }
        Object o = session.getAttribute(fullname);
        if (null == o) {
            if (fullname.indexOf('.') > -1) {
                Object obj = getAttribute(session, StringUtil.getLeft(fullname, "."));
                if (null == obj) {
                    return null;
                }
                return FieldUtil.get(obj, StringUtil.getRightOuter(fullname, "."));
            }
        }
        return o;
    }

    /**
     * 从ServletContext中取得对象值（支持对象路径）
     *
     * @param servletContext
     * @param fullname
     * @return
     */
    public static Object getAttribute(ServletContext servletContext, String fullname) {
        if (null == fullname) {
            return null;
        }
        Object o = servletContext.getAttribute(fullname);
        if (null == o) {
            if (fullname.indexOf('.') > -1) {
                o = getAttribute(servletContext, StringUtil.getLeft(fullname, "."));
                if (null == o) {
                    return null;
                }
                return FieldUtil.get(o, StringUtil.getRightOuter(fullname, "."));
            }
        }
        return o;
    }

    /**
     * 从request中取得参数并设置到对象属性中
     *
     * @param request
     * @param obj
     * @throws UnsupportedEncodingException
     */
    public static void fillObjectFromParams(HttpServletRequest request, Object obj) throws UnsupportedEncodingException {
        Enumeration<String> enums = request.getParameterNames();
        while (enums.hasMoreElements()) {
            String name = enums.nextElement();
            Class<?> type = FieldUtil.propertyType(obj.getClass(), name);
            if (null == type) {
                continue;
            }
            Object value = null;
            if (request.getMethod().equalsIgnoreCase("post")) {
                if (type.isArray()) {
                    value = request.getParameterValues(name);
                } else {
                    value = request.getParameter(name);
                }
            } else {
                if (type.isArray()) {
                    String[] vs = request.getParameterValues(name);
                    for (int i = 0; i < vs.length; i++) {
                        vs[i] = new String(vs[i].getBytes("iso8859-1"), request.getCharacterEncoding());
                    }
                    value = vs;
                } else {
                    String v = request.getParameter(name);
                    v = new String(v.getBytes("iso8859-1"), request.getCharacterEncoding());
                    value = v;
                }
            }
            FieldUtil.set(obj, name, value);
        }
    }

    /**
     * 将对象的属性值设置到request.attribute中。
     *
     * @param obj
     * @param request
     */
    public static void setAttributeFromObject(Object obj, HttpServletRequest request) {
        String[] getters = FieldUtil.getters(obj.getClass(), null);
        for (String getter : getters) {
            try {
                request.setAttribute(getter, FieldUtil.get(obj, getter));
            } catch (Exception e) {
                Logger.getLogger(RequestUtil.class).debug("Error in setAttribute.", e);
            }
        }
    }

    /**
     * 将请求参数转成请求字符串返回
     *
     * @param request
     * @return
     */
    public static String getParamsQueryString(HttpServletRequest request) {
        Enumeration<?> names = request.getParameterNames();
        StringBuffer sb = new StringBuffer();
        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            String[] values = request.getParameterValues(name);
            if (values != null) {
                for (String value : values) {
                    if (sb.length() > 0) {
                        sb.append("&");
                    }
                    if (name.endsWith("password")) sb.append(name).append("=").append("******");
                    else sb.append(name).append("=").append(UrlUtil.UTF8.encode(value));
                }
            }
        }
        return sb.toString();
    }

    public static List<Param> getParams(HttpServletRequest request) {
        List<Param> params = new LinkedList<Param>();
        Enumeration<?> names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            String[] values = request.getParameterValues(name);
            if (values != null) {
                for (String value : values) {
                    Param param = new Param();
                    param.setName(name);
                    param.setValue(value);
                    params.add(param);
                }
            }
        }
        return params;
    }

    /**
     * 得到请求的实际IP，但是无法确定是否作弊。<br />
     * 取得过程: 1.x-forwarded-for -> 2.Proxy-Client-IP -> 3.WL-Proxy-Client-IP ->
     * 4.request.getRemoteAddr()
     *
     * @param request
     * @return
     */
    public static String getRealIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
