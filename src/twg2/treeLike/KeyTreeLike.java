package twg2.treeLike;

import java.util.Map;

/**
 * @author TeamworkGuy2
 * @since 2015-9-5
 * @param <K> the type of index key used to lookup children in the tree
 * @param <D> the type of data stored in the tree
 * @param <P> the parent type of this tree
 */
public interface KeyTreeLike<K, D, P extends KeyTreeLike<K, D, P>> {

	public P getParent();

	public K getKey();

	public D getData();

	public boolean hasChildren();

	public Map<K, P> getChildren();

	public P getChild(K key);

}
