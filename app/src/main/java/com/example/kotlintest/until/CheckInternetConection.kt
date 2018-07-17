package com.example.kotlintest.until

import android.content.Context
import android.net.ConnectivityManager

fun CheckInternetConection(context: Context): Boolean =
        ((context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo != null)
