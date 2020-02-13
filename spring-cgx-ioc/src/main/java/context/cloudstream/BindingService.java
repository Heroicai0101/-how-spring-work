///*
// * Copyright 2015-2016 the original author or authors.
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
//package context.annotation.atImport;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.beans.BeanUtils;
//import org.springframework.boot.bind.RelaxedDataBinder;
//import org.springframework.cloud.stream.binder.*;
//import org.springframework.cloud.stream.config.BindingServiceProperties;
//import org.springframework.util.CollectionUtils;
//import org.springframework.util.StringUtils;
//import org.springframework.validation.beanvalidation.CustomValidatorBean;
//
//import java.util.*;
//
///**
// * Handles binding of input/output targets by delegating to an underlying {@link Binder}.
// *
// * @author Mark Fisher
// * @author Dave Syer
// * @author Marius Bogoevici
// * @author Ilayaperumal Gopinathan
// * @author Gary Russell
// */
//public class BindingService {
//
//	private final Log log = LogFactory.getLog(BindingService.class);
//
//	private final BindingServiceProperties bindingServiceProperties;
//
//	private final Map<String, Binding<?>> producerBindings = new HashMap<String, Binding<?>>();
//
//
//	private BinderFactory binderFactory;
//
//	public BindingService(
//			BindingServiceProperties bindingServiceProperties,
//			BinderFactory binderFactory) {
//		this.bindingServiceProperties = bindingServiceProperties;
//		this.binderFactory = binderFactory;
//	}
//
//
//	@SuppressWarnings("unchecked")
//	public <T> Binding<T> bindProducer(T output, String outputName) {
//		String bindingTarget = this.bindingServiceProperties
//				.getBindingDestination(outputName);
//		Binder<T, ?, ProducerProperties> binder = (Binder<T, ?, ProducerProperties>) getBinder(
//				outputName, output.getClass());
//		ProducerProperties producerProperties = this.bindingServiceProperties
//				.getProducerProperties(outputName);
//		if (binder instanceof ExtendedPropertiesBinder) {
//			Object extension = ((ExtendedPropertiesBinder) binder)
//					.getExtendedProducerProperties(outputName);
//			ExtendedProducerProperties extendedProducerProperties = new ExtendedProducerProperties<>(
//					extension);
//			BeanUtils.copyProperties(producerProperties, extendedProducerProperties);
//			producerProperties = extendedProducerProperties;
//		}
//		validate(producerProperties);
//		Binding<T> binding = binder.bindProducer(bindingTarget, output,
//				producerProperties);
//		this.producerBindings.put(outputName, binding);
//		return binding;
//	}
//
//
//
//	public void unbindProducers(String outputName) {
//		Binding<?> binding = this.producerBindings.remove(outputName);
//		if (binding != null) {
//			binding.unbind();
//		}
//		else if (log.isWarnEnabled()) {
//			log.warn("Trying to unbind '" + outputName + "', but no binding found.");
//		}
//	}
//
//	@SuppressWarnings("unchecked")
//	private <T> Binder<T, ?, ?> getBinder(String channelName, Class<T> bindableType) {
//		String binderConfigurationName = this.bindingServiceProperties.getBinder(channelName);
//		return binderFactory.getBinder(binderConfigurationName, bindableType);
//	}
//
//}
