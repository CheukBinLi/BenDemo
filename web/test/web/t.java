package web;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class t {
	public static void main(String[] args) throws UnsupportedEncodingException {
		System.out.println(new String(
				"D048-?昏.浜ゆ.?ュ?澶辫触-?ユ.[2015/05/02] 娴.按??942740977]"
						.getBytes("gbk"), "utf-8"));
		
		SimpleDateFormat dataformat = new SimpleDateFormat("yyyyMMdd");
		Date now = new Date();
		System.out.println(dataformat.format(now));
		File f=new File("d:/boomhope-tsbank-platform/WEB-INF/classes/cn/com/boomhope/tsbank/platform/controller/system");
		f.mkdirs();
	}
}
