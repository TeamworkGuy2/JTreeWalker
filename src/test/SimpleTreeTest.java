package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import checks.CheckCollections;
import simpleTree.SimpleTree;
import simpleTree.SimpleTreeImpl;
import simpleTree.SimpleTreeUtil;

/**
 * @author TeamworkGuy2
 * @since 2015-7-1
 */
public class SimpleTreeTest {
	private static List<List<String>> tagsByLevel = Arrays.asList(
			Arrays.asList("root"),
			Arrays.asList("A", "Rocks", "B"),
			Arrays.asList("Chair", "Stool", "Granite", "Starship"),
			Arrays.asList("indoor", "outdoor", "Battlecruiser"),
			Arrays.asList("Enterprise", "Hyperion"),
			Arrays.asList("1701-D")
		);


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
	public void testGetNodesByLevel() {
		SimpleTree<String> tree = createTree();
		List<List<String>> dst = new ArrayList<>();

		SimpleTreeUtil.retrieveNodesByDepth(tree, false, dst);

		Assert.assertEquals(tagsByLevel, dst);
	}


	@Test
	public void testTraverseNodesDepthFirst() {
		SimpleTree<String> tree = createTree();
		List<List<String>> dst = createEmptyTagLevelContainer();

		SimpleTreeUtil.traverseNodesDepthFirst(tree, (branch, index, size, depth, parentBranch) -> {
			dst.get(depth).add(branch);
		});

		Assert.assertEquals(tagsByLevel, dst);
	}


	@Test
	public void testRemoveNodesByDepth() {
		SimpleTree<String> tree = createTree();
		List<List<String>> allNodes = new ArrayList<>();

		SimpleTreeUtil.retrieveNodesByDepth(tree, false, allNodes);

		List<List<String>> removedNodes = createEmptyTagLevelContainer();
		SimpleTreeUtil.removeNodesByDepth(tree, false, (branch, depth, parentBranch) -> {
			removedNodes.get(depth).add(branch.getData());
		});

		CheckCollections.assertLooseEqualsDepth2(allNodes, removedNodes);
		Assert.assertTrue(tree.getChildren().size() == 0);
	}


	static SimpleTree<String> createTree() {
		SimpleTree<String> tree = new SimpleTreeImpl<>("root");
		addTreeTags(tree);
		return tree;
	}


	static List<List<String>> createEmptyTagLevelContainer() {
		List<List<String>> dst = new ArrayList<>();
		for(int i = 0, size = tagsByLevel.size(); i < size; i++) {
			dst.add(new ArrayList<>());
		}
		return dst;
	}


	@SuppressWarnings("unused")
	static void addTreeTags(SimpleTree<String> rootTree) {
		/*
		Panel layout:
		       A                  Rocks               B
		     Chair      Stool    Granite           Starship
		indoor outdoor                          Battlecruiser
		                                    Enterprise   Hyperion
		                                     1701-D
		*/

		SimpleTree<String> a = rootTree.addChild("A");
			SimpleTree<String> chair = a.addChild("Chair");
				SimpleTree<String> indoor = chair.addChild("indoor");
				SimpleTree<String> outdoor = chair.addChild("outdoor");
			SimpleTree<String> stool = a.addChild("Stool");

		SimpleTree<String> rocks = rootTree.addChild("Rocks");
			SimpleTree<String> granite = rocks.addChild("Granite");

		SimpleTree<String> b = rootTree.addChild("B");
			SimpleTree<String> starship = b.addChild("Starship");
				SimpleTree<String> battlecruiser = starship.addChild("Battlecruiser");
					SimpleTree<String> enterprise = battlecruiser.addChild("Enterprise");
						SimpleTree<String> _1701_D = enterprise.addChild("1701-D");
					SimpleTree<String> hyperion = battlecruiser.addChild("Hyperion");
	}

}
