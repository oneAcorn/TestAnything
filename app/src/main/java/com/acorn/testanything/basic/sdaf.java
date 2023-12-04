package com.acorn.testanything.basic;

import com.acorn.testanything.extendfunc.ByteArrExtendsKt;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by acorn on 2022/9/21.
 */
class sdaf {
    public static void main(String[] args) {
        System.out.println("ret:"+getUnsignedInt(ByteArrExtendsKt.toByteArrayBigOrder("c7c7a578")));
        System.out.println("ret:"+getUnsignedInt2(ByteArrExtendsKt.toByteArrayBigOrder("c7c7a578")));
        System.out.println("ret:"+getUnsignedInt(ByteArrExtendsKt.toByteArrayBigOrder("c7c7a578")));
    }

    public static long getUnsignedInt(byte[] data)
    {
        ByteBuffer bb = ByteBuffer.wrap(data);
        bb.order(ByteOrder.BIG_ENDIAN);
        return bb.getInt() & 0xffffffffL;
    }

    public static int getUnsignedInt2(byte[] data){
        ByteBuffer bb = ByteBuffer.wrap(data);
        bb.order(ByteOrder.BIG_ENDIAN);
        return (int)(bb.getInt() & 0xffffffffL);
    }
}
