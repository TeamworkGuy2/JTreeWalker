package twg2.treeLike;

import twg2.collections.interfaces.ListReadOnly;

/**
 * @author TeamworkGuy2
 * @since 2015-9-5
 * @param <D> the type of data stored in the tree
 * @param <P> the parent type of this tree
 */
public interface IndexedTree<D, P extends IndexedTree<D, P>> extends TreeLike<D, P> {

	@Override
	public ListReadOnly<P> getChildren();

	public int size();

}
