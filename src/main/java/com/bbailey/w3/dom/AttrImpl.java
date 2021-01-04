package com.bbailey.w3.dom;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.TypeInfo;
import org.w3c.dom.UserDataHandler;

public class AttrImpl extends NodeImpl implements Attr {

	private final String name;
	private String value = "";
	private boolean specified = false;
	private boolean isUserDefinedId = false;
	
	
	public AttrImpl(String name) {
		super(name);
		this.name = name;
	}
	
	public AttrImpl(String name, String value) {
		this(name);
		setValue(value);
	}
	
	public AttrImpl(String namespaceURI, String qualifiedName, String value) {
		super(namespaceURI, qualifiedName);
		this.name = getLocalName();
		setValue(value);
	}
	
	@Override
	public Node cloneNode(boolean deep) {
		AttrImpl clone = new AttrImpl(name);
		clone.setValue(value);
		return clone;
	}

	@Override
	public NamedNodeMap getAttributes() {
		return null;
	}

	@Override
	public String getNodeName() {
		return name;
	}

	@Override
	public short getNodeType() {
		return Node.ATTRIBUTE_NODE;
	}

	@Override
	public String getNodeValue() throws DOMException {
		return value;
	}

	@Override
	public void setNodeValue(String nodeValue) throws DOMException {
		value = nodeValue;
	}

	@Override
	public void setTextContent(String textContent) throws DOMException {
		// No affect
	}

	@Override
	public Object setUserData(String key, Object data, UserDataHandler handler) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Element getOwnerElement() {
		
		Element ownerElement = null;
		
		/*
		 * Note the getParent method for a Attr should return null
		 * so we have to peep at the member variable for the parent instead
		 */
		if (parent != null) {
			ownerElement = (Element)parent;
		}
		
		return ownerElement;
	}

	@Override
	public TypeInfo getSchemaTypeInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getSpecified() {
		return specified;
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public boolean isId() {
		return "id".equals(name) || isUserDefinedId;
	}
	
	void setUserDefinedId(boolean isUserDefinedId) {
		this.isUserDefinedId = isUserDefinedId;
	}

	@Override
	public void setValue(String value) throws DOMException {
		specified = true;
		this.value = value;
		
	}
	
	
	@Override
	public Node getParentNode() {
		return null;
	}
	
	@Override
	public Node getNextSibling() {
		return null;
	}
	
	@Override
	public Node getPreviousSibling() {
		return null;
	}


	@Override
	public boolean hasAttributes() {
		return false;
	}

	@Override
	public String toString() {
		String s = name;
		if (!StringUtils.isEmpty(value)) {
			s += "=\"" + value + "\"";
		}
		
		return s;
	}
}
