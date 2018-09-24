package twg2.treeLike.simpleTree;

import java.util.Arrays;
import java.util.Collection;

import twg2.collections.dataStructures.BaseList;
import twg2.collections.interfaces.ListReadOnly;

/**
 * @author TeamworkGuy2
 * @since 2015-5-27
 */
public class SimpleTreeImpl<T> implements SimpleTree<T> {
	private static ListReadOnly<SimpleTree<?>> emptyList = new BaseList<>();
	private SimpleTree<T> parent;
	private T data;
	private BaseList<SimpleTreeImpl<T>> children;


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


	@Override
	public int size() {
		return children != null ? children.size() : 0;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ListReadOnly<SimpleTree<T>> getChildren() {
		return (ListReadOnly<SimpleTree<T>>)((ListReadOnly)(children != null ? children : emptyList()));
	}


	public BaseList<SimpleTreeImpl<T>> getChildrenRaw() {
		return children != null ? children : emptyList();
	}


	public void setChildren(Collection<T> children) {
		lazyInitChildren(null);
		this.children.clear();
		addChilds(children);
	}


	@Override
	public SimpleTreeImpl<T> addChild(T child) {
		lazyInitChildren(null);
		SimpleTreeImpl<T> newChild = newChild(child);
		this.children.add(newChild);
		return newChild;
	}


	@Override
	public void addChildren(Iterable<? extends T> children) {
		lazyInitChildren(null);
		addChilds(children);
	}


	/** Moves {@code child} tree and its sub-trees to this tree, updating child's parent references.
	 * NOTE: the child node is reused, no new objects are created.
	 * @param child the child {@link SimpleTreeImpl} to move to this tree
	 * @return the new child node added to this tree
	 */
	@SuppressWarnings("unchecked")
	public SimpleTreeImpl<T> addChildTree(SimpleTreeImpl<? extends T> child) {
		lazyInitChildren(null);
		((SimpleTreeImpl<T>)child).setParent(this);
		this.children.add((SimpleTreeImpl<T>)child);
		return (SimpleTreeImpl<T>)child;
	}


	public boolean removeChild(T child) {
		return removeChild(newChild(child));
	}


	@Override
	public boolean removeChild(SimpleTree<T> subTree) {
		return this.children != null ? this.children.remove(subTree) : false;
	}


	public boolean removeChildRef(SimpleTree<T> subTree) {
		return this.children != null ? this.children.removeRef(subTree) : false;
	}


	@Override
	public boolean removeChildren(Iterable<? extends SimpleTree<T>> children) {
		boolean res = true;
		for(SimpleTree<T> child : children) {
			res &= removeChild(child);
		}
		return res;
	}


	private final SimpleTreeImpl<T> newChild(T child) {
		return new SimpleTreeImpl<>(this, child);
	}


	private final void lazyInitChildren(Collection<T> childs) {
		if(children == null) {
			children = new BaseList<>();
			if(childs != null) {
				addChilds(childs);
			}
		}
	}


	final void addChilds(Iterable<? extends T> childs) {
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


	@Override
	public String toString() {
		if(children == null) {
			return data != null ? data.toString() : "null";
		}
		else {
			return super.toString();
		}
	}


	@SuppressWarnings("unchecked")
	private static <_T> BaseList<_T> emptyList() {
		return (BaseList<_T>)emptyList;
	}

}
