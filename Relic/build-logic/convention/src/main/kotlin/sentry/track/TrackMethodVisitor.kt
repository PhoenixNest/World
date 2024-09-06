package sentry.track

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type
import org.objectweb.asm.commons.AdviceAdapter
import sentry.util.PrefixConstant.METHOD_DESCRIPTOR_VIEW

class TrackMethodVisitor(
    private val className: String?,
    private val apiVersion: Int,
    private val methodVisitor: MethodVisitor?,
    private val access: Int,
    private val methodName: String?,
    private val methodDescriptor: String?
) : AdviceAdapter(apiVersion, methodVisitor, access, methodName, methodDescriptor) {

    private var argumentArray = Type.getArgumentTypes(methodDescriptor)

    /**
     * Generates the "before" advice for the visited method. The default implementation of this method
     * does nothing. Subclasses can use or change all the local variables, but should not change state
     * of the stack. This method is called at the beginning of the method or after super class
     * constructor has been called (in constructors).
     */
    override fun onMethodEnter() {
        super.onMethodEnter()

        for (index in argumentTypes.indices) {
            val type = argumentArray.get(index)
            methodVisitor?.visitVarInsn(
                /* opcode = */ Opcodes.INVOKESTATIC,
                /* varIndex = */ index
            )
            box(type)
            checkOnClick()
        }
    }

    private fun checkOnClick() {
        if (!className.isNullOrEmpty()
            && className.contains("$")
        ) {
            methodVisitor?.visitMethodInsn(
                /* opcode = */ Opcodes.INVOKESTATIC,
                /* owner = */ "",
                /* name = */ "",
                /* descriptor = */ METHOD_DESCRIPTOR_VIEW,
                /* isInterface = */ false
            )
        } else {
            methodVisitor?.visitLdcInsn(className)
            methodVisitor?.visitMethodInsn(
                /* opcode = */ Opcodes.INVOKESTATIC,
                /* owner = */ "",
                /* name = */ "",
                /* descriptor = */ METHOD_DESCRIPTOR_VIEW,
                /* isInterface = */ false
            )
        }
    }
}