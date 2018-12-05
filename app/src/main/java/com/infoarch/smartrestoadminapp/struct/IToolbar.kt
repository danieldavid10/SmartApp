package com.infoarch.smartrestoadminapp.struct

import android.support.v7.widget.Toolbar

interface IToolbar {
    fun toolbarToLoad(toolbar: Toolbar?)
    fun enableHomeDisplay(value: Boolean)
}