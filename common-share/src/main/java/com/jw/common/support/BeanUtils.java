package com.jw.common.support;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description Bean工具类
 * @author 		Having
 * @date 		2016-05-24
 * @version 	1.0
 */
@SuppressWarnings(value={"rawtypes"})
public class BeanUtils {          
	
	private static Map<String, Method[]> methodMap = new ConcurrentHashMap<String, Method[]> ();

	/*
	 * @description bean拷贝
	 * @param 		(Object)target 目标bean,要拷入的bean
	 * @param		(Object)source 资源bean,数据来源bean
	 * @return 		void
	 */
	public static void copySimpleObject(Object target, Object source, boolean isExactMatch) {
		
		if (null == target|| null == source) {
			return;
		}

    	Map<String, Method> sourceMethodMap = new HashMap<String, Method>();
    	Map<String, Method> sourceMethodMapU = new HashMap<String, Method>();

        List targetMethodList = getSetter(target.getClass());
        List sourceMethodList = getGetter(source.getClass());
        
        for (int i = 0; i < sourceMethodList.size(); i++) {
        	Method sourceMethod = (Method)sourceMethodList.get(i);
        	sourceMethodMap.put(sourceMethod.getName(), sourceMethod);
        	sourceMethodMapU.put(sourceMethod.getName().toUpperCase(), sourceMethod);
        }
        
        for (int i = 0; i < targetMethodList.size(); i++) {
        	
        	Method targetMethod = (Method) targetMethodList.get(i);
        	Method sourceMethod = null;
        	
        	StringBuilder sb = new StringBuilder();
            sb.append("get").append(targetMethod.getName().substring(3));
	
            if (isExactMatch) {
            	sourceMethod = (Method)sourceMethodMap.get(sb.toString());
            } else {
            	sourceMethod = (Method)sourceMethodMapU.get(sb.toString().toUpperCase());
            }

            if(sourceMethod != null) {
            
	    		try {
	    			
					Object val = (Object) sourceMethod.invoke(source, new Object[]{});
					
					if(val != null) {
						targetMethod.invoke(target, new Object[]{val});
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				} 
            }
        } 
    }
		  
	
	private static List<Method> getSetter(Class cl) {
		
		List<Method> resultList = new ArrayList<Method>();
		
    	Method methods[] = getMethods(cl);
        int len = methods.length;
        
        for(int i = 0; i < len; i++) {
        	
        	Method method = methods[i];
        	String methodName = method.getName();
        	
        	if(methodName.length() > 3 && method.getParameterTypes().length == 1 && "set".equals(methodName.substring(0,3))){
                resultList.add(method);
            }
        }
        
        return resultList;
     }
          
          
	private static List<Method> getGetter(Class cl) {                                                                                                      
        
		List<Method> resultList = new ArrayList<Method>();
		
    	Method methods[] = getMethods(cl);
        int len = methods.length;
        
        for(int i = 0; i < len; i++) {
        	
        	Method method = methods[i];
        	String methodName = method.getName();

        	if(methodName.length() > 3 && method.getParameterTypes().length == 0 && "get".equals(methodName.substring(0,3))){
                resultList.add(method);
            }
		}
	
        return resultList;                                                                    
	}         
	
                                                                                                               
	private static Method[] getMethods(Class cl) {                                                                                                      
          initClassMethod(cl);                                                                             
          return (Method[])methodMap.get(cl.getName());                                                    
    }                                                                                                      
          
    
    private static void initClassMethod(Class cl) {
    	
    	String key = cl.getName();                                                                       
        
    	if(methodMap.containsKey(key)){  
    		
    		return; 
    		
        } else { 
   
        	List<Method> list = getAllClassMethod(cl);  
        	
        	Method methods[] = new Method[list.size()];                                                  
        	list.toArray(methods);                                                                       
        	methodMap.put(key, methods);                                                                           
        }   
    }                                                                                                      
        
    
	private static List<Method> getAllClassMethod(Class cl) {                                                                                                      
          
		List<Method> returnList = new ArrayList<>();
		
		Method methods[] = cl.getMethods();                                                      
        int size = methods.length;  
        
        for(int i = 0; i < size; i++){ 
        	
        	Method method = methods[i];                                                                  
        	returnList.add(method);                                                                                
        }

        return returnList;
    }    
	
	
	/*
	 * @description 根据stirng类型查找object是否存在对应的set方法  如果存在进行set赋值
	 * @param 		(Object)obj 	目标obj,要进行set赋值的bean
	 * @param		(String)str 	查找的string字段
	 * @param		(Object)value 	要set的数值
	 * @return 		void
	 */
	public static void setValueByStringField(Object obj, String str, Object value) { 
		
		if (null == obj || null == str || null == value) {
			return;
		}
		
 		String tempName = str;
 		tempName = tempName.substring(0, 1).toUpperCase() + str.substring(1);
 		str = "set" + tempName;
 		
// 		try {
//	 		Method method = obj.getClass().getMethod(str, new Class[] {Class.forName(strType)});
//	 		method.invoke(obj, new Object[]{value});
// 		} catch (Exception e) {
//			e.printStackTrace();
//		}
 		
		try {
			List methodList = getSetter(obj.getClass());
			
			for (int i = 0; i < methodList.size(); i++) {
			 	
			 	Method method = (Method) methodList.get(i);
			 	
			 	if (str.equals(method.getName())){

			 		method.invoke(obj, new Object[]{value});
			 	}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	
	
	/*
	 * @description 根据stirng类型查找object是否存在对应的get方法  如果存在取其值
	 * @param 		(Object)obj 目标obj,要进行get赋值的bean
	 * @param		(String)str 查找的string字段
	 * @return 		Object		返回值
	 */
	public static Object getValueByStringField(Object obj, String str) { 
		
		Object value = null;
		
		if (null == obj || null == str ) {
			return null;
		}
		
 		String tempName = str;
 		tempName = tempName.substring(0, 1).toUpperCase() + str.substring(1);
 		str = "get" + tempName;
						                                       	                                       	
		try {
	 		Method method = obj.getClass().getMethod(str); 
	 		value = (Object) method.invoke(obj, new Object[]{});
 		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return value;
    }
	
	
	/*
	 * @description 根据stirng类型查找object是否存在对应的get方法  如果存在取其值
	 * @param 		(Object)obj 目标obj,要进行get赋值的bean
	 * @param		(String [])strs 查找的string字段数组
	 * @return 		Map<String, Object>		返回值
	 */
	public static Map<String, Object> getValueByStringFields(Object obj, String [] strs) { 
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (null == obj || null == strs || strs.length == 0) {
			return null;
		}
		
		for (int i = 0; i < strs.length; i++) {
			
			String str = strs[i].trim();
	 		String tempName = str;
	 		tempName = tempName.substring(0, 1).toUpperCase() + str.substring(1);
	 		str = "get" + tempName;
							                                       	                                       	
			try {
				
		 		Method method = obj.getClass().getMethod(str); 
		 		Object value = (Object) method.invoke(obj, new Object[]{});
		 		map.put(strs[i], value);
		 		
	 		} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return map;
    }
}