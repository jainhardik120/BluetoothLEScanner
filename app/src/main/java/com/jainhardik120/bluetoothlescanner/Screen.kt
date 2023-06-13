package com.jainhardik120.bluetoothlescanner


sealed class Screen(val route: String){
    object HomeScreen : Screen("home_screen")
    object DeviceScreen : Screen("device_screen")

    fun withArgs(vararg args: String):String{
        return buildString {
            append(route)
            args.forEach { arg->
                append("/$arg")
            }
        }
    }
}
