package io.dev.build.convention.sentry.track

import io.dev.build.convention.sentry.util.PrefixConstant.MAIN_APP_PACKAGE_PREFIX
import io.dev.build.convention.sentry.util.PrefixConstant.METHOD_DESCRIPTOR_VIEW
import io.dev.build.convention.sentry.util.PrefixConstant.ON_CLICK_METHOD_NAME
import io.dev.build.convention.sentry.util.SentryConfig.IS_ENABLE_HOOK
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor

class TrackClassVisitor(
    private val isEnableTrack: Boolean,
    private val apiVersion: Int,
    private val classVisitor: ClassVisitor
) : ClassVisitor(apiVersion, classVisitor) {

    private var className: String? = null
    private val isEnableHook = IS_ENABLE_HOOK

    /**
     * Visits the header of the class.
     *
     * @param version the class version. The minor version is stored in the 16 most significant bits,
     * and the major version in the 16 least significant bits.
     * @param access the class's access flags (see [Opcodes]). This parameter also indicates if
     * the class is deprecated [Opcodes.ACC_DEPRECATED] or a record [     ][Opcodes.ACC_RECORD].
     * @param name the internal name of the class (see [Type.getInternalName]).
     * @param signature the signature of this class. May be null if the class is not a
     * generic one, and does not extend or implement generic classes or interfaces.
     * @param superName the internal of name of the super class (see [Type.getInternalName]).
     * For interfaces, the super class is [Object]. May be null, but only for the
     * [Object] class.
     * @param interfaces the internal names of the class's interfaces (see [     ][Type.getInternalName]). May be null.
     */
    override fun visit(
        version: Int,
        access: Int,
        name: String?,
        signature: String?,
        superName: String?,
        interfaces: Array<out String>?
    ) {
        super.visit(version, access, name, signature, superName, interfaces)
        className = name
        if (!isEnableTrack) {
            return
        }

        if (name?.startsWith(MAIN_APP_PACKAGE_PREFIX) == true) {
            interfaces?.forEach {
                checkWithViewInterface(it)
            }
        }
    }

    /**
     * Visits a method of the class. This method *must* return a new [MethodVisitor]
     * instance (or null) each time it is called, i.e., it should not return a previously
     * returned visitor.
     *
     * @param access the method's access flags (see [Opcodes]). This parameter also indicates if
     * the method is synthetic and/or deprecated.
     * @param name the method's name.
     * @param descriptor the method's descriptor (see [Type]).
     * @param signature the method's signature. May be null if the method parameters,
     * return type and exceptions do not use generic types.
     * @param exceptions the internal names of the method's exception classes (see [     ][Type.getInternalName]). May be null.
     * @return an object to visit the byte code of the method, or null if this class
     * visitor is not interested in visiting the code of this method.
     */
    override fun visitMethod(
        access: Int,
        name: String?,
        descriptor: String?,
        signature: String?,
        exceptions: Array<out String>?
    ): MethodVisitor {
        var methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions)
        if (isEnableTrack && isEnableHook) {
            if (name.equals(ON_CLICK_METHOD_NAME)
                && descriptor.equals(METHOD_DESCRIPTOR_VIEW)
            ) {
                methodVisitor = TrackMethodVisitor(
                    className = className,
                    apiVersion = apiVersion,
                    methodVisitor = methodVisitor,
                    access = access,
                    methodName = name,
                    methodDescriptor = descriptor
                )
            }
        }

        return methodVisitor
    }

    private fun checkWithViewInterface(interfaceName: String?) {
        // Check if the interface is View.OnClickListener
        if (interfaceName == "android/view/View\$OnClickListener") {
            println("Find callback interface function with [OnClick] method")
        }
    }
}