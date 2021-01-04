package com.bbailey.w3.dom;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.DOMException;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class NamedNodeMapImpl implements NamedNodeMap {

	private final Map<String, Node> nodes;
	
	public NamedNodeMapImpl() {
		this.nodes = new HashMap<>();
	}
	
	public NamedNodeMapImpl(Map<String, Node> nodes) {
		this.nodes = nodes;
	}
	
	@Override
	public int getLength() {
		return nodes.size();
	}

	@Override
	public Node getNamedItem(String name) {
		return nodes.get(name);
	}

	@Override
	public Node getNamedItemNS(String namespaceURI, String localName) throws DOMException {
		return nodes.get(namespaceURI + localName);
	}

	@Override
	public Node item(int index) {
		
		int i = 0;
		for (Node node : nodes.values()) {
			if (i++ == index) {
				return node;
			}
		}
		
		return null;
	}

	@Override
	public Node removeNamedItem(String name) throws DOMException {
		return nodes.remove(name);
	}

	@Override
	public Node removeNamedItemNS(String namespaceURI, String localName) throws DOMException {
		return nodes.remove(namespaceURI + localName);
	}

	@Override
	public Node setNamedItem(Node arg) throws DOMException {
		return nodes.put(arg.getNodeName(), arg);
	}

	@Override
	public Node setNamedItemNS(Node arg) throws DOMException {
		return nodes.put(arg.getNamespaceURI() + arg.getNodeName(), arg);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nodes == null) ? 0 : nodes.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof NamedNodeMapImpl)) {
			return false;
		}
		NamedNodeMapImpl other = (NamedNodeMapImpl) obj;
		if (nodes == null) {
			if (other.nodes != null) {
				return false;
			}
		} else if (!nodes.equals(other.nodes)) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return nodes.values().toString();
	}
}
