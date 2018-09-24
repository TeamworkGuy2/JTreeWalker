package twg2.treeLike;

import twg2.collections.interfaces.ListReadOnly;

/**
 * @author TeamworkGuy2
 * @since 2015-5-28
 */
public interface TreeLike<D, P extends TreeLike<D, P>> {

	public P getParent();

	public D getData();

	public boolean hasChildren();

	public ListReadOnly<P> getChildren();

}
