package twg2.treeLike.simpleTree;

import twg2.treeLike.KeyTree;

/**
 * @author TeamworkGuy2
 * @since 2015-9-5
 */
public interface SimpleKeyTree<K, D> extends KeyTree<K, D> {

	public SimpleKeyTree<K, D> addChild(K childKey, D childData);

	public boolean removeChild(K childKey);

}
