package utils;

import chat.ChatManager;
import engineClasses.Engine;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class ServletUtils {
    private static final Object engineLock = new Object();
    private static final Object userManagerLock = new Object();
    private static final Object chatManagerLock = new Object();

    private static final String CHAT_MANAGER_ATTRIBUTE_NAME = "chatManager";
    public static final int INT_PARAMETER_ERROR = Integer.MIN_VALUE;

    public static Engine getEngine(ServletContext servletContext) {
        synchronized (engineLock) {
            if (servletContext.getAttribute("Engine") == null) {
                servletContext.setAttribute("Engine", new Engine());
            }
        }
        return (Engine) servletContext.getAttribute("Engine");
    }
    public static ChatManager getChatManager(ServletContext servletContext) {
        synchronized (chatManagerLock) {
            if (servletContext.getAttribute(CHAT_MANAGER_ATTRIBUTE_NAME) == null) {
                servletContext.setAttribute(CHAT_MANAGER_ATTRIBUTE_NAME, new ChatManager());
            }
        }
        return (ChatManager) servletContext.getAttribute(CHAT_MANAGER_ATTRIBUTE_NAME);
    }
    public static int getIntParameter(HttpServletRequest request, String name) {
        String value = request.getParameter(name);
        if (value != null) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException numberFormatException) {
            }
        }
        return INT_PARAMETER_ERROR;
    }
}

