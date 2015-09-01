package twg2.treeLike.test;

import java.util.AbstractMap;

import org.junit.Test;

import twg2.treeLike.TreePrint;
import twg2.treeLike.TreeTransform;
import twg2.treeLike.parameters.SimpleTreeTraverseParameters;
import twg2.treeLike.simpleTree.SimpleTree;
import twg2.treeLike.simpleTree.SimpleTreeImpl;

/**
 * @author TeamworkGuy2
 * @since 2015-8-29
 */
public class TreeTransformTest {

	@Test
	public void testTransform() {
		SimpleTree<String> tree = new SimpleTreeImpl<>(null);
		TreeDataTest.RandomObjects.addTreeTags(tree);

		TreeTransform.transformTree(tree, null,
				(str, parent, parentTransformed) -> new AbstractMap.SimpleImmutableEntry<>(str.hashCode(), new StringBuilder(str.getData())),
				(t) -> t.hasChildren(),
				(t) -> t.getChildren(), (branch, depth, parent) -> {
			System.out.println("got (" + depth + "): " + branch + "\t,\t" + parent);
		});
	}


	@Test
	public void testPrint() {
		SimpleTree<String> tree = new SimpleTreeImpl<>(null);
		TreeDataTest.RandomObjects.addTreeTags(tree);

		TreePrint.printTree(SimpleTreeTraverseParameters.allNodes(tree), System.out);
	}

}
