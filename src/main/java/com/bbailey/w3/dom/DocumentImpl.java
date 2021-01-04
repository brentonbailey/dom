package com.bbailey.w3.dom;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.EntityReference;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;
import org.w3c.dom.UserDataHandler;

public class DocumentImpl extends NodeImpl implements Document {

	private static final String DOCUMENT_NAME = "#document";

	private String documentUri;
	private boolean strictErrorChecking = true;
	
	public DocumentImpl() {
		super();
	}
	
	@Override
	public Node cloneNode(boolean deep) {
		
		DocumentImpl clone = new DocumentImpl();
		clone.setDocumentURI(getDocumentURI());
		clone.setStrictErrorChecking(getStrictErrorChecking());
		
		if (deep) {
			NodeList childNodes = getChildNodes();
			for (int i = 0 ; i < childNodes.getLength() ; i++) {
				Node childNode = childNodes.item(i);
				clone.appendChild(childNode.cloneNode(deep));
			}
		}
		
		return clone;
	}

	@Override
	public NamedNodeMap getAttributes() {
		return null;
	}

	@Override
	public String getNodeName() {
		return DOCUMENT_NAME;
	}

	@Override
	public short getNodeType() {
		return Node.DOCUMENT_NODE;
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
	public Object setUserData(String key, Object data, UserDataHandler handler) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Node adoptNode(Node arg0) throws DOMException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Attr createAttribute(String name) throws DOMException {
		return new AttrImpl(name);
	}

	@Override
	public Attr createAttributeNS(String namespaceURI, String qualifiedName) throws DOMException {
		return new AttrImpl(namespaceURI, qualifiedName, "");
		
	}

	@Override
	public CDATASection createCDATASection(String data) throws DOMException {
		return new CDATASectionImpl(data);
	}

	@Override
	public Comment createComment(String data) {
		return new CommentImpl(data);
	}

	@Override
	public DocumentFragment createDocumentFragment() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Element createElement(String tagName) throws DOMException {
		return new ElementImpl(tagName);
	}

	@Override
	public Element createElementNS(String namespaceURI, String qualifiedName) throws DOMException {
		return new ElementImpl(namespaceURI, qualifiedName);
	}

	@Override
	public EntityReference createEntityReference(String name) throws DOMException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProcessingInstruction createProcessingInstruction(String target, String data) throws DOMException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Text createTextNode(String data) {
		return new TextImpl(data);
	}

	@Override
	public DocumentType getDoctype() {
		
		NodeList childNodes = getChildNodes();
		for (int i = 0 ; i < childNodes.getLength() ; i++) {
			Node child = childNodes.item(i);
			if (child.getNodeType() == DOCUMENT_TYPE_NODE) {
				return (DocumentType)child;
			}
		}
		
		return null;
	}

	@Override
	public Element getDocumentElement() {
		
		NodeList childNodes = getChildNodes();
		for (int i = 0 ; i < childNodes.getLength() ; i++) {
			Node child = childNodes.item(i);
			if (child.getNodeType() == ELEMENT_NODE) {
				return (Element)child;
			}
		}
		
		return null;
	}

	@Override
	public String getDocumentURI() {
		return documentUri;
	}

	@Override
	public DOMConfiguration getDomConfig() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Element getElementById(String elementId) {
		return getElementById(getDocumentElement(), elementId);
	}
	
	
	private Element getElementById(Element root, String elementId) {
		
		NamedNodeMap attributes = root.getAttributes();
		for (int i = 0 ; i < attributes.getLength() ; i++) {
			Attr attr = (Attr)attributes.item(i);
			if (attr.isId()) {
				if (StringUtils.equals(elementId, attr.getValue())) {
					return root;
				}
			}
		}
		
		NodeList childNodes = root.getChildNodes();
		for (int i = 0 ; i < childNodes.getLength() ; i++) {
			Node childNode = childNodes.item(i);
			if (childNode.getNodeType() == ELEMENT_NODE) {
				
				Element ret = getElementById((Element)childNode, elementId);
				if (ret != null) {
					return ret;
				}
			}
		}
		
		return null;
	}
	

	@Override
	public NodeList getElementsByTagName(String tagName) {
		return getDocumentElement().getElementsByTagName(tagName);
	}

	@Override
	public NodeList getElementsByTagNameNS(String namespaceURI, String localName) {
		return getDocumentElement().getElementsByTagNameNS(namespaceURI, localName);
	}

	@Override
	public DOMImplementation getImplementation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getInputEncoding() {
		return "utf-8";
	}

	@Override
	public boolean getStrictErrorChecking() {
		return strictErrorChecking;
	}

	@Override
	public String getXmlEncoding() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getXmlStandalone() {
		return false;
	}

	@Override
	public String getXmlVersion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Node importNode(Node importNode, boolean deep) throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "importNode is not supported");
	}

	@Override
	public void normalizeDocument() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Node renameNode(Node n, String namespaceURI, String qualifiedName) throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "renameNode is not supported");
	}

	@Override
	public void setDocumentURI(String documentUri) {
		this.documentUri = documentUri;
	}

	@Override
	public void setStrictErrorChecking(boolean strictErrorChecking) {
		this.strictErrorChecking = strictErrorChecking;
	}

	@Override
	public void setXmlStandalone(boolean xmlStandalone) throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "XML feature is not supported");
	}

	@Override
	public void setXmlVersion(String arg0) throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "XML feature is not supported");
	}

	@Override
	public boolean hasAttributes() {
		return false;
	}

	@Override
	public String toString() {
		return "Document";
	}
}
