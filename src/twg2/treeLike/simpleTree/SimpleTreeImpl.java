package twg2.treeLike.simpleTree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author TeamworkGuy2
 * @since 2015-5-27
 */
public class SimpleTreeImpl<T> implements SimpleTree<T> {
	private SimpleTree<T> parent;
	private T data;
	private List<SimpleTree<T>> children;


	public SimpleTreeImpl(T data) {
		this.data = data;
	}


	public SimpleTreeImpl(SimpleTree<T> parent, T data) {
		this.parent = parent;
		this.data = data;
	}


	@SafeVarargs
	public SimpleTreeImpl(SimpleTree<T> parent, T data, T... children) {
		this(parent, data);
		lazyInitChildren(Arrays.asList(children));
	}


	public SimpleTreeImpl(SimpleTree<T> parent, T data, Collection<T> children) {
		this(parent, data);
		lazyInitChildren(children);
	}


	@Override
	public SimpleTree<T> getParent() {
		return parent;
	}


	public void setParent(SimpleTree<T> parent) {
		this.parent = parent;
	}


	@Override
	public T getData() {
		return data;
	}


	public void setData(T data) {
		this.data = data;
	}


	@Override
	public boolean hasChildren() {
		return children != null && children.size() > 0;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<SimpleTree<T>> getChildren() {
		return children != null ? (List<SimpleTree<T>>)((List)children) : Collections.emptyList();
	}


	public void setChildren(Collection<T> children) {
		lazyInitChildren(null);
		addChilds(children);
	}


	@Override
	public SimpleTree<T> addChild(T child) {
		lazyInitChildren(null);
		SimpleTreeImpl<T> newChild = newChild(child);
		this.children.add(newChild);
		return newChild;
	}


	public boolean removeChild(T child) {
		return removeChild(newChild(child));
	}


	@Override
	public boolean removeChild(SimpleTree<T> subTree) {
		return this.children != null ? this.children.remove(subTree) : false;
	}


	private final SimpleTreeImpl<T> newChild(T child) {
		return new SimpleTreeImpl<>(this, child);
	}


	private final void lazyInitChildren(Collection<T> childs) {
		if(children == null) {
			children = new ArrayList<>();
			if(childs != null) {
				addChilds(childs);
			}
		}
	}


	final void addChilds(Collection<T> childs) {
		if(childs != null) {
			for(T child : childs) {
				children.add(newChild(child));
			}
		}
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj) { return true; }
		if (obj == null) { return false; }
		if (getClass() != obj.getClass()) {
			return false;
		}
		@SuppressWarnings("unchecked")
		SimpleTreeImpl<T> other = (SimpleTreeImpl<T>)obj;
		if (data == null) {
			if (other.data != null) { return false; }
		} else if (!data.equals(other.data)) {
			return false;
		}
		return true;
	}

}
