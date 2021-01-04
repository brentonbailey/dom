package com.bbailey.w3.dom;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.w3c.dom.Attr;
import org.w3c.dom.Comment;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;


public abstract class NodeImpl implements Node {

	protected NodeImpl parent;
	protected List<Node> children = new ArrayList<>();
	
	private final static Pattern QNAME_PATTERN = Pattern.compile("((?<prefix>[A-Za-z_0-9]+):)?(?<localName>[A-Za-z_0-9]+)$");
	private final static Pattern LNAME_PATTERN = Pattern.compile("[A-Za-z_0-9]+");
	
	public final static String DEFAULT_NAMESPACE = "xmlns";
	
	public final static String CORE_FEATURE = "Core";
	public final static String XML_FEATURE = "XML";
	public final static String EVENTS_FEATURE = "Events";
	public final static String UI_EVENTS_FEATURE = "UIEvents";
	public final static String MOUSE_EVENTS_FEATURE = "MouseEvents";
	public final static String TEXT_EVENTS_FEATURE = "TextEvents";
	public final static String KEYBOARD_EVENTS_FEATURE = "KeyboardEvents";
	public final static String MUTATION_EVENTS_FEATURE = "MutationEvents";
	public final static String MUTATION_NAME_EVENTS_FEATURE = "MutationNameEvents";
	public final static String HTML_EVENTS_FEATURE = "HTMLEvents";
	public final static String LOAD_AND_SAVE_FEATURE = "LS";
	public final static String ASYNCHRONOUS_LOAD_FEATURE = "LS-Async";
	public final static String VALIDATION_FEATURE = "Validation";
	public final static String XPATH_FEATURE = "XPath";
	
	private final String localName;
	private final String namespaceURI;
	private String prefix;
	
	
	/**
	 * @param namespaceURI The document namespace
	 * @param qualifiedName The qualified name (prefix:)?localName
	 */
	public NodeImpl(String namespaceURI, String qualifiedName) {

		this.namespaceURI = namespaceURI;
		
		Matcher matcher = QNAME_PATTERN.matcher(qualifiedName);
		if (!matcher.matches()) {
			throw new DOMException(DOMException.INVALID_CHARACTER_ERR, "Illegal qualifiedName for node");
		}
		
		prefix = matcher.group("prefix");
		localName = matcher.group("localName");
	}
	
	public NodeImpl(String localName) {
		this.prefix = null;
		this.namespaceURI = null;
		this.localName = localName;
		
		if (!StringUtils.isEmpty(localName)) {
			Matcher matcher = LNAME_PATTERN.matcher(localName);
			if (!matcher.matches()) {
				throw new DOMException(DOMException.INVALID_CHARACTER_ERR, "Illegal localName for node");
			}
		}
	}
	
	public NodeImpl() {
		this.prefix = null;
		this.namespaceURI = null;
		this.localName = null;
	}
	
	@Override
	public Node appendChild(Node node) throws DOMException {
		// TODO - If already in the tree - remove it first
		checkPreInsertionValidity(node, this, null);
		this.children.add(setChildParent(node));
		return node;
	}

	

	@Override
	public short compareDocumentPosition(Node arg0) throws DOMException {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public String getBaseURI() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NodeList getChildNodes() {
		return new NodeListImpl(children);
	}

	
	@Override
	public Object getFeature(String feature, String version) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Node getFirstChild() {
		return getChildNodes().item(0);
	}

	@Override
	public Node getLastChild() {
		NodeList childNodes = getChildNodes();
		return childNodes.item(childNodes.getLength() - 1);
	}


	@Override
	public String getNamespaceURI() {
		return namespaceURI;
	}
	
	@Override
	public String getLocalName() {
		return localName;
	}
	
	
	/**
	 * The node immediately following this node. If there is no such node, null
	 */
	@Override
	public Node getNextSibling() {
		
		if (parent != null) {
			NodeList siblings = parent.getChildNodes();
			for (int i = 0 ; i < siblings.getLength() ; i++) {
				if (siblings.item(i) == this) {
					return siblings.item(i + 1);
				}
			}
		}
		
		return null;
	}
	

	@Override
	public Document getOwnerDocument() {
		
		if (parent != null) {
			if (parent instanceof Document) {
				return (Document)parent;
			} else {
				return parent.getOwnerDocument();
			}
		} else {
			return null;
		}
	}

	@Override
	public Node getParentNode() {
		return parent;
	}
	
	void setParentNode(NodeImpl parent) {
		this.parent = parent;
	}

	@Override
	public String getPrefix() {
		return prefix;
	}

	/**
	 * The node immediately preceding this node. If there is no such node, this returns null
	 */
	@Override
	public Node getPreviousSibling() {
		
		if (parent != null) {
			NodeList siblings = parent.getChildNodes();
			for (int i = 0 ; i < siblings.getLength() ; i++) {
				if (siblings.item(i) == this) {
					return siblings.item(i - 1);
				}
			}
		}
		
		return null;
		
	}

	@Override
	public Object getUserData(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean hasChildNodes() {
		return !children.isEmpty();
	}

	
	/**
	 * Inserts the node newChild before the existing child node refChild.
	 * If refChild is null, insert newChild at the end of the list of children.
	 * 
	 * @param newChild The node to insert
	 * @param refChild The reference node. i.e the node before whilch the new node must be inserted
	 * @return The node being inserted
	 */
	@Override
	public Node insertBefore(Node newChild, Node refChild) throws DOMException {
		
		checkPreInsertionValidity(refChild, this, newChild);
		
		if (refChild != null && !children.contains(refChild)) {
			throw new DOMException(DOMException.NOT_FOUND_ERR, "refChild is not a child of this node");
		}
		
		// TODO: if newChild is already in the tree - remove it.
		
		int index = children.indexOf(refChild);
		if (index == -1) {
			// Append to the end
			children.add(setChildParent(newChild));
		} else {
			children.add(index, setChildParent(newChild));
		}
		
		return newChild;
	}

	@Override
	public boolean isDefaultNamespace(String namespaceURI) {
		return false;
	}

	@Override
	public boolean isEqualNode(Node arg) {

		EqualsBuilder builder = new EqualsBuilder();
		builder
			.append(this.getNodeType(), arg.getNodeType())
			.append(this.getNodeName(), arg.getNodeName())
			.append(this.getNamespaceURI(), arg.getNamespaceURI())
			.append(this.getLocalName(), arg.getLocalName())
			.append(this.getPrefix(), arg.getPrefix())
			.append(this.getNodeValue(), arg.getNodeValue())
			.append(this.getAttributes(), arg.getAttributes())
			.append(this.getChildNodes(), arg.getChildNodes());
		
		return builder.isEquals();
	}

	@Override
	public boolean isSameNode(Node other) {
		return this == other;
	}

	@Override
	public boolean isSupported(String feature, String version) {
		
		switch (feature) {
		case CORE_FEATURE:
			return true;
		case XML_FEATURE:
			return false;
		default:
			return false;
		}
	}

	@Override
	public String lookupNamespaceURI(String prefix) {
		
		switch (getNodeType()) {
		case ELEMENT_NODE:
			if (getNamespaceURI() != null && StringUtils.equals(getPrefix(),  prefix)) {
				return getNamespaceURI();
			}
			
			NamedNodeMap attributes = getAttributes();
			if (attributes != null) {
				for (int i = 0 ; i < attributes.getLength() ; i++) {
					Attr attr = (Attr)attributes.item(i);
					if (StringUtils.equals("xmlns", attr.getPrefix()) && StringUtils.equals(attr.getLocalName(), prefix)) {
						if (!StringUtils.isEmpty(attr.getValue())) {
							return attr.getValue();
						} else {
							return null;
						}
					} else if (StringUtils.equals("xmlns", attr.getLocalName()) && prefix == null) {
						if (!StringUtils.isEmpty(attr.getValue())) {
							return attr.getValue();
						} else {
							return null;
						}
					}
				}
			}
			
			// TODO: Implement ancestor lookup;
			// If element has an ancestor element - return ancestor.lookupNamespaceURI(prefix);
			return null;
		case DOCUMENT_NODE:
			Document document = (Document)this;
			return document.getDocumentElement().lookupNamespaceURI(prefix);
		case ENTITY_NODE:
		case NOTATION_NODE:
		case DOCUMENT_TYPE_NODE:
		case DOCUMENT_FRAGMENT_NODE:
			return null;
		case ATTRIBUTE_NODE:
			Attr attr = (Attr)this;
			if (attr.getOwnerElement() != null) {
				return attr.getOwnerElement().lookupNamespaceURI(prefix);
			} else {
				return null;
			}
		default:
			// TODO: Implement ancestor lookup
			// If element has an ancestor element - return ancestor.lookupNamespaceURI(prefix);
			return null;
		}
	}
	

	@Override
	public String lookupPrefix(String namespaceURI) {
		return null;
	}
	
	

	@Override
	public void normalize() {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Removes the child node indicated by oldChild from the list of children, and returns it.
	 */
	@Override
	public Node removeChild(Node oldChild) throws DOMException {
		
		if (!children.contains(oldChild)) {
			throw new DOMException(DOMException.NOT_FOUND_ERR, "oldChild is not a child of this node");
		} else {
			children.remove(removeChildParent(oldChild));
		}
		
		return oldChild;
	}

	@Override
	public Node replaceChild(Node oldChild, Node newChild) throws DOMException {
		
		int index = children.indexOf(oldChild);
		if (index == -1) {
			throw new DOMException(DOMException.NOT_FOUND_ERR, "oldChild is not a child of this node");
		}
		
		checkPreInsertionValidity(newChild, this, null);
		
		// TODO: If newChild is in the tree - remove it
		removeChild(oldChild);
		children.add(index, setChildParent(newChild));
		
		return newChild;
	}

	

	@Override
	public void setPrefix(String prefix) throws DOMException {
		this.prefix = prefix;
	}

		
	private void checkPreInsertionValidity(Node newNode, Node parent, Node refChild) throws DOMException {	
		// Ensure pre-insertion validiyy
		if (!(parent instanceof Document) && !(parent instanceof DocumentFragment) && !(parent instanceof Element)) {
			throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR, "Parent must be a Document or DocumentFragment or Element");
		}
		
		/*
		 * TODO: Add this check back in
		if (parent.contains(node)) {
			throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR, "Target Node is already contained within this node");
		}
		*/
		
		if (refChild != null && refChild.getParentNode() != parent) {
			throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR, "Target child is not a child of this node");
		}
		
		if (
				!(newNode instanceof DocumentFragment) &&
				!(newNode instanceof DocumentType) &&
				!(newNode instanceof Element) &&
				!(newNode instanceof Text) &&
				!(newNode instanceof ProcessingInstruction) &&
				!(newNode instanceof Comment)
		) {
			throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR, "Node is not allowed as a child");
		}
		
		if (
				((newNode instanceof Text) && (this instanceof Document)) ||
				((newNode instanceof DocumentType) && !(this instanceof Document))
		) {
			throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR, "Node is not alowwed as a child of this parent");	
		}
		
		/*
		 * TODO: When a node is newly created and not added to the tree, the ownerDocment property is null
		Document ownerDocument = getOwnerDocument();
		if (ownerDocument != null && ownerDocument != child.getOwnerDocument()) {
			throw new DOMException(DOMException.WRONG_DOCUMENT_ERR, "Child was created by a different document");
		}
		*/
		
		if (this instanceof Document) {
			
			if (newNode instanceof DocumentFragment) {
				
			} else if (newNode instanceof Element) {
				
			} else if (newNode instanceof DocumentType) {
				
			}
		}
	}
	
	
	private Node setChildParent(Node child) {
		
		NodeImpl childImpl = (NodeImpl)child;
		childImpl.setParentNode(this);
		return child;
	}
	
	
	private Node removeChildParent(Node child) {
		
		NodeImpl childImpl = (NodeImpl)child;
		childImpl.setParentNode(null);
		return child;
	}
	
	
	@Override
	public String getTextContent() throws DOMException {		
		switch (getNodeType()) {
		case ELEMENT_NODE:
		case ATTRIBUTE_NODE:
		case ENTITY_NODE:
		case ENTITY_REFERENCE_NODE:
		case DOCUMENT_FRAGMENT_NODE:
			String text = "";
			NodeList childNodes = getChildNodes();
			for (int i = 0 ; i < childNodes.getLength() ; i++) {
				Node childNode = childNodes.item(i);
				if (childNode.getNodeType() != COMMENT_NODE && childNode.getNodeType() != PROCESSING_INSTRUCTION_NODE) {
					text += childNode.getTextContent();
				}
			}
			return text;
		case TEXT_NODE:
		case CDATA_SECTION_NODE:
		case COMMENT_NODE:
		case PROCESSING_INSTRUCTION_NODE:
			return getNodeValue();
		default:
			return null;
		}
	}
	
	
	@Override
	public void setTextContent(String textContent) throws DOMException {
		
		if (StringUtils.isEmpty(textContent)) {
			// Do nothing
		}
		
		switch (getNodeType()) {
		case ELEMENT_NODE:
		case ATTRIBUTE_NODE:
		case ENTITY_NODE:
		case ENTITY_REFERENCE_NODE:
		case DOCUMENT_FRAGMENT_NODE:
			// All child nodes are removed
			NodeList childNodes = getChildNodes();
			for (int i = 0 ; i < childNodes.getLength() ; i++) {
				Node childNode = childNodes.item(i);
				removeChild(childNode);
			}
			
			// And replaced by a single text node
			
		case TEXT_NODE:
		case CDATA_SECTION_NODE:
		case COMMENT_NODE:
		case PROCESSING_INSTRUCTION_NODE:
			setNodeValue(textContent);
			break;
		default:
			// No affect
			break;
		}
	}
	
	
	@Override
	public boolean equals(Object obj) {
		
		if (obj instanceof Node) {
			return isEqualNode((Node)obj);
		} else {
			return false;
		}
	}
	
	
	@Override
	public int hashCode() {
		
		HashCodeBuilder builder = new HashCodeBuilder();
		builder
			.append(this.getNodeType())
			.append(this.getNodeName())
			.append(this.getNamespaceURI())
			.append(this.getLocalName())
			.append(this.getPrefix())
			.append(this.getNodeValue())
			.append(this.getAttributes())
			.append(this.getChildNodes());
		
		return builder.toHashCode();
	}
}
