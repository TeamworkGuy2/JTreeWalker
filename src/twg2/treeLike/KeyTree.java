package twg2.treeLike;

/**
 * @author TeamworkGuy2
 * @since 2015-9-5
 * @param <K> the type of index key used to lookup children in the tree
 * @param <D> the type of data stored in the tree
 */
public interface KeyTree<K, D> extends KeyTreeLike<K, D, KeyTree<K, D>> {

	public int size();

}
