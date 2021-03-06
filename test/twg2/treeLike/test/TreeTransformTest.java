package twg2.treeLike.test;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Assert;
import org.junit.Test;

import twg2.treeLike.TreePrint;
import twg2.treeLike.TreeTransform;
import twg2.treeLike.TreeTraversalOrder;
import twg2.treeLike.TreeTraverse;
import twg2.treeLike.parameters.SimpleTreeTraverseParameters;
import twg2.treeLike.simpleTree.SimpleTree;
import twg2.treeLike.simpleTree.SimpleTreeImpl;

/**
 * @author TeamworkGuy2
 * @since 2015-8-29
 */
public class TreeTransformTest {

	@Test
	public void testTraverseTransform() {
		SimpleTree<String> tree = new SimpleTreeImpl<>(null);
		TreeData.RandomObjects.addTreeTags(tree);

		System.out.println("==== testTraverseTransform() ====");

		TreeTransform.traverseTransformTree(tree, null,
				(str, parent, parentTransformed) -> new AbstractMap.SimpleImmutableEntry<>(str.hashCode(), new StringBuilder(str.getData())),
				(t) -> t.hasChildren(),
				(t) -> t.getChildren(), (branch, depth, parent) -> {
			System.out.println("got (" + depth + "): " + branch + "\t,\t" + parent);
		});

		System.out.println();
	}


	@Test
	public void testTransform() {
		SimpleTree<String> tree = new SimpleTreeImpl<>(null);
		TreeData.RandomObjects.addTreeTags(tree);

		SimpleTree<Map.Entry<Integer, StringBuilder>> copy = TreeTransform.transformSimpleTree(tree,
				(str, parent, parentTransformed) -> new AbstractMap.SimpleImmutableEntry<>(str.hashCode(), new StringBuilder(str)),
				(branch, depth, parent) -> {
			System.out.println("got (" + depth + "): " + branch + "\t,\t" + parent);
		});

		ArrayList<String> leavesPreOrder = new ArrayList<>();

		TreeTraverse.Indexed.traverse(SimpleTreeTraverseParameters.allNodes(copy, TreeTraversalOrder.PRE_ORDER)
			.setConsumerSimpleTree((branch, index, size, depth, parentBranch) -> {
				leavesPreOrder.add(branch.getValue().toString());
			}, true));

		Assert.assertEquals(TreeData.RandomObjects.getPreOrderTags(), leavesPreOrder);
	}


	@Test
	public void testPreOrder() {
		SimpleTree<String> tree = TreeData.RandomObjects.createTreeTags();
		List<String> preOrderTags = TreeData.RandomObjects.getPreOrderTags();
		AtomicInteger i = new AtomicInteger();

		System.out.println("==== testPreOrder() ====");

		TreeTraverse.Indexed.traverse(SimpleTreeTraverseParameters.allNodes(tree, true, TreeTraversalOrder.PRE_ORDER)
			.setConsumerSimpleTree((branch, index, size, depth, parentBranch) -> {
				String expectTag = preOrderTags.get(i.get());

				// TODO debugging
				System.out.println("pre-order " + i.get() + ": " + branch + "\t(expect: " + expectTag + ")");

				Assert.assertEquals(expectTag, branch);
				i.incrementAndGet();
			}));

		System.out.println();
	}


	@Test
	public void testPostOrder() {
		SimpleTree<String> tree = TreeData.RandomObjects.createTreeTags();
		List<String> postOrderTags = TreeData.RandomObjects.getPostOrderTags();
		AtomicInteger i = new AtomicInteger();

		System.out.println("==== testPostOrder() ====");

		TreeTraverse.Indexed.traverse(SimpleTreeTraverseParameters.allNodes(tree, true, TreeTraversalOrder.POST_ORDER)
			.setConsumerSimpleTree((branch, index, size, depth, parentBranch) -> {
				String expectTag = postOrderTags.get(i.get());

				// TODO debugging
				System.out.println("post-order " + i.get() + ": " + branch + "\t(expect: " + expectTag + ")");

				Assert.assertEquals(expectTag, branch);
				i.incrementAndGet();
			}));

		System.out.println();
	}


	@Test
	public void testPrint() {
		SimpleTree<String> tree = new SimpleTreeImpl<>(null);
		TreeData.RandomObjects.addTreeTags(tree);

		System.out.println("==== testPrint() ====");
		TreePrint.printTree(tree, System.out);
		System.out.println();
	}

}
