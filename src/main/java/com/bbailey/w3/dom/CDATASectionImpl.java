package com.bbailey.w3.dom;

import org.w3c.dom.CDATASection;
import org.w3c.dom.DOMException;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import org.w3c.dom.UserDataHandler;

public class CDATASectionImpl extends CharacterDataImpl implements CDATASection {

	private final static String CDATA_SECTION_NODE_NAME = "#cdata-section";
	
	public CDATASectionImpl(String data) {
		super("");
	}

	@Override
	public String getWholeText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isElementContentWhitespace() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Text replaceWholeText(String arg0) throws DOMException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Text splitText(int arg0) throws DOMException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Node cloneNode(boolean deep) {
		return new CDATASectionImpl(getData());
	}

	@Override
	public NamedNodeMap getAttributes() {
		return null;
	}

	@Override
	public String getNodeName() {
		return CDATA_SECTION_NODE_NAME;
	}

	@Override
	public short getNodeType() {
		return CDATA_SECTION_NODE;
	}

	@Override
	public String getNodeValue() throws DOMException {
		return getData();
	}

	@Override
	public boolean hasAttributes() {
		return false;
	}

	@Override
	public void setNodeValue(String nodeValue) throws DOMException {
		setData(nodeValue);
	}

	@Override
	public Object setUserData(String key, Object data, UserDataHandler handler) {
		return null;
	}

	@Override
	public String toString() {
		return "CDATA: " + getData();
	}
}
