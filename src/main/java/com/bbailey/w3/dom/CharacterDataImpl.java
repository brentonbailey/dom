package com.bbailey.w3.dom;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.CharacterData;
import org.w3c.dom.DOMException;

public abstract class CharacterDataImpl extends NodeImpl implements CharacterData {

	private String text = "";

	public CharacterDataImpl(String namespaceURI, String qualifiedName) {
		super(namespaceURI, qualifiedName);
	}

	public CharacterDataImpl(String localName) {
		super(localName);
	}
	
	public CharacterDataImpl() {
		super();
	}

	@Override
	public void appendData(String arg) throws DOMException {
		text += arg;
	}

	@Override
	public void deleteData(int offset, int count) throws DOMException {
		
		if (offset < 0 || offset > text.length()) {
			throw new DOMException(DOMException.INDEX_SIZE_ERR, "Invalid offset");
		}
		
		String left = StringUtils.substring(text, 0, offset);
		String right = StringUtils.substring(text, offset + count);
		text = left + right;
	}

	@Override
	public String getData() throws DOMException {
		return text;
	}

	@Override
	public int getLength() {
		return text.length();
	}

	@Override
	public void insertData(int offset, String arg) throws DOMException {
		
		if (offset < 0 || offset > text.length()) {
			throw new DOMException(DOMException.INDEX_SIZE_ERR, "Invalid offset");
		}
		
		String left = StringUtils.substring(text,  0, offset);
		String right = StringUtils.substring(text, offset);
		text = left + arg + right;
	}

	@Override
	public void replaceData(int offset, int count, String arg) throws DOMException {
		
		if (offset < 0 || offset > text.length()) {
			throw new DOMException(DOMException.INDEX_SIZE_ERR, "Invalid offset");
		}
		
		String left = StringUtils.substring(text, 0, offset);
		String right = StringUtils.substring(text, offset + count);
		text = left + arg + right;
	}

	@Override
	public void setData(String data) throws DOMException {
		this.text = data;
		
	}

	@Override
	public String substringData(int offset, int count) throws DOMException {
		
		if (offset < 0 || offset > text.length()) {
			throw new DOMException(DOMException.INDEX_SIZE_ERR, "Invalid offset");
		}
		
		return StringUtils.substring(text,  offset, offset + count);
	}

}