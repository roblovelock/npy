package org.jetbrains.bio.npy

import org.junit.Assert.assertArrayEquals
import org.junit.Test
import kotlin.test.assertEquals

/**
 * The test file was generated by the following Python script:
 *
 *     import numpy as np
 *
 *     np.savez("example.npz",
 *              x_i1=np.array([1, 2, 3, 4], dtype="|i1"),
 *              x_i2=np.array([1, 2, 3, 4, 4096], dtype=">i2"),
 *              x_u4=np.array([1, 2, 3, 4], dtype=">u4"),
 *              x_i8=np.array([1, 2, 3, 4, 65536], dtype="<i8"),
 *              x_f4=np.array([1, 2, 3, 4], dtype="<f4"),
 *              x_f8=np.array([1, 2, 3, 4], dtype="<f8"),
 *              x_b=np.array([True, True, True, False], dtype="|b1"),
 *              x_S3=np.array(["aha", "hah"], dtype="|S3"))
 *
 * NumPy version 1.10.2.
 */
class NpzFileTest {
    @Test fun list() {
        NpzFile(Examples["example.npz"]).use { npzf ->
            assertEquals(setOf("x_i1", "x_i2", "x_u4", "x_i8",
                               "x_f4", "x_f8", "x_b", "x_S3"),
                         npzf.list().toSet())
        }
    }

    @Test fun get() {
        NpzFile(Examples["example.npz"]).use { npzf ->
            assertArrayEquals(byteArrayOf(1, 2, 3, 4), npzf["x_i1"] as ByteArray)
            assertArrayEquals(shortArrayOf(1, 2, 3, 4, 4096), npzf["x_i2"] as ShortArray)
            assertArrayEquals(intArrayOf(1, 2, 3, 4), npzf["x_u4"] as IntArray)
            assertArrayEquals(longArrayOf(1, 2, 3, 4, 65536), npzf["x_i8"] as LongArray)

            assertArrayEquals(floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f),
                              npzf["x_f4"] as FloatArray,
                              Math.ulp(1.0f))
            assertArrayEquals(doubleArrayOf(1.0, 2.0, 3.0, 4.0),
                              npzf["x_f8"] as DoubleArray,
                              Math.ulp(1.0))

            assertArrayEquals(booleanArrayOf(true, true, true, false),
                              npzf["x_b"] as BooleanArray)

            assertArrayEquals(arrayOf("aha", "hah"), npzf["x_S3"] as Array<String>)
        }
    }
}
