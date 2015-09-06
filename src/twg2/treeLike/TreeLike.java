package twg2.treeLike;

import java.util.Collection;

/**
 * @author TeamworkGuy2
 * @since 2015-5-28
 */
public interface TreeLike<D, P extends TreeLike<D, P>> {

	public P getParent();

	public D getData();

	public boolean hasChildren();

	public Collection<P> getChildren();

}
