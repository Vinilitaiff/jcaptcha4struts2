This wiki page provides guidelines for using different Image Captcha Service engines with JCaptcha4Struts2.

<b>This applies to version 2.0 and above only.</b>

# Introduction #

JCaptcha4Struts2 by default uses the com.octo.captcha.service.image.DefaultManageableImageCaptchaService class to generate captcha. This class is instantiated internally within the plugin.

However, developers may require to use different ImageCaptchaService implementations due to various reasons, including clustered environments. There are two approaches to provide different captcha engines to the plugin.

<b>1. Programmatical Approach</b>

In programmatical approach, developers can directly attach an instance of any class that implements com.octo.captcha.service.image.ImageCaptchaService interface to the plugin.

For example, if your implementation of ImageCaptchaService is MyImageCaptchaServiceImpl, all you have to do is, add the following line into some place where it will get invoked once before the actions are used (ideally, you could put this into a ServletContextListener).
```
   JC4S2Config.getInstance().setImageCaptchaService(MyImageCaptchaServiceImpl());
```

<b>2. Spring-based Approach</b>

Alternatively, if you are using Spring, you could use the following bean definition to achieve the same.

This assumes that your Captcha Engine implementation is a bean configured in the same context under id myCaptchaServiceRef. Also you have to make sure that jc4s2config bean is configured prior to any action invocation.
```
<bean id="jc4s2config" class="com.google.code.jcaptcha4struts2.core.beans.JC4S2Config" factory-method="getInstance">
	<property name="imageCaptchaService" ref="myCaptchaServiceRef" />
</bean>
```

Similarly, you may use any other IoC Framework to achieve the same.