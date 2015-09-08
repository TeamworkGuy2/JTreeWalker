package twg2.treeLike.simpleTree;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import lombok.val;

/**
 * @author TeamworkGuy2
 * @since 2015-9-5
 */
public class SimpleKeyTreeImpl<K, D> implements SimpleKeyTree<K, D> {
	private Supplier<? extends Map<K, SimpleKeyTreeImpl<K, D>>> mapCtor;
	private SimpleKeyTreeImpl<K, D> parent;
	private Map<K, SimpleKeyTreeImpl<K, D>> children;
	private K key;
	private D data;


	public SimpleKeyTreeImpl(K key, D data) {
		this.key = key;
		this.data = data;
	}


	public SimpleKeyTreeImpl(SimpleKeyTreeImpl<K, D> parent, K key, D data) {
		this.parent = parent;
		this.key = key;
		this.data = data;
	}


	@SafeVarargs
	public SimpleKeyTreeImpl(SimpleKeyTreeImpl<K, D> parent, K key, D data, Map.Entry<K, D>... children) {
		this(parent, key, data);
		lazyInitChildren(children);
	}


	public SimpleKeyTreeImpl(SimpleKeyTreeImpl<K, D> parent, K key, D data, Map<K, D> children) {
		this(parent, key, data);
		lazyInitChildren(children);
	}


	public SimpleKeyTreeImpl(SimpleKeyTreeImpl<K, D> parent, K key, D data, Function<D, K> toKey, Collection<D> children) {
		this(parent, key, data);
		lazyInitChildren(toKey, children);
	}


	@Override
	public SimpleKeyTreeImpl<K, D> getParent() {
		return parent;
	}


	@Override
	public K getKey() {
		return key;
	}


	@Override
	public D getData() {
		return data;
	}


	@Override
	public boolean hasChildren() {
		return children != null && children.size() > 0;
	}


	@Override
	public int size() {
		return children != null ? children.size() : 0;
	}


	@SuppressWarnings("unchecked")
	@Override
	public Map<K, SimpleKeyTree<K, D>> getChildren() {
		return (Map<K, SimpleKeyTree<K, D>>)(Map<K, ? extends SimpleKeyTree<K, D>>)children;
	}


	@Override
	public SimpleKeyTreeImpl<K, D> getChild(K key) {
		return children != null ? children.get(key) : null;
	}


	@Override
	public SimpleKeyTreeImpl<K, D> addChild(Map.Entry<K, D> child) {
		return addChild(child.getKey(), child.getValue());
	}


	@Override
	public SimpleKeyTreeImpl<K, D> addChild(K childKey, D childData) {
		SimpleKeyTreeImpl<K, D> newChild = newChild(childKey, childData);
		addChild(newChild);
		return newChild;
	}


	public void addChild(SimpleKeyTreeImpl<K, D> child) {
		addChild(null, child);
	}


	public void addChild(K key, SimpleKeyTreeImpl<K, D> child) {
		lazyInitChildren((Map<K, D>)null);
		if(key != null) {
			child.key = key;
		}
		this.children.put(child.getKey(), child);
	}


	@Override
	public boolean removeChild(K childKey) {
		return this.children != null ? this.children.remove(childKey) != null : false;
	}


	private final SimpleKeyTreeImpl<K, D> newChild(K childKey, D childData) {
		val child = new SimpleKeyTreeImpl<>(this, childKey, childData);
		child.setChildMapConstructor(mapCtor);
		return child;
	}


	private final void lazyInitChildren(Map<K, D> childs) {
		if(children == null) {
			children = (mapCtor != null ? mapCtor.get() : new HashMap<>());
			if(childs != null) {
				addChilds(childs);
			}
		}
	}


	private final void lazyInitChildren(Function<D, K> toKey, Collection<D> childs) {
		if(children == null) {
			children = (mapCtor != null ? mapCtor.get() : new HashMap<>());
			if(childs != null) {
				for(D child : childs) {
					addChild(toKey.apply(child), child);
				}
			}
		}
	}


	@SafeVarargs
	private final void lazyInitChildren(Map.Entry<K, D>... childs) {
		if(children == null) {
			children = (mapCtor != null ? mapCtor.get() : new HashMap<>());
			if(childs != null) {
				for(Map.Entry<K, D> child : childs) {
					addChild(child.getKey(), child.getValue());
				}
			}
		}
	}


	final void addChilds(Map<K, D> childs) {
		if(childs != null) {
			for(Map.Entry<K, D> childEntry : childs.entrySet()) {
				SimpleKeyTreeImpl<K, D> child = newChild(childEntry.getKey(), childEntry.getValue());
				children.put(child.getKey(), child);
			}
		}
	}


	public Supplier<? extends Map<K, SimpleKeyTreeImpl<K, D>>> getChildMapConstructor() {
		return mapCtor;
	}


	public void setChildMapConstructor(Supplier<? extends Map<K, SimpleKeyTreeImpl<K, D>>> mapCtor) {
		this.mapCtor = mapCtor;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (key == null ? 0 : key.hashCode()) + (data == null ? 0 : data.hashCode());
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
		SimpleKeyTreeImpl<K, D> other = (SimpleKeyTreeImpl<K, D>)obj;
		if (data == null) {
			if (other.data != null) { return false; }
		} else if(!key.equals(other.key)) {
			return false;
		} else if (!data.equals(other.data)) {
			return false;
		}
		return true;
	}


	public static final <K, D> SimpleKeyTreeImpl<K, D> newInsertionOrderInst(K key, D data) {
		val inst = new SimpleKeyTreeImpl<K, D>(key, data);
		inst.setChildMapConstructor(LinkedHashMap::new);
		return inst;
	}


	public static final <K, D> SimpleKeyTreeImpl<K, D> newInsertionOrderInst(SimpleKeyTreeImpl<K, D> parent, K key, D data) {
		val inst = new SimpleKeyTreeImpl<K, D>(parent, key, data);
		inst.setChildMapConstructor(LinkedHashMap::new);
		return inst;
	}


	@SafeVarargs
	public static final <K, D> SimpleKeyTreeImpl<K, D> newInsertionOrderInst(SimpleKeyTreeImpl<K, D> parent, K key, D data, Map.Entry<K, D>... children) {
		val inst = new SimpleKeyTreeImpl<K, D>(parent, key, data, children);
		inst.setChildMapConstructor(LinkedHashMap::new);
		return inst;
	}


	public static final <K, D> SimpleKeyTreeImpl<K, D> newInsertionOrderInst(SimpleKeyTreeImpl<K, D> parent, K key, D data, Map<K, D> children) {
		val inst = new SimpleKeyTreeImpl<K, D>(parent, key, data, children);
		inst.setChildMapConstructor(LinkedHashMap::new);
		return inst;
	}


	public static final <K, D> SimpleKeyTreeImpl<K, D> newInsertionOrderInst(SimpleKeyTreeImpl<K, D> parent, K key, D data, Function<D, K> toKey, Collection<D> children) {
		val inst = new SimpleKeyTreeImpl<K, D>(parent, key, data, toKey, children);
		inst.setChildMapConstructor(LinkedHashMap::new);
		return inst;
	}

}
