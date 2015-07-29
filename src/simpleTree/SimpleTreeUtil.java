package simpleTree;

import java.util.ArrayList;
import java.util.List;


/**
 * @author TeamworkGuy2
 * @since 2015-7-11
 */
public class SimpleTreeUtil {

	private SimpleTreeUtil() {
		throw new AssertionError("cannot instantiate static class SimpleTreeUtil");
	}


	// simple indexed tree traversal
	public static <R> void traverseLeafNodes(SimpleTree<R> tree, IndexedSubtreeConsumer<R> consumer) {
		TreeUtil.traverseLeafNodes(tree, (t) -> t.hasChildren(), (t) -> t.getChildren(), (t, i, s, d, p) -> consumer.accept(t.getData(), i, s, d, p.getData()));
	}


	public static <R> void traverseAllNodes(SimpleTree<R> tree, IndexedSubtreeConsumer<R> consumer) {
		TreeUtil.traverseAllNodes(tree, (t) -> t.hasChildren(), (t) -> t.getChildren(), (t, i, s, d, p) -> consumer.accept(t.getData(), i, s, d, p.getData()));
	}


	public static <R> void traverseNodesDepthFirst(SimpleTree<R> tree, IndexedSubtreeConsumer<R> consumer) {
		TreeUtil.traverseNodesDepthFirst(tree, (t) -> {
			return t.hasChildren();
		}, (t) -> {
			return t.getChildren();
		}, (t, i, s, d, p) -> {
			consumer.accept(t.getData(), i, s, d, p != null ? p.getData() : null);
		});
	}


	public static <R, S extends TreeLike<R, S>> void traverseLeafNodes(S tree, SubtreeConsumer<R> consumer) {
		TreeUtil.traverseLeafNodes(tree, (t) -> t.hasChildren(), (t) -> t.getChildren(), (t, d, p) -> consumer.accept(t.getData(), d, p.getData()));
	}


	public static <R, S extends TreeLike<R, S>> void traverseAllNodes(S tree, SubtreeConsumer<R> consumer) {
		TreeUtil.traverseAllNodes(tree, (t) -> t.hasChildren(), (t) -> t.getChildren(), (t, d, p) -> consumer.accept(t.getData(), d, p.getData()));
	}


	public static <R> void retrieveNodesByDepth(SimpleTree<R> tree, boolean consumeOnlyLeafNodes, List<List<R>> dst) {
		List<List<SimpleTree<R>>> tmpDst = new ArrayList<>();
		TreeUtil.retrieveNodesByDepth(tree, consumeOnlyLeafNodes, (t) -> t.hasChildren(), (t) -> t.getChildren(), tmpDst);

		for(int i = 0, size = tmpDst.size(); i < size; i++) {
			List<SimpleTree<R>> childrenList = tmpDst.get(i);
			if(dst.size() <= i) {
				dst.add(new ArrayList<>());
			}
			List<R> childrenDstList = dst.get(i);
			for(int ii = 0, sizeI = childrenList.size(); ii < sizeI; ii++) {
				childrenDstList.add(childrenList.get(ii).getData());
			}
		}
	}


	public static <R> void removeNodesByDepth(SimpleTree<R> tree, boolean consumeOnlyLeafNodes) {
		removeNodesByDepth(tree, consumeOnlyLeafNodes, (branch, depth, parentBranch) -> { });
	}


	public static <R> void removeNodesByDepth(SimpleTree<R> tree, boolean consumeOnlyLeafNodes, SubtreeConsumer<SimpleTree<R>> consumer) {
		TreeUtil.removeAllNodes(0, null, tree, (t) -> t.hasChildren(), (t) -> t.getChildren(), consumer, (p, c) -> p.removeChild(c), consumeOnlyLeafNodes);
	}


	/*
	public static <R> SimpleTreeImpl<R> copyToSimpleTree(R tree, Predicate<R> hasChildren, Function<R, Collection<R>> childrenGetter) {
		SimpleTreeImpl<R> dst = new SimpleTreeImpl<R>(tree);

		TreeUtil.traverse
	}
	*/

}
