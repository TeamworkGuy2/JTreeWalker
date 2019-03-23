package twg2.treeLike;

import java.util.Map;

/** A tree similar to {@link TreeLike} where the children are stored as a {@link Map} and each tree node has a key.
 * @author TeamworkGuy2
 * @since 2015-9-5
 * @param <K> the type of index key used to lookup children in the tree
 * @param <D> the type of data stored in the tree
 * @param <P> the parent/child type of this tree
 */
public interface KeyTreeLike<K, D> {

	/** This tree node's key
	 */
	public K getKey();

	/** This tree's parent (possibly null)
	 */
	public KeyTreeLike<K, D> getParent();

	/** This tree node's data (possibly null)
	 */
	public D getData();

	/** The map of immediate children of this tree node (possibly null)
	 */
	public Map<K, ? extends KeyTreeLike<K, D>> getChildren();

	/** Shortcut for {@code getChildren().get(key)}
	 */
	public KeyTreeLike<K, D> getChild(K key);

	/** Shortcut for {@code getChildren().size() > 0}
	 */
	public boolean hasChildren();

	/** Shortcut for {@code getChildren().size()}
	 */
	public int size();

}
