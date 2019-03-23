package twg2.treeLike.referenceTree;

import java.util.List;

/** A reference containing a reference a mutable list of reference holders
 * @author TeamworkGuy2
 * @since 2015-9-1
 * @param <T> the types type of reference
 * @param <R> the type of reference holders
 */
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