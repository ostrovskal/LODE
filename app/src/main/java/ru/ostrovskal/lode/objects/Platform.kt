package ru.ostrovskal.lode.objects

import android.graphics.Point

class Platform(pos: Point, private val isVert: Boolean, private val length: Int) : LodeObject(pos)