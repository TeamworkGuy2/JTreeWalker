package twg2.treeLike.test;

import java.util.AbstractMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import twg2.treeLike.KeyTree;
import twg2.treeLike.TreeFlatten;
import twg2.treeLike.TreeTraversalOrder;
import twg2.treeLike.simpleTree.SimpleKeyTreeImpl;

/**
 * @author TeamworkGuy2
 * @since 2015-9-6
 */
public class SimpleKeyTreeTest {

	@Test
	public void testCreate() {
		SimpleKeyTreeImpl<String, String> tree = new SimpleKeyTreeImpl<>(null, null);
		tree.setChildMapConstructor(() -> new LinkedHashMap<>());
		TreeData.RandomObjects.addKeyTreeTags(tree);

		Map<String, String> keys = TreeFlatten.toMap((KeyTree<String, String>)tree, true, TreeTraversalOrder.PRE_ORDER,
				(t) -> t.hasChildren(),
				(t) -> t.getChildren().entrySet(),
				(k) -> {
					System.out.println("proc: " + k.getKey() + ": " + k.getValue().getData());
					return new AbstractMap.SimpleImmutableEntry<>(k.getKey(), k.getValue().getData());
				}, new LinkedHashMap<>());

		Assert.assertArrayEquals(TreeData.RandomObjects.getPreOrderTags().toArray(new String[0]), keys.keySet().toArray(new String[0]));
		Assert.assertArrayEquals(TreeData.RandomObjects.getPreOrderTags().toArray(new String[0]), keys.values().toArray(new String[0]));
	}

}
