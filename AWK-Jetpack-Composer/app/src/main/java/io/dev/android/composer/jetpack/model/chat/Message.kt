package io.dev.android.composer.jetpack.model.chat

data class Message(
    val id: Int,
    val user: User,
    val content: String
) {
    companion object {
        fun testConversation(): List<Message> {
            val testList: MutableList<Message> = mutableListOf()
            for (i in 1 until 10) {
                if (((i - 1) % 2) != 0) {
                    testList.add(Message(i, User.admin, "Warning message: you have reach Zone: $i, please exit right now !"))
                    continue
                }

                testList.add(Message(i, User.admin, "Hey there, this is the test message which reference the id with $i"))
            }

            return testList
        }
    }
}