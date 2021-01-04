package com.bbailey.w3.dom;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.DOMException;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import org.w3c.dom.UserDataHandler;

public class TextImpl extends CharacterDataImpl implements Text {

	private final static String TEXT_NODE_NAME = "#text";
	
	public TextImpl(String data) {
		super();
		setData(data);
	}

	@Override
	public Node cloneNode(boolean deep) {
		return new TextImpl(getData());
	}

	@Override
	public NamedNodeMap getAttributes() {
		return null;
	}

	@Override
	public String getNodeName() {
		return TEXT_NODE_NAME;
	}

	@Override
	public short getNodeType() {
		return TEXT_NODE;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getWholeText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isElementContentWhitespace() {
		return StringUtils.isWhitespace(getData());
	}

	@Override
	public Text replaceWholeText(String content) throws DOMException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Text splitText(int offset) throws DOMException {
		
		String text = getData();
		if (offset < 0 || offset > text.length()) {
			throw new DOMException(DOMException.INDEX_SIZE_ERR, "Invalid offset");
		}
		
		String left = StringUtils.substring(text, 0, offset);
		String right = StringUtils.substring(text, offset);
		
		setData(left);
		
		TextImpl other = new TextImpl(getLocalName());
		other.setData(right);
		
		if (getParentNode() != null) {
			Node next = getNextSibling();
			if (next == null) {
				getParentNode().appendChild(other);
			} else {
				getParentNode().insertBefore(other, next);
			}
		}
		return other;
	}

	@Override
	public String toString() {
		return "Text: " + getData();
	}
}
