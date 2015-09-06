package twg2.treeLike;

import java.util.List;

/**
 * @author TeamworkGuy2
 * @since 2015-9-5
 * @param <D> the type of data stored in the tree
 * @param <P> the parent type of this tree
 */
public interface IndexedTree<D, P extends IndexedTree<D, P>> extends TreeLike<D, P> {

	@Override
	public List<P> getChildren();

	public int size();

}
