package meng.interceptor;

import java.util.Set;

import org.apache.log4j.Logger;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import meng.model.base.SessionInfo;
import meng.model.base.Torganization;
import meng.model.base.Tresource;
import meng.model.base.Trole;
import meng.util.ConfigUtil;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

/**
 * 权限拦截器
 * @author Administrator
 *
 */
public class SecurityInterceptor extends MethodFilterInterceptor{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SecurityInterceptor.class);

	@Override
	protected String doIntercept(ActionInvocation actionInvocation) throws Exception {
		//ActionContext actionContext = actionInvocation.getInvocationContext();
		SessionInfo sessionInfo = (SessionInfo) ServletActionContext.getRequest().getSession().getAttribute(ConfigUtil.getSessionInfoName());
		String servletPath = ServletActionContext.getRequest().getServletPath();

		servletPath = StringUtils.substringBeforeLast(servletPath, ".");// 去掉后面的后缀 *.td *.action之类的

		logger.info("进入权限拦截器->访问的资源为：[" + servletPath + "]");

		Set<Trole> roles = sessionInfo.getTuser().getTroles();
		for (Trole role : roles) {
			for (Tresource resource : role.getTresources()) {
				if (resource != null && StringUtils.equals(resource.getUrl(), servletPath)) {
					return actionInvocation.invoke();
				}
			}
		}
		Set<Torganization> organizations = sessionInfo.getTuser().getTorganizations();
		for (Torganization organization : organizations) {
			for (Tresource resource : organization.getTresources()) {
				if (resource != null && StringUtils.equals(resource.getUrl(), servletPath)) {
					return actionInvocation.invoke();
				}
			}
		}

		String errMsg = "您没有访问此功能的权限！功能路径为[" + servletPath + "]请联系管理员给你赋予相应权限。";
		logger.info(errMsg);
		ServletActionContext.getRequest().setAttribute("msg", errMsg);
		return "noSecurity";
	}

}
