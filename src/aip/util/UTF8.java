package aip.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;

public class UTF8 {
	
	public static void main(String[] args) {
//		System.out.println("UTF8.main():"+URLEncoder.encode("ی"));
//		System.out.println("UTF8.main():"+URLEncoder.encode("ي"));
		try {
			System.out.println("UTF8.main()"+URLEncoder.encode(URLEncoder.encode("ی"), "iso8859-1"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static boolean imageDirectoriesBuilt = false;
	public static boolean treeDirectoriesBuilt = false;
	public static boolean lawviewDirectoryBuilt = false;
	public static boolean searchDirectoryBuilt = false;
	public static boolean regulationhDirectoryBuilt = false;
	public static boolean advancedsearchDirectoryBuilt = false;
	public static boolean lawStructureDirectoryBuilt = false;

	private static void append(StringBuilder dest, StringBuilder tmp, String ch, String extra) {
		try {
			if (tmp.length() > 0) {
				dest.append(new String(URLDecoder.decode(tmp.toString(), "iso8859-1").getBytes(), "utf8"));
			}
			tmp.delete(0, tmp.length());
			dest.append(ch);
			if (extra.length() > 0) {
				dest.append(new String(URLDecoder.decode(extra, "iso8859-1").getBytes(), "utf8"));
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public static String cnvUTF8(String text) {
		if (text == null || "".equals(text))
			return text;
		String st = "";
		StringBuilder sb = new StringBuilder();
		try {
			st = URLEncoder.encode(text, "iso8859-1");
//			System.out.println(st);

			String[] ar = st.split("%");
			StringBuilder tmpb = new StringBuilder();
			for (int i = 0; i < ar.length; i++) {
				if ("D9".equalsIgnoreCase(ar[i])) {
					if (i + 1 < ar.length) {
						i += 1;
						if (ar[i].startsWith("88")) {
							append(sb, tmpb, "و", ar[i].substring(2));
						} else if (ar[i].startsWith("82")) {
							append(sb, tmpb, "ق", ar[i].substring(2));
						} else if (ar[i].startsWith("81")) {
							append(sb, tmpb, "ف", ar[i].substring(2));
						} else if (ar[i].startsWith("87")) {
							append(sb, tmpb, "ه", ar[i].substring(2));
						} else if (ar[i].startsWith("84")) {
							append(sb, tmpb, "ل", ar[i].substring(2));
						} else if (ar[i].startsWith("86")) {
							append(sb, tmpb, "ن", ar[i].substring(2));
						} else if (ar[i].startsWith("85")) {
							append(sb, tmpb, "م", ar[i].substring(2));
						} else if (ar[i].startsWith("8A")) {
							append(sb, tmpb, "ی", ar[i].substring(2));
						} else if (ar[i].startsWith("BE")) {
							append(sb, tmpb, "پ", ar[i].substring(2));
						} else {
							tmpb.append((i > 0 ? "%" : "") + ar[i]);
						}
					} else {
						tmpb.append((i > 0 ? "%" : "") + ar[i]);
					}
				} else if ("DA".equalsIgnoreCase(ar[i])) {
					if (i + 1 < ar.length) {
						i += 1;
						if (ar[i].startsWith("86")) {
							append(sb, tmpb, "چ", ar[i].substring(2));
						} else if (ar[i].startsWith("98")) {
							append(sb, tmpb, "ژ", ar[i].substring(2));
						} else if (ar[i].startsWith("A9")) {				// By Keshavarz
							append(sb, tmpb, "ک", ar[i].substring(2));
						} else if (ar[i].startsWith("AF")) {
							append(sb, tmpb, "گ", ar[i].substring(2));
						} else {
							tmpb.append((i > 0 ? "%" : "") + ar[i]);
						}
					} else {
						tmpb.append((i > 0 ? "%" : "") + ar[i]);
					}
					// these are only for linux
				} else if ("D8".equalsIgnoreCase(ar[i])) {
					if (i + 1 < ar.length) {
						i += 1;
						if (ar[i].startsWith("8C")) {
							append(sb, tmpb, "ي", ar[i].substring(2));
						} else if(ar[i].startsWith("A3")) {					// By Keshavarz
							append(sb, tmpb, "أ", ar[i].substring(2));
						} else if(ar[i].startsWith("A4")) {					// By Keshavarz
							append(sb, tmpb, "ؤ", ar[i].substring(2));
						}  else if(ar[i].startsWith("A5")) {				// By Keshavarz
							append(sb, tmpb, "إ", ar[i].substring(2));
						} else if(ar[i].startsWith("A2")) {					// By Keshavarz
							append(sb, tmpb, "آ", ar[i].substring(2));
						} else if(ar[i].startsWith("A6")) {					// By Keshavarz
							append(sb, tmpb, "ئ", ar[i].substring(2));
						} else if(ar[i].startsWith("A9")) {					// By Keshavarz
							append(sb, tmpb, "ة", ar[i].substring(2));
						} else if (ar[i].startsWith("B6")) {
							append(sb, tmpb, "ض", ar[i].substring(2));
						} else if (ar[i].startsWith("B5")) {
							append(sb, tmpb, "ص", ar[i].substring(2));
						} else if (ar[i].startsWith("AB")) {
							append(sb, tmpb, "ث", ar[i].substring(2));
						} else if (ar[i].startsWith("BA")) {
							append(sb, tmpb, "غ", ar[i].substring(2));
						} else if (ar[i].startsWith("B9")) {
							append(sb, tmpb, "ع", ar[i].substring(2));
						} else if (ar[i].startsWith("AE")) {
							append(sb, tmpb, "خ", ar[i].substring(2));
						} else if (ar[i].startsWith("AD")) {
							append(sb, tmpb, "ح", ar[i].substring(2));
						} else if (ar[i].startsWith("AC")) {
							append(sb, tmpb, "ج", ar[i].substring(2));
						} else if (ar[i].startsWith("B4")) {
							append(sb, tmpb, "ش", ar[i].substring(2));
						} else if (ar[i].startsWith("B3")) {
							append(sb, tmpb, "س", ar[i].substring(2));
						} else if (ar[i].startsWith("A8")) {
							append(sb, tmpb, "ب", ar[i].substring(2));
						} else if (ar[i].startsWith("A7")) {
							append(sb, tmpb, "ا", ar[i].substring(2));
						} else if (ar[i].startsWith("AA")) {
							append(sb, tmpb, "ت", ar[i].substring(2));
						} else if (ar[i].startsWith("B8")) {
							append(sb, tmpb, "ظ", ar[i].substring(2));
						} else if (ar[i].startsWith("B7")) {
							append(sb, tmpb, "ط", ar[i].substring(2));
						} else if (ar[i].startsWith("B2")) {
							append(sb, tmpb, "ز", ar[i].substring(2));
						} else if (ar[i].startsWith("B1")) {
							append(sb, tmpb, "ر", ar[i].substring(2));
						} else if (ar[i].startsWith("B0")) {
							append(sb, tmpb, "ذ", ar[i].substring(2));
						} else if (ar[i].startsWith("AF")) {
							append(sb, tmpb, "د", ar[i].substring(2));
						} else if (ar[i].startsWith("A1")) {
							append(sb, tmpb, "ء", ar[i].substring(2));
						} else {
							tmpb.append((i > 0 ? "%" : "") + ar[i]);
						}
					} else {
						tmpb.append((i > 0 ? "%" : "") + ar[i]);
					}
				} else if ("DB".equalsIgnoreCase(ar[i])) {
					// %D8%
					// %D9%
					if (i + 1 < ar.length) {
						i += 1;
						if (ar[i].startsWith("8C")) {
							append(sb, tmpb, "ی", ar[i].substring(2));
						} else if (ar[i].startsWith("B0")) {
							append(sb, tmpb, "0", ar[i].substring(2));
						} else if (ar[i].startsWith("B1")) {
							append(sb, tmpb, "1", ar[i].substring(2));
						} else if (ar[i].startsWith("B2")) {
							append(sb, tmpb, "2", ar[i].substring(2));
						} else if (ar[i].startsWith("B3")) {
							append(sb, tmpb, "3", ar[i].substring(2));
						} else if (ar[i].startsWith("B4")) {
							append(sb, tmpb, "4", ar[i].substring(2));
						} else if (ar[i].startsWith("B5")) {
							append(sb, tmpb, "5", ar[i].substring(2));
						} else if (ar[i].startsWith("B6")) {
							append(sb, tmpb, "6", ar[i].substring(2));
						} else if (ar[i].startsWith("B7")) {
							append(sb, tmpb, "7", ar[i].substring(2));
						} else if (ar[i].startsWith("B8")) {
							append(sb, tmpb, "8", ar[i].substring(2));
						} else if (ar[i].startsWith("B9")) {
							append(sb, tmpb, "9", ar[i].substring(2));
						} else {
							tmpb.append((i > 0 ? "%" : "") + ar[i]);
						}
					} else {
						tmpb.append((i > 0 ? "%" : "") + ar[i]);
					}
				} else {
					tmpb.append((i > 0 ? "%" : "") + ar[i]);
				}
			}
			append(sb, tmpb, "", "");
			text = sb.toString(); // new
			// String(sb.toString().getBytes(),"utf8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
//		System.out.println(text);

		return text;

		// theses are only for linux
		// ض %D8%B6
		// ص %D8%B5
		// ث %D8%AB
		// غ %D8%BA
		// ع %D8%B9
		// خ %D8%AE
		// %D8%AD ح
		// %D8%AC ج
		// %D8%B4 ش
		// %D8%B3 س
		// %D8%A8 ب
		// %D8%A7 ا
		// %D8%AA ت
		// %D8%B8 ظ
		// %D8%B7 ط
		// %D8%B2 ز
		// %D8%B1 ر
		// %D8%B0 ذ
		// %D8%AF د
		// %D9%BE پ

	}

	public static boolean createDirectory(String path) {
		File file = new File(path);
		if (!file.exists() || !file.isDirectory()) {
			file.mkdirs();
		}
		return true;
	}

	public static void makeObjUTF8(Object obj) {
		Class cls = obj.getClass();
		ArrayList<Field> fieldsArray = new ArrayList<Field>();
		Field[] fields;
		while (cls != null) {
			fields = cls.getDeclaredFields();
			for(int i=0; i< fields.length; i++) {
				fieldsArray.add(fields[i]);
			}
			cls = cls.getSuperclass();
		}
		for(int i=0; i< fieldsArray.size(); i++) {
			String fieldName = fieldsArray.get(i).getName().substring(0,1).toUpperCase() + fieldsArray.get(i).getName().substring(1);
			Object value = null;
			try {
				value = AIPUtil.invokeGetter(obj, fieldName);
			} catch (Exception e) {
				//e.printStackTrace();
			}
			try {
				if(value != null && value instanceof String) {
					value = cnvUTF8(value.toString());
					AIPUtil.invokeSetter(obj, fieldName, value.toString());
				}else if(!(value instanceof String) && value instanceof Object) {
					makeObjUTF8(value);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}

}
