package twg2.treeLike;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.Predicate;

import twg2.treeLike.parameters.IndexedTreeTraverseParameters;
import twg2.treeLike.parameters.KeyTreeTraverseParameters;
import twg2.treeLike.parameters.TreePathTraverseParameters;
import twg2.treeLike.parameters.TreeTraverseParametersImpl;
import twg2.treeLike.simpleTree.SimpleTreeUtil;

/** Utility methods for {@link TreeLike} traversal
 * @author TeamworkGuy2
 * @since 2015-7-11
 */
public class TreeTraverse {

	private TreeTraverse() { throw new AssertionError("cannot instantiate static class TreeTraverse"); }


	public static class Indexed {

		public static <R> void traverse(IndexedTreeTraverseParameters<R> params) {
			switch(params.getTraversalOrder()) {
			case POST_ORDER:
				TreeTraverse.Indexed.traversePostOrder(params);
				break;
			case PRE_ORDER:
				TreeTraverse.Indexed.traversePreOrder(params);
				break;
			default:
				throw new IllegalArgumentException("tree traversal order " + params.getTraversalOrder() + " not supported");
			}
		}


		public static <R> void traversePostOrder(IndexedTreeTraverseParameters<R> params) {
			TreeTraversePostOrder.traverseIndexedPostOrder(0, 1, 0, null, params.getTree(), params.isSkipRoot(), params.isSkipNullRoot(),
					params.getHasChildren(), params.getChildrenGetter(), params.getConsumerIndexed(),
					params.isOnlyVisitLeaves(), params.getStartSubtreeFunc(), params.getEndSubtreeFunc());
		}


		public static <R> void traversePreOrder(IndexedTreeTraverseParameters<R> params) {
			TreeTraversePreOrder.traverseIndexedPreOrder(0, 1, 0, null, params.getTree(), params.isSkipRoot(), params.isSkipNullRoot(),
					params.getHasChildren(), params.getChildrenGetter(), params.getConsumerIndexed(),
					params.isOnlyVisitLeaves(), params.getStartSubtreeFunc(), params.getEndSubtreeFunc());
		}


		public static <R> void traverseNodesDepthFirst(IndexedTreeTraverseParameters<R> params) {
			TreeTraversePostOrder.traverseIndexedPostOrder(0, 1, 0, null, params.getTree(), params.isSkipRoot(), params.isSkipNullRoot(),
					params.getHasChildren(), params.getChildrenGetter(), params.getConsumerIndexed(),
					false, params.getStartSubtreeFunc(), params.getEndSubtreeFunc());
		}

	}


	// simple tree traversal
	public static <R> void traverse(TreeTraverseParametersImpl<R> params) {
		switch(params.getTraversalOrder()) {
		case POST_ORDER:
			TreeTraverse.traversePostOrder(params);
			break;
		case PRE_ORDER:
			TreeTraverse.traversePreOrder(params);
			break;
		default:
			throw new IllegalArgumentException("tree traversal order " + params.getTraversalOrder() + " not supported");
		}
	}


	public static <K, V> void traverse(KeyTreeTraverseParameters<K, V> params) {
		switch(params.getTraversalOrder()) {
		case POST_ORDER:
			TreeTraverse.traversePostOrder(params);
			break;
		case PRE_ORDER:
			TreeTraverse.traversePreOrder(params);
			break;
		default:
			throw new IllegalArgumentException("tree traversal order " + params.getTraversalOrder() + " not supported");
		}
	}


	public static <K, V> void traversePostOrder(KeyTreeTraverseParameters<K, V> params) {
		TreeTraversePostOrder.traversePostOrder(0, null, new AbstractMap.SimpleImmutableEntry<K, V>(null, params.getTree()), params.isSkipRoot(), params.isSkipNullRoot(),
				(t) -> params.getHasChildren().test(t.getValue()),
				(t) -> params.getChildrenGetter().apply(t.getValue()),
				params.getConsumer(), params.isOnlyVisitLeaves(), params.getStartSubtreeFunc(), params.getEndSubtreeFunc());
	}


	public static <K, V> void traversePreOrder(KeyTreeTraverseParameters<K, V> params) {
		TreeTraversePreOrder.traversePreOrder(0, null, new AbstractMap.SimpleImmutableEntry<K, V>(null, params.getTree()), params.isSkipRoot(), params.isSkipNullRoot(),
				(t) -> params.getHasChildren().test(t.getValue()),
				(t) -> params.getChildrenGetter().apply(t.getValue()),
				params.getConsumer(), params.isOnlyVisitLeaves(), params.getStartSubtreeFunc(), params.getEndSubtreeFunc());
	}


	public static <R> void traversePostOrder(TreeTraverseParametersImpl<R> params) {
		TreeTraversePostOrder.traversePostOrder(0, null, params.getTree(), params.isSkipRoot(), params.isSkipNullRoot(),
				params.getHasChildren(), params.getChildrenGetter(), params.getConsumer(),
				params.isOnlyVisitLeaves(), params.getStartSubtreeFunc(), params.getEndSubtreeFunc());
	}


	public static <R> void traversePreOrder(TreeTraverseParametersImpl<R> params) {
		TreeTraversePreOrder.traversePreOrder(0, null, params.getTree(), params.isSkipRoot(), params.isSkipNullRoot(),
				params.getHasChildren(), params.getChildrenGetter(), params.getConsumer(),
				params.isOnlyVisitLeaves(), params.getStartSubtreeFunc(), params.getEndSubtreeFunc());
	}


	public static <R> void traverseTreeWithPath(TreePathTraverseParameters<R> params) {
		List<R> parentStack = new ArrayList<>();
		Logic.traverseTreeWithPath(0, 1, 0, null, params.getTree(), params.getHasChildren(), params.getChildrenGetter(), params.isOnlyVisitLeaves(), parentStack,
				params.getConsumerTreePath(), params.getStartSubtreeFunc(), params.getEndSubtreeFunc());
	}


	// retrieve all levels of children from a tree
	public static <R> void treeToDepthLists(IndexedTreeTraverseParameters<R> params, List<List<R>> dst) {
		Logic.treeToDepthLists(0, 1, 0, null, params.getTree(), params.getHasChildren(), params.getChildrenGetter(), params.isOnlyVisitLeaves(), dst);
	}




	/** Tree traversal functions that take many flags and parameters for complete control of tree traversal
	 * @see TreeTransform
	 * @see SimpleTreeUtil
	 * @see TreeTraversePostOrder
	 * @see TreeTraversePreOrder
	 */
	public static class Logic {

		// ==== traverse with path stack ====
		public static <R> void traverseTreeWithPath(int index, int size, int depth, R parent, R tree, Predicate<R> hasChildren, Function<R, ? extends Collection<R>> childrenGetter,
				boolean consumeOnlyLeafNodes, List<R> parentStack, TreePathConsumer<R> consumer, IntConsumer startNodeFunc, IntConsumer endNodeFunc) {
			if(!hasChildren.test(tree)) {
				consumer.accept(tree, depth, parentStack);
				// return early because no children
				return;
			}

			Collection<R> children = childrenGetter.apply(tree);
			int count = 0;

			parentStack.add(tree);

			int sizeI = children.size();
			for(R subtree : children) {
				if(count == 0) {
					depth++;
					if(startNodeFunc != null) {
						startNodeFunc.accept(depth);
					}
				}

				if(subtree != null) {
					traverseTreeWithPath(count, sizeI, depth, tree, subtree, hasChildren, childrenGetter, consumeOnlyLeafNodes, parentStack, consumer, startNodeFunc, endNodeFunc);
				}

				count++;
			}

			parentStack.remove(parentStack.size() - 1);

			if(count > 0) {
				if(endNodeFunc != null) {
					endNodeFunc.accept(depth);
				}
				if(!consumeOnlyLeafNodes) {
					consumer.accept(tree, depth - 1, parentStack);
				}
				depth--;
			}
		}


		public static <R> void treeToDepthLists(int index, int size, int depth, R parent, R tree, Predicate<R> hasChildren, Function<R, ? extends List<? extends R>> childrenGetter,
				boolean consumeOnlyLeafNodes, List<List<R>> dst) {
			if(!hasChildren.test(tree)) {
				while(dst.size() <= (!consumeOnlyLeafNodes ? depth : 0)) {
					dst.add(new ArrayList<>());
				}
				if(!consumeOnlyLeafNodes) {
					dst.get(depth).add(tree);
				}
				else {
					dst.get(0).add(tree);
				}
				// return early because no children
				return;
			}

			List<? extends R> children = childrenGetter.apply(tree);
			int count = 0;

			int sizeI = children.size();
			while(count < sizeI) {
				if(count == 0) { depth++; }

				R subtree = children.get(count);
				if(subtree != null) {
					treeToDepthLists(count, sizeI, depth, tree, subtree, hasChildren, childrenGetter, consumeOnlyLeafNodes, dst);
				}

				count++;
			}

			if(count > 0) {
				if(!consumeOnlyLeafNodes) {
					while(dst.size() < depth) {
						dst.add(new ArrayList<>());
					}
					dst.get(depth - 1).add(tree);
				}

				depth--;
			}
		}

	}

}
