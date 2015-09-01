package com.ben.net.server;

import java.nio.channels.SelectionKey;
import java.util.concurrent.Callable;

/***
 * 返回对象并释放
 * 
 * @author Ben-P
 *
 */
public class ReadHadler implements Callable<SelectionKey> {

	private SelectionKey key;

	public SelectionKey call() throws Exception {
		return null;
	}

}
