package neilyich.polynomialfactorizationservice.config

import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.stereotype.Component
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Proxy

@Component
class WrapExceptionsBeanPostProcessor: BeanPostProcessor {
    private val beans = mutableMapOf<String, Class<*>>()
    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any? {
        println("try")
        if (bean.javaClass.isAnnotationPresent(WrapExceptions::class.java)) {
            beans[beanName] = bean.javaClass
            println("added new class: ${bean.javaClass.name}")
        }
        return bean
    }

    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any? {
        val beanClass = beans[beanName] ?: return bean
        return Proxy.newProxyInstance(beanClass.classLoader, beanClass.interfaces) { _, method, arguments ->
            // if method does not return string or not public
            if (method.returnType != String::class.java || method.modifiers % 2 == 0) {
                return@newProxyInstance method.invoke(bean, arguments)
            } else {
                try {
                    return@newProxyInstance method.invoke(bean, *arguments)
                } catch (ite: InvocationTargetException) {
                    val e = ite.cause
                    e?.printStackTrace()
                    return@newProxyInstance "ERROR: ${e?.message}"
                } catch (e: Exception) {
                    return@newProxyInstance "Unknown error: (${e.message})"
                }
            }
        }
    }
}