package referenceTree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author TeamworkGuy2
 * @since 2015-4-19
 */
public class ReferenceTreeEntry<T, R> {
	// package-private
	final Reference<T, R> ref;
	final int level;
	final ReferenceTreeEntry<T, R> parent;
	List<ReferenceTreeEntry<T, R>> children;
	List<ReferenceTreeEntry<T, R>> childrenIm;


	/**
	 * @param ref
	 * @param level
	 */
	public ReferenceTreeEntry(Reference<T, R> ref, int level) {
		this.ref = ref;
		this.level = level;
		this.parent = null;
	}


	/**
	 * @param ref
	 * @param level
	 * @param parent
	 * @param children
	 */
	public ReferenceTreeEntry(Reference<T, R> ref, int level,
			ReferenceTreeEntry<T, R> parent, List<ReferenceTreeEntry<T, R>> children) {
		this.ref = ref;
		this.level = level;
		this.parent = parent;
		if(children != null) {
			lazyInitChildrenRefs(children);
		}
	}


	public ReferenceTreeEntry<T, R> copy() {
		ReferenceTreeEntry<T, R> copy = new ReferenceTreeEntry<>(this.ref, this.level, this.parent, this.children);
		return copy;
	}


	public Reference<T, R> getRef() {
		return this.ref;
	}


	public ReferenceTreeEntry<T, R> getParent() {
		return this.parent;
	}


	public int getLevel() {
		return this.level;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ref == null) ? 0 : ref.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		@SuppressWarnings("unchecked")
		ReferenceTreeEntry<T, R> other = (ReferenceTreeEntry<T, R>)obj;
		if (ref == null) {
			if (other.ref != null) {
				return false;
			}
		} else if (!ref.equals(other.ref)) {
			return false;
		}
		return true;
	}


	@Override
	public String toString() {
		return this.ref.toString() + " (level=" + this.level +
				(this.parent != null ? ", parent=" + this.parent.ref : "") +
				(this.childrenIm != null ? ", childCount=" + this.childrenIm.size() : "") + ")";
	}


	private void lazyInitChildrenRefs(List<ReferenceTreeEntry<T, R>> children) {
		if(children != null) {
			this.children = new ArrayList<>(children);
		}
		else {
			this.children = new ArrayList<>();
		}
		this.childrenIm = Collections.unmodifiableList(this.children);
	}

}
