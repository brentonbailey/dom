package com.bbailey.w3.dom;

import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class NodeListImpl implements NodeList {

	private final List<Node> nodes;
	
	public NodeListImpl(List<Node> nodes) {
		this.nodes = nodes;
	}
	
	@Override
	public int getLength() {
		return nodes.size();
	}

	@Override
	public Node item(int index) {
		
		// This method will never throw an exception
		if (index >= 0 && index < nodes.size()) {
			return nodes.get(index);
		} else {
			return null;
		}
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
		if (!(obj instanceof NodeListImpl)) {
			return false;
		}
		NodeListImpl other = (NodeListImpl) obj;
		if (nodes == null) {
			if (other.nodes != null) {
				return false;
			}
		} else if (!nodes.equals(other.nodes)) {
			return false;
		}
		return true;
	}
}
