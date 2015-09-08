package twg2.treeLike.simpleTree;

import java.util.Map;

import twg2.treeLike.KeyTree;

/**
 * @author TeamworkGuy2
 * @since 2015-9-5
 */
public interface SimpleKeyTree<K, D> extends KeyTree<K, D> {

	@Override
	public Map<K, SimpleKeyTree<K, D>> getChildren();

	@Override
	public SimpleKeyTree<K, D> getChild(K key);

	public SimpleKeyTree<K, D> addChild(K childKey, D childData);

	public SimpleKeyTree<K, D> addChild(Map.Entry<K, D> child);

	public boolean removeChild(K childKey);

}
