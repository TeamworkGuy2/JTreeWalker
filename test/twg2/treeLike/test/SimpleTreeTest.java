package twg2.treeLike.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import twg2.collections.builder.ListUtil;
import twg2.junitassist.checks.CheckCollections;
import twg2.treeLike.TreeTraversalOrder;
import twg2.treeLike.TreeTraverse;
import twg2.treeLike.parameters.TreePathTraverseParameters;
import twg2.treeLike.simpleTree.SimpleTree;
import twg2.treeLike.simpleTree.SimpleTreeImpl;
import twg2.treeLike.simpleTree.SimpleTreeUtil;

/**
 * @author TeamworkGuy2
 * @since 2015-7-1
 */
public class SimpleTreeTest {


	@Test
	public void testSimpleTree() {
		SimpleTree<Byte> root = new SimpleTreeImpl<>(Byte.valueOf((byte)2));

		SimpleTree<Byte> child1 = root.addChild(Byte.valueOf((byte)5));
		SimpleTree<Byte> child2 = root.addChild(Byte.valueOf((byte)10));

		SimpleTree<Byte> child1_1 = child1.addChild(Byte.valueOf((byte)6));
		SimpleTree<Byte> child1_2 = child1.addChild(Byte.valueOf((byte)7));
		SimpleTree<Byte> child2_1 = child2.addChild(Byte.valueOf((byte)11));

		/* Tree:            root
		          child1            child2
		    child1_1  child1_2        child2_2
		*/

		// getChildren()
		CheckCollections.assertLooseEquals(Arrays.asList(child1_1, child1_2), child1.getChildren());
		CheckCollections.assertLooseEquals(Arrays.asList(child2_1), child2.getChildren());
		CheckCollections.assertLooseEquals(Arrays.asList(child1, child2), root.getChildren());

		// removeChild()
		root.removeChild(child2);
		CheckCollections.assertLooseEquals(Arrays.asList(child1), root.getChildren());

		// getParent()
		Assert.assertEquals(child1_1.getParent(), child1);
		Assert.assertEquals(child1.getParent(), root);

		// hasChildren()
		Assert.assertTrue(child1.hasChildren());
		Assert.assertFalse(child1_1.hasChildren());
	}


	@Test
	public void testTraverseNodesParents() {
		SimpleTree<String> tree = createTree();

		Map<String, List<String>> tmpParentsByLeaf = new HashMap<>(TreeData.RandomObjects.getParentsByLeaf());

		TreeTraverse.traverseTreeWithPath(TreePathTraverseParameters.of(tree, true, TreeTraversalOrder.POST_ORDER, (t) -> t.hasChildren(), (t) -> t.getChildren())
			.setConsumerTreePath((branch, depth, parents) -> {
				List<String> expectedParents = tmpParentsByLeaf.remove(branch.getData());
				List<String> parentsData = ListUtil.map(parents, (p) -> p.getData());

				CheckCollections.assertLooseEquals("'" + branch.getData() + "', at depth=" + depth, expectedParents, parentsData);
			}));
	}


	@Test
	public void testGetNodesByLevel() {
		SimpleTree<String> tree = createTree();
		List<List<String>> dst = new ArrayList<>();

		SimpleTreeUtil.retrieveNodesByDepth(tree, false, TreeTraversalOrder.POST_ORDER, dst);

		Assert.assertEquals(TreeData.RandomObjects.getTagsByLevel(), dst);
	}


	@Test
	public void testTraverseNodesDepthFirst() {
		SimpleTree<String> tree = createTree();
		List<List<String>> dst = createEmptyTagLevelContainer();

		SimpleTreeUtil.traverseNodesDepthFirst(tree, TreeTraversalOrder.POST_ORDER, (branch, index, size, depth, parentBranch) -> {
			dst.get(depth).add(branch);
		});

		Assert.assertEquals(TreeData.RandomObjects.getTagsByLevel(), dst);
	}


	@Test
	public void testRemoveNodesByDepth() {
		SimpleTree<String> tree = createTree();
		List<List<String>> allNodes = new ArrayList<>();

		SimpleTreeUtil.retrieveNodesByDepth(tree, false, TreeTraversalOrder.POST_ORDER, allNodes);

		List<List<String>> removedNodes = createEmptyTagLevelContainer();
		SimpleTreeUtil.removeNodesByDepth(tree, false, (branch, depth, parentBranch) -> {
			removedNodes.get(depth).add(branch.getData());
		});

		CheckCollections.assertLooseEqualsNested(allNodes, removedNodes);
		Assert.assertTrue(tree.getChildren().size() == 0);
	}


	static SimpleTree<String> createTree() {
		SimpleTree<String> tree = new SimpleTreeImpl<>("root");
		TreeData.RandomObjects.addTreeTags(tree);
		return tree;
	}


	static List<List<String>> createEmptyTagLevelContainer() {
		List<List<String>> dst = new ArrayList<>();
		for(int i = 0, size = TreeData.RandomObjects.getTagsByLevel().size(); i < size; i++) {
			dst.add(new ArrayList<>());
		}
		return dst;
	}

}
