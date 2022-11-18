package org.sopt.sample.util.extensions

operator fun <T> Collection<T>.times(count: Int): List<T> = List(count) { this }.flatten()