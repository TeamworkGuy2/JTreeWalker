package referenceTree.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import referenceTree.Reference;
import referenceTree.ReferenceImpl;
import referenceTree.ReferenceProcessor;
import referenceTree.ReferenceTree;
import referenceTree.ReferenceTreeEntry;

/**
 * @author TeamworkGuy2
 * @since 2015-4-20
 */
public class RefTreeTest {


	/**
	 * @param root
	 * @param flatMap a list of ref values to stack depth
	 * @return
	 */
	static ReferenceTree<String, String> referenceTestDataToTree(String root, List<Map.Entry<String, Integer>> flatMap) {
		ReferenceTree<String, String> tree = new ReferenceTree<>();

		List<ReferenceTreeEntry<String, String>> parentRefStack = new ArrayList<>();
		ReferenceTreeEntry<String, String> prevStackParent = tree.addRef(new ReferenceImpl<>(root));
		ReferenceTreeEntry<String, String> lastRef = prevStackParent;
		int stackDepth = 0;

		int i = 0;
		for(Map.Entry<String, Integer> entry : flatMap) {
			// if the entry is one level deeper than it's parent, add it as a child of the parent
			if(entry.getValue() > stackDepth) {
				parentRefStack.add(lastRef);
				if(entry.getValue() > stackDepth + 1) {
					throw new IllegalStateException("two consecutive values cannot increase in stack depth by more than 1");
				}
				stackDepth = entry.getValue();
			}
			// if the entry is one or more levels higher than the previous parent, pop parent elements off the stack until the appropriate level for this entry to be added to is reached
			else if(entry.getValue() < stackDepth) {
				while(stackDepth > entry.getValue()) {
					parentRefStack.remove(parentRefStack.size() - 1);
					stackDepth--;
				}
			}
			Reference<String, String> entryRef = new ReferenceImpl<>(entry.getKey());
			lastRef = tree.addRef(entryRef, parentRefStack.get(parentRefStack.size() - 1));

			i++;
		}

		return tree;
	}


	@Test
	public void createNestedTreeTest() {
		Map<String, List<String>> treeInfo = RefTreeMockData.createTestTreeInfo();
		Map.Entry<Map<Reference<String, String>, List<ReferenceTreeEntry<String, String>>>, Map<Reference<String, String>, String>> masterTree = ReferenceProcessor.createFirstLevelRefTree(treeInfo.keySet(), (k) -> treeInfo.get(k), (ref) -> ref, (ref) -> ref);

		Map<String, ReferenceTree<String, String>> trees = new HashMap<>();
		Map<String, List<List<ReferenceTreeEntry<String, String>>>> treesFlat = new HashMap<>();
		Reference<String, String> duplicateRef = new ReferenceImpl<String, String>("!!duplicate");
		for(Map.Entry<String, ReferenceTree<String, String>> tree : ReferenceProcessor.createRefTrees(treeInfo.keySet(), (k) -> treeInfo.get(k), (String ref) -> ref, (ref) -> ref, duplicateRef)) {
			String treeName = tree.getKey();
			if(trees.containsKey(treeName)) {
				throw new IllegalStateException("duplicate tree " + treeName);
			}
			trees.put(treeName, tree.getValue());
			List<List<ReferenceTreeEntry<String, String>>> treeFlat = ReferenceTree.treeToLevels(tree.getValue());
			treesFlat.put(treeName, treeFlat);
		}

		Map<String, ReferenceTree<String, String>> expectedTrees = new HashMap<>();
		Map<String, List<List<ReferenceTreeEntry<String, String>>>> expectedTreesFlat = new HashMap<>();
		for(Map.Entry<String, List<Map.Entry<String, Integer>>> expectData : RefTreeMockData.expectTestTreeResult().entrySet()) {
			String treeName = expectData.getKey();
			ReferenceTree<String, String> expectTree = referenceTestDataToTree(treeName, expectData.getValue());
			if(expectedTrees.containsKey(treeName)) {
				throw new IllegalStateException("duplicate expected tree " + treeName);
			}
			expectedTrees.put(treeName, expectTree);
			List<List<ReferenceTreeEntry<String, String>>> expectTreeFlat = ReferenceTree.treeToLevels(expectTree);
			expectedTreesFlat.put(treeName, expectTreeFlat);

			// print out debug info about the trees
			System.out.println();
			System.out.println("flattened_expect_tree: " + treeName);
			for(List<ReferenceTreeEntry<String, String>> expectLevel : expectTreeFlat) {
				Collections.sort(expectLevel, (a, b) -> a.getRef().getRef().compareTo(b.getRef().getRef()));
				System.out.println(Arrays.toString(expectLevel.stream().map((refEntry) -> refEntry.getRef()).toArray()));
			}
			System.out.println();

			/*
			System.out.println("test_1_lvl_tree: " + treeName);
			Map<Reference<String, String>, ReferenceTreeEntry<String, String>> firstLevelTree = null;
			BiConsumer<ReferenceTreeEntry<String, String>, Consumer<ReferenceTreeEntry<String, String>>> handleDuplicate = (ref, addRefCallback) -> {
				System.out.println("duplicate ref: " + ref);
			};
			ReferenceProcessor.extractReferencesFromFirstLevelRefTree(firstLevelTree, handleDuplicate);
			*/

			List<List<ReferenceTreeEntry<String, String>>> treeFlat = treesFlat.get(treeName);
			System.out.println("flattened_tree: " + treeName);
			for(List<ReferenceTreeEntry<String, String>> expectLevel : treeFlat) {
				Collections.sort(expectLevel, (a, b) -> a.getRef().getRef().compareTo(b.getRef().getRef()));
				System.out.println(Arrays.toString(expectLevel.stream().map((refEntry) -> refEntry.getRef()).toArray()));
			}
			System.out.println();

			ReferenceTree<String, String> tree = trees.get(treeName);
			List<List<ReferenceTreeEntry<String, String>>> expectedTreeFlat = expectedTreesFlat.get(treeName);
			System.out.println("map " + treeName + ": equal=" + treeFlat.equals(expectedTreeFlat));
			System.out.println("diff " + treeName + ": " + ReferenceTree.diffTrees(tree, expectTree));
			System.out.println();
			System.out.println("========");
			System.out.println();

			Assert.assertTrue(ReferenceTree.diffTrees(tree, expectTree));
			Assert.assertEquals(treeFlat, expectedTreeFlat);
		}

		System.out.println(trees);
	}

}
