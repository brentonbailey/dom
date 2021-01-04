package com.bbailey.w3.dom;

import org.w3c.dom.Comment;
import org.w3c.dom.DOMException;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.UserDataHandler;

public class CommentImpl extends CharacterDataImpl implements Comment {

	private final static String COMMENT_NODE_NAME = "#comment";
	
	
	public CommentImpl(String text) {
		super();
		setData(text);
	}

	@Override
	public Node cloneNode(boolean deep) {
		return new CommentImpl(getData());
	}

	@Override
	public NamedNodeMap getAttributes() {
		return null;
	}

	@Override
	public String getNodeName() {
		return COMMENT_NODE_NAME;
	}

	@Override
	public short getNodeType() {
		return COMMENT_NODE;
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
	public String toString() {
		return "Comment: " + getData();
	}
}
