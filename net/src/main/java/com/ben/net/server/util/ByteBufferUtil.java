package com.ben.net.server.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.ScatteringByteChannel;

/***
 * 16位长度
 * @author hnbh
 *
 */
public class ByteBufferUtil {

	private static ByteBufferUtil instance = new ByteBufferUtil();

	public static ByteBufferUtil instance() {
		return instance;
	}

	private static final int LENGTH_WAY = 16;

	public ByteArrayOutputStream getByte(ScatteringByteChannel scatteringByteChannel) throws IOException {
		ByteBuffer[] buffer = new ByteBuffer[] { ByteBuffer.allocate(1024) };
		scatteringByteChannel.read(buffer, 0, LENGTH_WAY);
		byte[] lenByte = new byte[8];
		buffer[0].get(lenByte);
		buffer[0].flip();
		int length = Integer.valueOf(new String(lenByte));
		buffer[0].clear();
		ByteArrayOutputStream out = new ByteArrayOutputStream(length);
		int count = length / buffer.length;
		int x = length % buffer.length;
		for (int i = 0; i < count; i++) {
			scatteringByteChannel.read(buffer);
			buffer[0].flip();
			out.write(buffer[0].array());
			buffer[0].clear();
		}
		if (x > 0) {
			byte[] temp = new byte[x];
			scatteringByteChannel.read(buffer, 0, x);
			buffer[0].flip();
			buffer[0].get(temp);
			out.write(temp);
		}
		return out;
	}

	public ByteBuffer getBuffer(InputStream in, int size) throws IOException {
		ByteBuffer byteBuffer = ByteBuffer.allocate(size + LENGTH_WAY);
		byte[] buff = new byte[512];
		int len = -1;
		String formatChar = "%0" + LENGTH_WAY + "d";
		byteBuffer.put(String.format(formatChar, size).getBytes());
		while ((len = in.read(buff)) > 0) {
			byteBuffer.put(buff, 0, len);
		}
		return byteBuffer;
	}

	public ByteBuffer getBuffer(byte[] bytes) throws IOException {
		ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length + LENGTH_WAY);
		String formatChar = "%0" + LENGTH_WAY + "d";
		byteBuffer.put(String.format(formatChar, bytes.length).getBytes()).put(bytes);
		return byteBuffer;
	}

	public static void main(String[] args) {
		System.out.println(113 / 6);
		System.out.println(113 % 6);
		System.out.println(113 % 6 == 0);
		System.out.println(107 / 6);
		System.out.println(10 % 3);
		System.out.println(95 % 1024);
		String formatChar = "%0" + LENGTH_WAY + "d";
		String length = String.format(formatChar, 123);
		System.err.println(length);
		System.err.println(length.getBytes().length);
		System.err.println(Integer.MAX_VALUE);
		System.err.println(Long.MAX_VALUE);
	}

}
