package twg2.treeLike.simpleTree;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntConsumer;

import twg2.treeLike.IndexedTreeConsumer;
import twg2.treeLike.TreeConsumer;
import twg2.treeLike.TreeTransformer;
import twg2.treeLike.TreeLike;
import twg2.treeLike.TreeRemove;
import twg2.treeLike.TreeTransform;
import twg2.treeLike.TreeTraversalOrder;
import twg2.treeLike.TreeTraverse;
import twg2.treeLike.parameters.IndexedTreeTraverseParameters;
import twg2.treeLike.parameters.SimpleTreeTraverseParameters;
import twg2.treeLike.parameters.TreeTraverseParametersImpl;


/**
 * @author TeamworkGuy2
 * @since 2015-7-11
 */
public final class SimpleTreeUtil {

	private SimpleTreeUtil() { throw new AssertionError("cannot instantiate static class SimpleTreeUtil"); }


	// simple indexed tree traversal
	public static <D> void traverseLeafNodes(SimpleTree<D> tree, TreeTraversalOrder traversalOrder, IndexedTreeConsumer<D> consumer) {
		traverseLeafNodes(tree, traversalOrder, consumer, null, null);
	}

	public static <D> void traverseLeafNodes(SimpleTree<D> tree, TreeTraversalOrder traversalOrder, IndexedTreeConsumer<D> consumer, IntConsumer startNodeFunc, IntConsumer endNodeFunc) {
		TreeTraverse.Indexed.traverse(SimpleTreeTraverseParameters.leafNodes(tree, traversalOrder)
				.setConsumerSimpleTree(consumer)
				.setStartSubtreeFunc(startNodeFunc)
				.setEndSubtreeFunc(endNodeFunc));
	}


	public static <D> void traverseAllNodes(SimpleTree<D> tree, TreeTraversalOrder traversalOrder, IndexedTreeConsumer<D> consumer) {
		traverseAllNodes(tree, traversalOrder, consumer, null, null);
	}

	public static <D> void traverseAllNodes(SimpleTree<D> tree, TreeTraversalOrder traversalOrder, IndexedTreeConsumer<D> consumer, IntConsumer startNodeFunc, IntConsumer endNodeFunc) {
		TreeTraverse.Indexed.traverse(SimpleTreeTraverseParameters.allNodes(tree, traversalOrder)
				.setConsumerSimpleTree(consumer)
				.setStartSubtreeFunc(startNodeFunc)
				.setEndSubtreeFunc(endNodeFunc));
	}


	public static <D> void traverseNodesDepthFirst(SimpleTree<D> tree, TreeTraversalOrder traversalOrder, IndexedTreeConsumer<D> consumer) {
		traverseNodesDepthFirst(tree, traversalOrder, consumer, null, null);
	}

	public static <D> void traverseNodesDepthFirst(SimpleTree<D> tree, TreeTraversalOrder traversalOrder, IndexedTreeConsumer<D> consumer, IntConsumer startNodeFunc, IntConsumer endNodeFunc) {
		TreeTraverse.Indexed.traverseNodesDepthFirst(IndexedTreeTraverseParameters.allNodes(tree, traversalOrder,
				(t) -> t.hasChildren(),
				(t) -> t.getChildren())
			.setConsumerIndexed((t, i, s, d, p) -> {
				consumer.accept(t.getData(), i, s, d, p != null ? p.getData() : null);
			})
			.setStartSubtreeFunc(startNodeFunc)
			.setEndSubtreeFunc(endNodeFunc));
	}


	public static <D, S extends TreeLike<D, S>> void traverseLeafNodes(S tree, TreeTraversalOrder traversalOrder, TreeConsumer<D> consumer) {
		traverseLeafNodes(tree, traversalOrder, consumer, null, null);
	}

	public static <D, S extends TreeLike<D, S>> void traverseLeafNodes(S tree, TreeTraversalOrder traversalOrder, TreeConsumer<D> consumer, IntConsumer startNodeFunc, IntConsumer endNodeFunc) {
		TreeTraverse.traverse(TreeTraverseParametersImpl.leafNodes(tree, traversalOrder,
					(t) -> t.hasChildren(),
					(t) -> t.getChildren())
				.setConsumer((t, d, p) -> consumer.accept(t.getData(), d, p != null ? p.getData() : null))
				.setStartSubtreeFunc(startNodeFunc)
				.setEndSubtreeFunc(endNodeFunc));
	}


	public static <D, S extends TreeLike<D, S>> void traverseAllNodes(S tree, TreeTraversalOrder traversalOrder, TreeConsumer<D> consumer) {
		traverseAllNodes(tree, traversalOrder, consumer, null, null);
	}

	public static <D, S extends TreeLike<D, S>> void traverseAllNodes(S tree, TreeTraversalOrder traversalOrder, TreeConsumer<D> consumer, IntConsumer startNodeFunc, IntConsumer endNodeFunc) {
		TreeTraverse.traverse(TreeTraverseParametersImpl.allNodes(tree, traversalOrder,
					(t) -> t.hasChildren(),
					(t) -> t.getChildren())
				.setConsumer((t, d, p) -> consumer.accept(t.getData(), d, p != null ? p.getData() : null))
				.setStartSubtreeFunc(startNodeFunc)
				.setEndSubtreeFunc(endNodeFunc));
	}


	public static <D, S extends TreeLike<D, S>, R> void transformTree(S tree, R treeTransformed, TreeTransformer<S, R> transformer, TreeConsumer<R> consumer) {
		transformTree(tree, treeTransformed, transformer, consumer, null, null);
	}

	public static <D, S extends TreeLike<D, S>, R> void transformTree(S tree, R treeTransformed, TreeTransformer<S, R> transformer, TreeConsumer<R> consumer, IntConsumer startNodeFunc, IntConsumer endNodeFunc) {
		TreeTransform.traverseTransformTree(tree, treeTransformed,
				transformer,
				(t) -> t.hasChildren(),
				(t) -> t.getChildren(), consumer, startNodeFunc, endNodeFunc);
	}


	public static <D> void retrieveNodesByDepth(SimpleTree<D> tree, boolean consumeOnlyLeafNodes, TreeTraversalOrder traversalOrder, List<List<D>> dst) {
		List<List<SimpleTree<D>>> tmpDst = new ArrayList<>();
		TreeTraverse.treeToDepthLists(IndexedTreeTraverseParameters.of(tree, consumeOnlyLeafNodes, traversalOrder,
				(t) -> t.hasChildren(),
				(t) -> t.getChildren()), tmpDst);

		for(int i = 0, size = tmpDst.size(); i < size; i++) {
			List<SimpleTree<D>> childrenList = tmpDst.get(i);
			if(dst.size() <= i) {
				dst.add(new ArrayList<>());
			}
			List<D> childrenDstList = dst.get(i);
			for(int ii = 0, sizeI = childrenList.size(); ii < sizeI; ii++) {
				childrenDstList.add(childrenList.get(ii).getData());
			}
		}
	}


	public static <D> void removeNodesByDepth(SimpleTree<D> tree, boolean consumeOnlyLeafNodes) {
		removeNodesByDepth(tree, consumeOnlyLeafNodes,
				(branch, depth, parentBranch) -> { });
	}

	public static <D> void removeNodesByDepth(SimpleTree<D> tree, boolean consumeOnlyLeafNodes, TreeConsumer<SimpleTree<D>> consumer) {
		TreeRemove.removeAllNodes(0, null, tree,
				(t) -> t.hasChildren(),
				(t) -> t.getChildren(),
				consumer,
				(p, c) -> p.removeChild(c), consumeOnlyLeafNodes);
	}

}
