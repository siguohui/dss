package com.xiaosi.wx.utils;

import java.io.IOException;
import java.security.MessageDigest;

import cn.hutool.core.codec.Base64;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public abstract class ServletUtils {

    private static final Log logger = LogFactory.getLog(ServletUtils.class);

    public final static String encode(String src) {
        return Base64.encode(src.getBytes());
    }

    public final static String decode(String encoded) {
        return new String(Base64.decode(encoded));
    }

    /**
     * 获取当前登录用户的token
     */
    public static String getToken() {
      /*  ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = sra.getRequest();*/
        return getRequest().getHeader("Authorization");
    }

    /**
     * 获取request
     */
    public static HttpServletRequest getRequest() {
        return getRequestAttributes().getRequest();
    }

    /**
     * 获取response
     *
     * @return
     */
    public static HttpServletResponse getResponse() {
        return getRequestAttributes().getResponse();
    }

    /**
     * 获取session
     *
     * @return
     */
    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    /**
     * request和response在ServletRequestAttributes类当中
     *
     * @return
     */
    public static ServletRequestAttributes getRequestAttributes() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        return (ServletRequestAttributes) attributes;
    }

    /**
     * 将字符串渲染到客户端(前后端分离很少会用到)
     *
     * @param response
     * @param string
     * @return
     */
    public static String renderString(HttpServletResponse response, String string) {
        try {
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取完整的请求路径，包括：域名，端口，上下文访问路径
     *
     * @return 上传图片的时候需要：服务器路径+上下文访问路径（所以封装了该方法）
     */
    public static String getUrl() {
        HttpServletRequest request = ServletUtils.getRequest();
        return getDomain(request);
    }

    public static String getDomain(HttpServletRequest request) {
        StringBuffer url = request.getRequestURL();
        String contextPath = request.getServletContext().getContextPath();
        return url.delete(url.length() - request.getRequestURI().length(), url.length()).append(contextPath).toString();
    }

    /**
     * 生成32位MD5加密字符串
     *
     * @param text
     * @return
     */
    public final static String md5(String text) {
        String md5Str = null;
        try {
            StringBuffer buf = new StringBuffer();
            /**
             * MessageDigest 类为应用程序提供信息摘要算法的功能，如 MD5 或 SHA 算法。
             * 信息摘要是安全的单向哈希函数，它接收任意大小的数据，并输出固定长度的哈希值。
             *
             * MessageDigest 对象开始被初始化。 该对象通过使用 update()方法处理数据。 任何时候都可以调用
             * reset()方法重置摘要。 一旦所有需要更新的数据都已经被更新了，应该调用digest()方法之一完成哈希计算。
             *
             * 对于给定数量的更新数据，digest 方法只能被调用一次。 在调用 digest 之后，MessageDigest
             * 对象被重新设置成其初始状态。
             */
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(text.getBytes());// 添加要进行计算摘要的信息,使用 plainText 的 byte
            // 数组更新摘要。
            byte b[] = md.digest();// 计算出摘要,完成哈希计算。
            int i;
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }

                buf.append(Integer.toHexString(i)); // 将整型 十进制 i
                // 转换为16位，用十六进制参数表示的无符号整数值的字符串表示形式。
            }

            md5Str = buf.toString();// 32位的加密
        } catch (Exception e) {
            e.printStackTrace();
        }
        return md5Str;
    }

    /**
     * 生成16位MD5加密字符串
     *
     * @param text
     * @return
     */
    public final static String md5L16(String text) {
        return md5(text).substring(8, 24);
    }

    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr()的原因是有可能用户使用了代理软件方式避免真实IP地址
     * <p>
     * 如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，其中第一个非unknown的有效IP字符串
     * <blockquote>
     * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130, 192.168.1.100
     * <br>
     * 用户真实IP为： 192.168.1.110
     * </blockquote>
     * </p>
     *
     * @param request
     * @return
     */
    public static String getIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip.equals("0:0:0:0:0:0:0:1")) {
            ip = "LocalHost";
        }
        return ip;
    }

//    /**
//     * 判断当前客户端IP是否内网地址
//     *
//     * @param ip
//     * @return
//     */
//    public static boolean isIntranetIp(String ip) {
//        if (ip == null) {
//            return false;
//        }
//        if (ip.startsWith("192.168.") || ip.equals("127.0.0.1") || ip.equals("LocalHost")) {
//            return true;
//        } else {
//            String ips = ConfigUtils.getSysIps();
//            if (ips != null && ips.contains(ip)) {
//                return true;
//            } else {
//                return false;
//            }
//        }
//    }
//
//    /**
//     * 判断当前客户端IP是否内网地址
//     *
//     * @param request
//     * @return
//     */
//    public static boolean isIntranetIp(HttpServletRequest request) {
//        return isIntranetIp(getIP(request));
//    }

    /* *//**
     * 验证权限
     *
     * @param access
     * @param customerId
     * @param departmentId
     * @return true/false
     *//*
    public static boolean hasAccess(Constants.Access access, String customerId, String departmentId) {
        // TODO security check
        SessionInfo sessionInfo = getSessionInfo();

        // pass super user
        if (sessionInfo.getSuperUser() == 1) {
            return true;
        }

        // check user security setting
        List<UserSecurity> securityList = sessionInfo.getSecurityList();
        for (UserSecurity us : securityList) {
            if (us.getCustomerId().equalsIgnoreCase(customerId) || us.getCustomerId().equalsIgnoreCase(departmentId)) {
                if (access == Access.BOM && NumberUtils.compare(us.getBom(), 1)) {
                    return true;
                } else if (access == Access.TDOF && NumberUtils.compare(us.getTdof(), 1)) {
                    return true;
                } else if (access == Access.TRANSLATE && NumberUtils.compare(us.getTranslate(), 1)) {
                    return true;
                } else if (access == Access.PAPER && NumberUtils.compare(us.getPaper(), 1)) {
                    return true;
                } else if (access == Access.COSTING && NumberUtils.compare(us.getCosting(), 1)) {
                    return true;
                } else if (access == Access.SAMPLE && NumberUtils.compare(us.getSample(), 1)) {
                    return true;
                } else if (access == Access.ORDER && NumberUtils.compare(us.getOrder(), 1)) {
                    return true;
                } else if (access == Access.PURCHASE && NumberUtils.compare(us.getPurchase(), 1)) {
                    return true;
                } else if (access == Access.INVOICE && NumberUtils.compare(us.getInvoice(), 1)) {
                    return true;
                } else if (access == Access.WAREHOUSE && NumberUtils.compare(us.getWarehouse(), 1)) {
                    return true;
                } else if (access == Access.REPORT && NumberUtils.compare(us.getReport(), 1)) {
                    return true;
                } else if (access == Access.COSTING_APPROVAL_LV1 && NumberUtils.compare(us.getCostingApprovalLv1(), 1)) {
                    return true;
                } else if (access == Access.COSTING_APPROVAL_LV2 && NumberUtils.compare(us.getCostingApprovalLv2(), 1)) {
                    return true;
                } else if (access == Access.COSTING_APPROVAL_LV3 && NumberUtils.compare(us.getCostingApprovalLv3(), 1)) {
                    return true;
                } else if (access == Access.ORDER_APPROVAL_LV1 && NumberUtils.compare(us.getOrderApprovalLv1(), 1)) {
                    return true;
                } else if (access == Access.ORDER_APPROVAL_LV2 && NumberUtils.compare(us.getOrderApprovalLv2(), 1)) {
                    return true;
                } else if (access == Access.ORDER_APPROVAL_LV3 && NumberUtils.compare(us.getOrderApprovalLv3(), 1)) {
                    return true;
                } else if (access == Access.PO_APPROVAL_LV1 && NumberUtils.compare(us.getPoApprovalLv1(), 1)) {
                    return true;
                } else if (access == Access.PO_APPROVAL_LV2 && NumberUtils.compare(us.getPoApprovalLv2(), 1)) {
                    return true;
                } else if (access == Access.PO_APPROVAL_LV3 && NumberUtils.compare(us.getPoApprovalLv3(), 1)) {
                    return true;
                } else if (access == Access.LABORATORY_APPROVAL_LV1 && NumberUtils.compare(us.getLaboratoryApprovalLv1(), 1)) {
                    return true;
                } else if (access == Access.LABORATORY_APPROVAL_LV2 && NumberUtils.compare(us.getLaboratoryApprovalLv2(), 1)) {
                    return true;
                } else if (access == Access.LABORATORY_APPROVAL_LV3 && NumberUtils.compare(us.getLaboratoryApprovalLv3(), 1)) {
                    return true;
                } else if (access == Access.SAMPLE_APPROVAL_LV1 && NumberUtils.compare(us.getSampleApprovalLv1(), 1)) {
                    return true;
                } else if (access == Access.SAMPLE_APPROVAL_LV2 && NumberUtils.compare(us.getSampleApprovalLv2(), 1)) {
                    return true;
                } else if (access == Access.SAMPLE_APPROVAL_LV3 && NumberUtils.compare(us.getSampleApprovalLv3(), 1)) {
                    return true;
                }
            }
        }

        // 未找到权限
        return false;

    }*/

    /**
     *  验证权限，如果没有权限，则抛出异常
     * @param access 访问资源
     * @param model 包含customerId/departmentId属性的数据模型
     */
    /*public static void checkAccess(Constants.Access access, BaseModel model) {
        String customerId = null;
        String customerName = null;
        String customerAlias = null;
        String departmentId = null;
        String departmentName = null;

        if (model instanceof BaseCustomerDepartmentModel) {
            customerId = ((BaseCustomerDepartmentModel) model).getCustomerId();
            customerName = ((BaseCustomerDepartmentModel) model).getCustomerName();
            customerAlias = ((BaseCustomerDepartmentModel) model).getCustomerAlias();
            departmentId = ((BaseCustomerDepartmentModel) model).getDepartmentId();
            departmentName = ((BaseCustomerDepartmentModel) model).getDepartmentName();
        } else if (model instanceof BaseCustomerModel) {
            customerId = ((BaseCustomerModel) model).getCustomerId();
            customerName = ((BaseCustomerModel) model).getCustomerName();
            customerAlias = ((BaseCustomerModel) model).getCustomerAlias();
        } else {
            try {
                Class<? extends BaseModel> clazz = model.getClass();

//                Field customerIdField = clazz.getDeclaredField("customerId");
//                Field customerNameField = clazz.getDeclaredField("customerName");
//                Field departmentIdField = clazz.getDeclaredField("departmentId");
//                Field departmentNameField = clazz.getDeclaredField("departmentName");
//
//                if (!customerIdField.isAccessible()) {
//                    customerIdField.setAccessible(true);
//                }
//                if (!customerNameField.isAccessible()) {
//                    customerNameField.setAccessible(true);
//                }
//                if (!departmentIdField.isAccessible()) {
//                    departmentIdField.setAccessible(true);
//                }
//                if (!departmentNameField.isAccessible()) {
//                    departmentNameField.setAccessible(true);
//                }

                Field[] fields = clazz.getDeclaredFields();
                for (Field f : fields) {
                    if ("customerId".equals(f.getName())) {
                        if (!f.isAccessible()) {
                            f.setAccessible(true);
                        }
                        customerId = (String) f.get(model);
                    } else if ("customerName".equalsIgnoreCase(f.getName())) {
                        if (!f.isAccessible()) {
                            f.setAccessible(true);
                        }
                        customerName = (String) f.get(model);
                    } else if ("customerAlias".equalsIgnoreCase(f.getName())) {
                        if (!f.isAccessible()) {
                            f.setAccessible(true);
                        }
                        customerAlias = (String) f.get(model);
                    } else if ("departmentId".equalsIgnoreCase(f.getName())) {
                        if (!f.isAccessible()) {
                            f.setAccessible(true);
                        }
                        departmentId = (String) f.get(model);
                    } else if ("departmentName".equalsIgnoreCase(f.getName())) {
                        if (!f.isAccessible()) {
                            f.setAccessible(true);
                        }
                        departmentName = (String) f.get(model);
                    }
                }
            } catch (SecurityException | IllegalAccessException | IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        if (customerId != null) {
            if (departmentId != null) {
                if (!hasAccess(access, customerId, departmentId)) {
                    if (customerName == null || customerName.length() == 0) {
                        customerName = customerId;
                    }
                    if (departmentName == null || departmentName.length() == 0) {
                        departmentName = departmentId;
                    }
                    throw new VegaUnauthorizedAccessException("Require [" + access.toString() + "] permission of [" + customerName + "] [" + departmentName + "]");
                }
            } else {
                if (!hasAccess(access, customerId, customerId)) {
                    if (customerName == null || customerName.length() == 0) {
                        customerName = customerId;
                    }
                    throw new VegaUnauthorizedAccessException("Require [" + access.toString() + "] permission of [" + customerName + "]");
                }
            }
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("No customerId/departmentId properties found in bean " + model.getClass().getName());
            }
        }
    }*/

}
