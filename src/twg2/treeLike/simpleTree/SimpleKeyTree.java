package twg2.treeLike.simpleTree;

import java.util.Map;

import twg2.treeLike.KeyTreeLike;

/** Mutable {@link KeyTreeLike}
 * @author TeamworkGuy2
 * @since 2015-9-5
 */
public interface SimpleKeyTree<K, D> extends KeyTreeLike<K, D> {

	@Override
	public Map<K, SimpleKeyTree<K, D>> getChildren();

	@Override
	public SimpleKeyTree<K, D> getChild(K key);

	/** Add a child key and data to this tree
	 * @param childKey the child key
	 * @param childData the child data
	 * @return the child tree created to hold the new key and data
	 */
	public SimpleKeyTree<K, D> addChild(K childKey, D childData);

	/** Add a child key-data pair to this tree
	 * @param child the child key and data pair
	 * @return the child tree created to hold the new key and data
	 */
	public SimpleKeyTree<K, D> addChild(Map.Entry<K, D> child);

	/** Remove a child node by key from this tree
	 * @param childKey the child key to remove
	 * @return true if a child node matching the {@code childKey} was found and removed from this tree, false otherwise
	 */
	public boolean removeChild(K childKey);

}
