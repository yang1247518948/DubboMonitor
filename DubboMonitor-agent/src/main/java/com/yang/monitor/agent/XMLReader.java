package com.yang.monitor.agent;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yangping
 * @date 2017.11.27 11:08
 **/
public class XMLReader {
	private static AppInfo info = AppInfo.getInstance();

	/**
	 * 读取XML文件
	 */
	static void readFile(){
		File file = new File(System.getProperty("agent.dir")+"config.xml");
		if(file.exists()){
			readXMLFile(file);
		}
	}

	/**
	 * 读取文件，加入黑名单和白名单
	 * @param file xml文件
	 */
	private static void readXMLFile(File file) {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);

			NodeList classNames = doc.getElementsByTagName("Ignore");
			NodeList allList = doc.getElementsByTagName("Add");

			for(int i=0;i<classNames.getLength();i++){
				info.addBlackListItem(classNames.item(i).getFirstChild().getNodeValue()+".*");
			}

			for(int i=0;i<allList.getLength();i++){
				info.addWhiteListItem(allList.item(i).getFirstChild().getNodeValue()+".*");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Deprecated
	public static List<String> readCodisFile() {
		File file = new File(System.getProperty("agent.dir")+"config.xml");
		if(file.exists()){
			readXMLFile(file);
		}

		List<String> result = new ArrayList<>();
		try {

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);

			NodeList addressNode = doc.getElementsByTagName("CodisAddress");
			NodeList pathNode = doc.getElementsByTagName("CodisPath");
			NodeList sessionTimeoutNode = doc.getElementsByTagName("CodisTimeout");

			if(addressNode!=null&&pathNode!=null&&sessionTimeoutNode!=null) {
				String address = addressNode.item(0).getFirstChild().getNodeValue();
				String path = pathNode.item(0).getFirstChild().getNodeValue();
				String sessionTimeout = sessionTimeoutNode.item(0).getFirstChild().getNodeValue();
				result.add(address);
				result.add(sessionTimeout);
				result.add(path);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * read Netty IP from config file
	 * @return NettyIP
	 */
	public static String readNettyIP(){
		File file = new File(System.getProperty("agent.dir")+"config.xml");
		if(file.exists()){
			readXMLFile(file);
		}

		String result = null;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);

			NodeList IPNode = doc.getElementsByTagName("NettyIP");

			if(IPNode!=null) {
				result = IPNode.item(0).getFirstChild().getNodeValue();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * read Netty port form config file
	 * @return Netty port
	 */
	public static String readNettyPort(){
		File file = new File(System.getProperty("agent.dir")+"config.xml");
		if(file.exists()){
			readXMLFile(file);
		}

		String result = null;
		try {

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);

			NodeList portNode = doc.getElementsByTagName("NettyPort");

			if(portNode!=null) {
				result = portNode.item(0).getFirstChild().getNodeValue();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
