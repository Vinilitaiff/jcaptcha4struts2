# JCaptcha4Struts2 #

## Quick Start Guide ##

### 1. Installation ###

Installing and configuring JCaptcha4Struts2 is straight forward.

  1. Download the latest version of the plugin from http://code.google.com/p/jcaptcha4struts2/downloads/list
  1. Include the JAR file with your Struts2 Application.
  1. Enable JCaptcha support by extending the provided base action class in your actions (which handle captcha input), or by configuring the intercepter.

### 2. Enabling JCaptcha support in your application ###

There are two ways to enable your application to use JCaptcha using this plugin.

  1. Extend the Action which handles captcha from the provided `JCaptchaBaseAction` class
  1. Configure the Action to be intercepted by the provided `JCaptchaValidationIntercepter`.

Note that you should do only one of these. If you use both of these for a single action, it would result in exceptions (due to double validation for same captcha).

#### 2.1 Exending JCaptchaBaseAction ####

This is fairly straight forward. Just extend your action class from `com.google.code.jcaptcha4struts2.common.actions.JCaptchaBaseAction` class.

Following example shows a sample action which does exactly that. Note that there's nothing special realted to JCaptcha, except for the fact that it is extended from `JCaptchaBaseAction` class. This class transparently handles all activities necessary to validate captcha and to generate error messages. Note that this class extends from Struts2 `ActionSupport` class (thus all convenience methods are available).


```
public class MyFormActionWithCaptcha extends JCaptchaBaseAction {

	private static final long serialVersionUID = 6643871470200594031L;
	
	private String message;

	@Override
	public String execute() throws Exception {
		// Your action code here
		return super.execute();
	}
	
	
	@Override
	public void validateInput() {
		// Validation Logic Here
		
		// Note that you cannot override validate() method (it's final).
		// Instead, the logic should be provided in this method.
	}


	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	
}
```

#### 2.2 Using JCaptchaValidationIntercepter ####

Alternatively, you may require not to use any code which is specific to the plugin in your Java Source, or you may require to extend another class, apart from the `JCaptchaBaseAction`  class. In such scenarios, you may configure Struts2 to use `JCaptchaValidationIntercepter` to intercept action invocation and to perform captcha validation transparently.

Following code snippet from struts.xml shows how this is done.

```
	<package name="mypackage" extends="jcaptcha4struts2-default">
	
		<action name="myformaction" class="com.my.app.FormAction">
			
			<!-- Interceptor Stack -->
			<interceptor-ref name="jcaptchaDefaultStack" />
			
			<result name="input">/form.jsp</result>
			<result>/done.jsp</result>
		</action>	
		
	</package>
```

As shown here, the action package should extend from `jcaptcha4struts2-default` package (which inturn extends struts-default). If this is not possible, you will have to configure the intercepter yourself by exposing the intercepter class `com.google.code.jcaptcha4struts2.core.interceptors.JCaptchaValidationIntercepter`. If you use the given package as your base package, this is done for you and you can simply refer to the intercepter by the name `jcaptchaDefaultStack`. This intercepter stack consists of the default struts2 stack followed  by the `JCaptchaValidationIntercepter`.

The rest of the code is your normal action configuration.

### 3. Writing your JSP ###

Write your JSP file, and add the following tag library declaration, along with struts tag library declaration.

```
<%@ taglib uri="http://code.google.com/p/jcaptcha4struts2/taglib/2.0" prefix="jcaptcha" %>
```

Then with in your form, just include `<jcaptcha:image label="Label Name Here"/>` tag.

Example:
```
<s:form action="myformaction">
	<s:textfield name="text" label="Text"/>
	<!-- Rest of Controls -->

	<!-- The JCaptcha Control -->
	<jcaptcha:image label="Type the text "/>

	<s:submit />
</s:form>
```

### 4. And .... ###
Congratulations ! You have integrated JCaptcha support to your Struts 2 Application.