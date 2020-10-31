package com.acorn.testanything.mmkv

import android.os.Parcelable
import com.tencent.mmkv.MMKV
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * 委托模式实现的缓存
 * Created by acorn on 2020/6/9.
 */
@Suppress("UNCHECKED_CAST")
class MMKVDelegate<T>(private val mmkv: MMKV, private val clazz: Class<T>) :
    ReadWriteProperty<Any?, T?> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): T? {
        //不能直接在key里放入userId.因为在静态类中字段在最初初始化时,userId可能为空，
        // 之后登录后即使userId已经部位空，字段传入的key也并不能及时更新
        val realKey = property.name
        return when {
            clazz == Int::class.java -> {
                mmkv.decodeInt(realKey) as? T
            }
            clazz == String::class.java -> {
                mmkv.decodeString(realKey) as? T
            }
            clazz == Long::class.java -> {
                mmkv.decodeLong(realKey) as? T
            }
            clazz == Boolean::class.java -> {
                mmkv.decodeBool(realKey) as? T
            }
            clazz == Float::class.java -> {
                mmkv.decodeFloat(realKey) as? T
            }
            clazz == Double::class.java -> {
                mmkv.decodeDouble(realKey) as? T
            }
            isImplementInterface(clazz, Parcelable::class.java) -> {
                val pClazz = clazz as Class<out Parcelable>
                mmkv.decodeParcelable(realKey, pClazz) as? T
            }
            else -> null
        }
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
        val realKey = property.name
        if (value == null) {
            mmkv.removeValueForKey(realKey)
            return
        }
        when {
            clazz == Int::class.java -> {
                mmkv.encode(realKey, value as Int)
            }
            clazz == String::class.java -> {
                mmkv.encode(realKey, value as String)
            }
            clazz == Long::class.java -> {
                mmkv.encode(realKey, value as Long)
            }
            clazz == Boolean::class.java -> {
                mmkv.encode(realKey, value as Boolean)
            }
            clazz == Float::class.java -> {
                mmkv.encode(realKey, value as Float)
            }
            clazz == Double::class.java -> {
                mmkv.encode(realKey, value as Double)
            }
            isImplementInterface(clazz, Parcelable::class.java) -> {
                mmkv.encode(realKey, value as Parcelable)
            }
            else -> {
                throw IllegalArgumentException("unsupport value")
            }
        }
    }
}