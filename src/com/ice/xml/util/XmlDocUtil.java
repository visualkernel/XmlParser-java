package com.ice.xml.util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.SAXReader;
import org.xml.sax.SAXException;
import org.xml.sax.XMLFilter;
import org.xml.sax.helpers.XMLFilterImpl;

public class XmlDocUtil {

	/**
	 * 将url所指向的XML解析成Document对象
	 * 
	 * @param url
	 * @return
	 * @throws DocumentException
	 * @throws MalformedURLException
	 */
	public static Document parseUrlToDoc(String url)
			throws MalformedURLException, DocumentException {
		Document doc = null;

		if (url == null)
			return null;

		SAXReader xmlReader = createSAXReaderWithCharFilter();

		doc = xmlReader.read(new URL(url));

		return doc;
	}

	/**
	 * 将xml文件解析成Document对象
	 * 
	 * @param file
	 * @return
	 * @throws DocumentException
	 */
	public static Document parseXmlFileToDoc(File file)
			throws DocumentException {
		Document doc = null;

		if (file == null)
			return null;

		SAXReader xmlReader = createSAXReaderWithCharFilter();

		doc = xmlReader.read(file);

		return doc;
	}

	/**
	 * 将文本格式的XML解析成Document对象
	 * 
	 * @param text
	 * @return
	 * @throws DocumentException
	 */
	public static Document parseXmlTextToDoc(String text)
			throws DocumentException {
		Document doc = null;

		if (text == null)
			return null;

		text = removeInvalidChars(text);
		doc = DocumentHelper.parseText(text);

		return doc;
	}

	/**
	 * 判断字符ch是否为无效的XML字符
	 * 
	 * @param ch
	 * @return
	 */
	public static boolean isInvalidChar(int ch) {
		return (ch >= 0x00 && ch <= 0x08) || (ch >= 0x0b && ch <= 0x0c)
				|| (ch >= 0x0e && ch <= 0x1f);
	}

	/**
	 * 删除字符串xmlstr中的无效字符
	 * 
	 * @param str
	 * @return
	 */
	public static String removeInvalidChars(String xmlstr) {
		if (xmlstr == null)
			return null;
		return xmlstr.replaceAll("[\\x00-\\x08\\x0b-\\x0c\\x0e-\\x1f]", "");
	}

	/**
	 * 创建一个带无效字符过滤的SAXReader对象
	 * 
	 * @return
	 */
	public static SAXReader createSAXReaderWithCharFilter() {
		SAXReader xmlReader = new SAXReader();
		/* 定义字符串过滤，处理无效字符 */
		XMLFilter filter = new XMLFilterImpl() {
			@Override
			public void characters(char[] ch, int start, int length)
					throws SAXException {
				for (int i = 0; i < ch.length; i++) {
					if (isInvalidChar(ch[i])) {// 将非法字符置换成空
						ch[i] = ' ';
					}
				}
				super.characters(ch, start, length);
			}
		};
		xmlReader.setXMLFilter(filter);

		return xmlReader;
	}

}
