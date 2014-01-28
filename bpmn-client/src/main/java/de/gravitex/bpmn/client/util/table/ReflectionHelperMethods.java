package de.gravitex.bpmn.client.util.table;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;

public class ReflectionHelperMethods {
	
	private static Logger logger = Logger.getLogger(ReflectionHelperMethods.class);
	
	public static final String PREFIX_GET = "get";

	private static final String PREFIX_IS = "is";
	
	public static final Object[] EMPTY_OBJECT_ARRAY = new Object[] {};

	public static final Class[] EMPTY_CLASS_ARRAY = new Class[] {};

	public static String buildGetterName(Object declaringObject, String memberName) {
		if (getFieldByName(declaringObject, memberName) != null) {
			if (isBooleanType(getFieldByName(declaringObject, memberName).getType())) {
				return PREFIX_IS + StringHelperMethods.firstToUpper(memberName);
			} else {
				return PREFIX_GET + StringHelperMethods.firstToUpper(memberName);
			}
		} else {
			return PREFIX_GET + StringHelperMethods.firstToUpper(memberName);
		}
	}
	
	public static boolean isBooleanType(Class<?> objectClass) {
		return doesObjectTypeEqual(objectClass, boolean.class) || doesObjectTypeEqual(objectClass, Boolean.class);
	}
	
	public static boolean doesObjectTypeEqual(Class<?> objectClass, Class<?> template) {
		if (objectClass == null) {
			return false;
		}
		return objectClass.getSimpleName().equals(template.getSimpleName());
	}

	private static Field getFieldByName(Object declaringObject, String memberName) {
		if (declaringObject != null) {
			try {
				return declaringObject.getClass().getDeclaredField(memberName);
			} catch (SecurityException e) {
				logger.error(e);
			} catch (NoSuchFieldException e) {
				//logger.error(e);
			}
			return null;
		} else {
			return null;
		}
	}
	
	public static String guessGetterName(Object declaringObject, String memberName) {

		/**
		 * Gibt es die Methode get... zum übergebenen (virtuellen) Member ?
		 */
		if (methodExists(declaringObject, PREFIX_GET + StringHelperMethods.firstToUpper(memberName), EMPTY_CLASS_ARRAY)) {
			return PREFIX_GET + StringHelperMethods.firstToUpper(memberName);
		} else if (methodExists(declaringObject, PREFIX_IS + StringHelperMethods.firstToUpper(memberName), EMPTY_CLASS_ARRAY)) {
			/**
			 * Wenn nicht : Gibt es die Methode is... zum übergebenen
			 * (virtuellen) Member ?
			 */
			return PREFIX_IS + StringHelperMethods.firstToUpper(memberName);
		}
		// Nix zu holen...
		return null;
	}
	
	public static boolean methodExists(Object declaringObject, String methodName, Class<?>[] parameters) {

		boolean tmpMethodExists = false;

		try {
			if (declaringObject.getClass().getMethod(methodName, parameters) != null) {
				tmpMethodExists = true;
			}
		} catch (SecurityException e) {
			// logger.error(e);
		} catch (NoSuchMethodException e) {
			// logger.error(e);
		}
		return tmpMethodExists;
	}

	public static Object invokeGetter(Object declaringObject, String getterName) throws NoSuchMethodException {
		return invokeMethodIfFound(declaringObject, getterName);
	}

	private static Object invokeMethodIfFound(Object declaringObject, String methodName) throws NoSuchMethodException {
		Object tmpObject = invokeMethodIfFound(declaringObject, methodName, EMPTY_CLASS_ARRAY, EMPTY_OBJECT_ARRAY);
		return tmpObject;
	}
	
	public static Object invokeMethodIfFound(Object declaringObject, String methodName, Class[] parameters, Object[] values) throws NoSuchMethodException {

		logger.info("invokeMethodIfFound : " + methodName + " parameters : " + parameters + " values : " + values);

		try {
			Method tmpMethod = findObjectMethod(declaringObject, methodName, parameters);
			if (tmpMethod == null) {
				return null;
			}
			Object _value = tmpMethod.invoke(declaringObject, values);
			return _value;
		} catch (IllegalArgumentException e) {
			logger.error(e);
		} catch (SecurityException e) {
			logger.error(e);
		} catch (IllegalAccessException e) {
			logger.error(e);
		} catch (InvocationTargetException e) {
			logger.error(e + " in method " + methodName + " in " + declaringObject.getClass().getSimpleName());
			String classes = "";
			for (Class<?> clazz : parameters) {
				classes += clazz.getSimpleName() + ", ";
			}
			logger.error(e + " with parameter-classes: " + classes);
			Throwable cause = e.getCause();
			logger.error(e + "-cause : " + cause);
			logger.error(e + "-message : " + cause.getMessage());
			e.printStackTrace();

		}
		return null;
	}

	private static Method findObjectMethod(Object declaringObject, String methodName, Class[] parameters) throws NoSuchMethodException {
		if (declaringObject == null) {
			return null;
		}

		try {
			Method tmpMethod = declaringObject.getClass().getMethod(methodName, parameters);
			return tmpMethod;
		} catch (SecurityException e) {
			logger.error(e);
		}
		return null;
	}
}
