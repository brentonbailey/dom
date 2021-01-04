package com.bbailey.w3.dom;

import org.w3c.dom.DOMException;
import org.w3c.dom.DocumentType;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.UserDataHandler;

public class DocumentTypeImpl extends NodeImpl implements DocumentType {

	private final String name;
	private final String publicId;
	private final String systemId;
	
	public DocumentTypeImpl(String name, String publicId, String systemId) {
		super("");
		this.name = name;
		this.publicId = publicId;
		this.systemId = systemId;
	}

	@Override
	public Node cloneNode(boolean deep) {
		return new DocumentTypeImpl(name, publicId, systemId);
	}

	@Override
	public NamedNodeMap getAttributes() {
		return null;
	}

	@Override
	public String getNodeName() {
		return getName();
	}

	@Override
	public short getNodeType() {
		return DOCUMENT_TYPE_NODE;
	}

	@Override
	public String getNodeValue() throws DOMException {
		return null;
	}

	@Override
	public boolean hasAttributes() {
		return false;
	}

	@Override
	public void setNodeValue(String nodeValue) throws DOMException {
		// No affect
	}

	@Override
	public Object setUserData(String arg0, Object arg1, UserDataHandler arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NamedNodeMap getEntities() {
		return new NamedNodeMapImpl();
	}

	@Override
	public String getInternalSubset() {
		return null;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public NamedNodeMap getNotations() {
		return new NamedNodeMapImpl();
	}

	@Override
	public String getPublicId() {
		return publicId;
	}

	@Override
	public String getSystemId() {
		return systemId;
	}

	@Override
	public String toString() {
		return "DocumentType";
	}
}
