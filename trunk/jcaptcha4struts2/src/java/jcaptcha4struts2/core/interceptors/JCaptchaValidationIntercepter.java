/*
 * Copyright (C) 2008 Yohan Liyanage. 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 */
package jcaptcha4struts2.core.interceptors;

import jcaptcha4struts2.core.validation.JCaptchaValidator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ValidationAware;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * Intercepter which automatically validates JCaptcha responses.
 * <p>
 * <b>Note :</b> Actions intercepted by this intercepter are <b>required</b>
 * to implement {@link ValidationAware} or should be a subclass of
 * a class which implements it (such as {@link ActionSupport}).
 * 
 * @author Yohan Liyanage
 *
 */
public class JCaptchaValidationIntercepter extends AbstractInterceptor {

	private static final long serialVersionUID = 3410862000200098945L;
	private static Log log = LogFactory.getLog(JCaptchaValidationIntercepter.class);
	
	/**
	 * Intercepts invocation and adds a field error if captcha validation
	 * fails.
	 * <p>
	 * Note that intercepting an action which does not implement {@link ValidationAware} or
	 * an action invocation which does not contain captcha information will result in
	 * an exception.
	 * 
	 * @param invocation ActionInvocation
	 */
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		
		log.debug("JCaptchaValidationInterceptor intercepting...");
		
		if (!(invocation.getAction() instanceof ValidationAware)) {
			// Action does not implement ValidationAware, which is a requirement
			// if JCaptchaValidationIntercepter is being used to intercept an action.
		
			log.error("JCaptchaValidationInterceptor intercepted action which does not implement ValidationAware");
			
			throw new IllegalArgumentException("Action does not implement ValidationAware");
		}
		
		// Get Action Reference
		ValidationAware action = (ValidationAware) invocation.getAction();

		// Validate and add field error if fails
		if (! JCaptchaValidator.validate()) {
			log.debug("JCaptchaValidationInterceptor validation failed");
			action.addFieldError("j_captcha_response", "Text is incorrect");
			return Action.INPUT;
		}
		else {
			log.debug("JCaptchaValidationInterceptor validation success");
		}
		
		log.debug("JCaptchaValidationInterceptor completed");
		
		// Continue invocation
		return invocation.invoke();
	}
}
