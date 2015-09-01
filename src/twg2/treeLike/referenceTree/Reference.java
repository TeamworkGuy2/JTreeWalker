package twg2.treeLike.referenceTree;

import java.util.List;

public interface Reference<T, R> {

	public int getRefCount();

	public T getRef();

	public void addRef(R ref);

	public boolean removeRef(R ref);

	public List<R> getRefHolders();

	@Override
	public int hashCode();

	@Override
	public boolean equals(Object obj);

	@Override
	public String toString();

}