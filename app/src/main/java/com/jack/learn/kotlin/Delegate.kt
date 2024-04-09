package com.jack.learn.kotlin

import kotlin.reflect.KProperty

class Delegate {

    operator fun getValue(thisRef: Any?,property: KProperty<*>): String {
        return "$thisRef,${property.name}"
    }


}


interface IUserAction {
    fun attack()

    val filed: Int
        get() = 0
}
class UserDelegate(private val action: IUserAction): IUserAction by action

class UserActionImpl: IUserAction {
    override fun attack() {

    }
}
fun main() {
    UserDelegate(UserActionImpl()).apply {
        attack()
    }
}