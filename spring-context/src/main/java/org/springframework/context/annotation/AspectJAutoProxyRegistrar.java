/*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.context.annotation;

import org.springframework.aop.config.AopConfigUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

/**
 * Registers an {@link org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator
 * AnnotationAwareAspectJAutoProxyCreator} against the current {@link BeanDefinitionRegistry}
 * as appropriate based on a given @{@link EnableAspectJAutoProxy} annotation.
 *
 * @author Chris Beams
 * @author Juergen Hoeller
 * @since 3.1
 * @see EnableAspectJAutoProxy
 */
class AspectJAutoProxyRegistrar implements ImportBeanDefinitionRegistrar {

	/**
	 * Register, escalate, and configure the AspectJ auto proxy creator based on the value
	 * of the @{@link EnableAspectJAutoProxy#proxyTargetClass()} attribute on the importing
	 * {@code @Configuration} class.
	 */
	@Override
	public void registerBeanDefinitions(
			AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

		/*
		 * 导入 AnnotationAwareAspectJAutoProxyCreator 组件，beanName= org.springframework.aop.config.internalAutoProxyCreator
		 * 该组件是一个BPP和BeanFactoryAware，故重点关注BPP的实例化、初始化前后共4个回调方法及 setBeanFactory()方法
		 *
		 *  AnnotationAwareAspectJAutoProxyCreator
		 *  	AspectJAwareAdvisorAutoProxyCreator
		 *  		AbstractAdvisorAutoProxyCreator
		 *  			AbstractAutoProxyCreator
		 *  				ProxyProcessorSupport
		 *						implements SmartInstantiationAwareBeanPostProcessor, BeanFactoryAware
		 *
		 *
		 *  AbstractAutoProxyCreator.setBeanFactory()
		 *  子类 AbstractAdvisorAutoProxyCreator 重写了setBeanFactory()方法:
		 *      AbstractAdvisorAutoProxyCreator.setBeanFactory() --> initBeanFactory()
		 *          AnnotationAwareAspectJAutoProxyCreator 重写了 initBeanFactory() 方法
		 *
		 * 附：
		 *   1、setBeanFactory() 方法在 AbstractAutowireCapableBeanFactory.invokeAwareMethods() 触发
		 *   2、postProcessBeforeInstantiation() 方法在 AbstractAutowireCapableBeanFactory.resolveBeforeInstantiation 触发
		 *   3、postProcessAfterInitialization() 方法在 AbstractAutowireCapableBeanFactory.applyBeanPostProcessorsBeforeInitialization() 触发
		 */
		AopConfigUtils.registerAspectJAnnotationAutoProxyCreatorIfNecessary(registry);

		AnnotationAttributes enableAspectJAutoProxy =
				AnnotationConfigUtils.attributesFor(importingClassMetadata, EnableAspectJAutoProxy.class);
		if (enableAspectJAutoProxy.getBoolean("proxyTargetClass")) {
			AopConfigUtils.forceAutoProxyCreatorToUseClassProxying(registry);
		}
		if (enableAspectJAutoProxy.getBoolean("exposeProxy")) {
			AopConfigUtils.forceAutoProxyCreatorToExposeProxy(registry);
		}
	}

}
