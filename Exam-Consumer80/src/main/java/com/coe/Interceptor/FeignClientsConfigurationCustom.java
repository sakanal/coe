//package com.coe.Interceptor;
//
//import feign.RequestInterceptor;
//import feign.RequestTemplate;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.cloud.openfeign.EnableFeignClients;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.context.request.RequestAttributes;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//import java.util.Enumeration;
//
//@EnableFeignClients
//@Configuration
//@Slf4j
//public class FeignClientsConfigurationCustom implements RequestInterceptor {
//
//    @Override
//    public void apply(RequestTemplate requestTemplate) {
//        log.info("经过了FeignClientsConfigurationCustom");
//
//        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
//        if (requestAttributes == null) {
//            return;
//        }
//
//        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
////        Enumeration<String> headerNames = request.getHeaderNames();
////        if (headerNames != null) {
////            while (headerNames.hasMoreElements()) {
////                String name = headerNames.nextElement();
////                Enumeration<String> values = request.getHeaders(name);
////                while (values.hasMoreElements()) {
////                    String value = values.nextElement();
////                    template.header(name, value);
//////                    log.info("header:"+template.headers());
////                    log.info("name-->"+name);
////                    log.info("value-->"+value);
////                }
////            }
////        }
//
//        HttpSession session = request.getSession();
//        Enumeration<String> attributeNames = session.getAttributeNames();
//        if (attributeNames!=null){
//            while (attributeNames.hasMoreElements()){
//                String name = attributeNames.nextElement();
//                String value =(String) session.getAttribute(name);
//                log.info("name-->"+name);
//                log.info("value-->"+value);
//                if (value!=null){
//                    requestTemplate.header(name,value);
//                }
//            }
//        }
//
//    }
//
//}
//
