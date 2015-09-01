package twg2.treeLike.referenceTree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @param <T> the type of reference being held
 * @param <R> the type of objects holding the references
 * @author TeamworkGuy2
 * @since 2015-4-19
 */
public class ReferenceImpl<T, R> implements Reference<T, R> {
	T reference;
	List<R> referenceHolders;
	List<R> referenceHoldersIm;


	/**
	 * @param reference
	 */
	public ReferenceImpl(T reference) {
		this.reference = reference;
		this.referenceHolders = null;
		this.referenceHoldersIm = null;
	}


	/**
	 * @param reference
	 * @param referenceHolders
	 */
	public ReferenceImpl(T reference, List<R> referenceHolders) {
		this.reference = reference;
		if(referenceHolders != null) {
			lazyInitRefHolders(referenceHolders);
		}
	}


	@Override
	public int getRefCount() {
		lazyInitRefHolders(null);
		return this.referenceHolders.size();
	}


	@Override
	public T getRef() {
		return this.reference;
	}


	@Override
	public void addRef(R ref) {
		lazyInitRefHolders(null);
		this.referenceHolders.add(ref);
	}


	@Override
	public boolean removeRef(R ref) {
		lazyInitRefHolders(null);
		return this.referenceHolders.remove(ref);
	}


	@Override
	public List<R> getRefHolders() {
		lazyInitRefHolders(null);
		return this.referenceHoldersIm;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((reference == null) ? 0 : reference.hashCode());
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
		ReferenceImpl<T, R> other = (ReferenceImpl<T, R>) obj;
		if (reference == null) {
			if (other.reference != null) {
				return false;
			}
		}
		else if (!reference.equals(other.reference)) {
			return false;
		}
		return true;
	}


	@Override
	public String toString() {
		return "ref=" + this.reference;
	}


	private final void lazyInitRefHolders(Collection<R> referenceHolders) {
		if(referenceHolders != null) {
			this.referenceHolders = new ArrayList<>(referenceHolders);
		}
		else {
			this.referenceHolders = new ArrayList<>();
		}
		this.referenceHoldersIm = Collections.unmodifiableList(this.referenceHolders);
	}

}
