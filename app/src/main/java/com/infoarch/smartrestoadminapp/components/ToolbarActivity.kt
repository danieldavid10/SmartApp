package com.infoarch.smartrestoadminapp.components

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.infoarch.smartrestoadminapp.struct.IToolbar

open class ToolbarActivity : AppCompatActivity(), IToolbar {

    protected var _toolbar: Toolbar? = null

    override fun toolbarToLoad(toolbar: Toolbar?) {
        _toolbar = toolbar
        _toolbar?.let { setSupportActionBar(_toolbar) }
    }

    override fun enableHomeDisplay(value: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(value)
    }
}
