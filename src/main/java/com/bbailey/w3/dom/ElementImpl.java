package com.bbailey.w3.dom;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.TypeInfo;
import org.w3c.dom.UserDataHandler;

public class ElementImpl extends NodeImpl implements Element {

	private final String tagName;
	private final NamedNodeMap attributes = new NamedNodeMapImpl();
	
	public ElementImpl(String namespaceURI, String qualifiedName) {
		super(namespaceURI, qualifiedName);
		this.tagName = getLocalName();
	}
	
	public ElementImpl(String tagName) {
		super(tagName);
		this.tagName = tagName;
	}
	
	@Override
	public Node cloneNode(boolean deep) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NamedNodeMap getAttributes() {
		return attributes;
	}

	@Override
	public String getNodeName() {
		return tagName;
	}

	@Override
	public short getNodeType() {
		return Node.ELEMENT_NODE;
	}

	@Override
	public String getNodeValue() throws DOMException {
		return null;
	}

	@Override
	public void setNodeValue(String nodeValue) throws DOMException {
		// No affect
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
	public String getAttribute(String name) {
		
		String value = "";
		Node attr = attributes.getNamedItem(name);
		if (attr != null) {
			value = attr.getNodeValue();
		}
		
		return value;
	}

	@Override
	public String getAttributeNS(String namespaceURI, String localName) throws DOMException {
		
		String value = "";
		Node attr = attributes.getNamedItemNS(namespaceURI, localName);
		if (attr != null) {
			value = attr.getNodeValue();
		}
		
		return value;
	}

	@Override
	public Attr getAttributeNode(String nodeName) {
		return (Attr)attributes.getNamedItem(nodeName);
	}

	@Override
	public Attr getAttributeNodeNS(String namespaceURI, String localName) throws DOMException {
		return (Attr)attributes.getNamedItemNS(namespaceURI, localName);
	}

	@Override
	public NodeList getElementsByTagName(String name) {
		return new NodeListImpl(getElementsByTagName(this, name));
	}
	
	
	private List<Node> getElementsByTagName(Node root, String name) {
		
		List<Node> matchedElements = new ArrayList<>();
		NodeList children = root.getChildNodes();
		for (int i = 0 ; i < children.getLength() ; i++) {
			Node child = children.item(i);
			if (child instanceof Element) {
				Element childElement = (Element)child;
				if ("*".equals(name) || childElement.getTagName().equals(name)) {
					matchedElements.add(childElement);
					matchedElements.addAll(getElementsByTagName(child, name));
				}
			}
		}
		
		return matchedElements;
	}

	@Override
	public NodeList getElementsByTagNameNS(String namespaceURI, String localName) throws DOMException {
		// TODO: Handle namespaces
		return new NodeListImpl(getElementsByTagName(this, localName));
	}

	@Override
	public TypeInfo getSchemaTypeInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTagName() {
		return tagName;
	}

	@Override
	public boolean hasAttribute(String name) {
		return getAttributeNode(name) != null;
	}

	@Override
	public boolean hasAttributeNS(String namespaceURI, String localName) throws DOMException {
		return getAttributeNodeNS(namespaceURI, localName) != null;
	}

	@Override
	public void removeAttribute(String name) throws DOMException {
		attributes.removeNamedItem(name);
	}

	@Override
	public void removeAttributeNS(String namespaceURI, String localName) throws DOMException {
		attributes.removeNamedItemNS(namespaceURI, localName);
	}

	@Override
	public Attr removeAttributeNode(Attr oldAttr) throws DOMException {
		Node node = attributes.removeNamedItem(oldAttr.getName());
		if (node == null) {
			throw new DOMException(DOMException.NOT_FOUND_ERR, "oldAttr is not a an attribute of this element");
		} else {
			return (Attr)node;
		}
	}

	@Override
	public void setAttribute(String name, String value) throws DOMException {
		
		Attr existingAttr = getAttributeNode(name);
		if (existingAttr != null) {
			existingAttr.setValue(value);
		} else {
			attributes.setNamedItem(new AttrImpl(name, value));
		}
	}

	@Override
	public void setAttributeNS(String namespaceURI, String qualifiedName, String value) throws DOMException {
		Attr existingAttr = getAttributeNodeNS(namespaceURI, qualifiedName);
		if (existingAttr != null) {
			existingAttr.setValue(value);
		} else {
			// TODO: Handle namespace names correctly
			attributes.setNamedItem(new AttrImpl(qualifiedName, value));
		}
	}

	@Override
	public Attr setAttributeNode(Attr newAttr) throws DOMException {
		return (Attr)attributes.setNamedItem(newAttr);
	}

	@Override
	public Attr setAttributeNodeNS(Attr newAttr) throws DOMException {
		return (Attr)attributes.setNamedItemNS(newAttr);
	}

	@Override
	public void setIdAttribute(String name, boolean isId) throws DOMException {
		
		AttrImpl attr = (AttrImpl)getAttributeNode(name);
		if (attr == null) {
			throw new DOMException(DOMException.NOT_FOUND_ERR, "name does not exist as an attribute of this element");
		} else {
			attr.setUserDefinedId(isId);
		}
	}

	@Override
	public void setIdAttributeNS(String namespaceURI, String localName, boolean isId) throws DOMException {
		AttrImpl attr = (AttrImpl)getAttributeNodeNS(namespaceURI, localName);
		if (attr == null) {
			throw new DOMException(DOMException.NOT_FOUND_ERR, "name does not exist as an attribute of this element");
		} else {
			attr.setUserDefinedId(isId);
		}
	}

	@Override
	public void setIdAttributeNode(Attr idAttr, boolean isId) throws DOMException {
		setIdAttribute(idAttr.getName(), isId);
		
	}

	@Override
	public boolean hasAttributes() {
		return true;
	}
	
	@Override
	public String toString() {
		String s = "Element: " + getTagName();
		if (attributes.getLength() > 0) {
			s += " " + attributes;
		}
		
		return s;
	}
}
