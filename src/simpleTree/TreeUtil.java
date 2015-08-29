package simpleTree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.RandomAccess;
import java.util.function.Function;
import java.util.function.Predicate;

/** Utility methods for {@link TreeLike} traversal
 * @author TeamworkGuy2
 * @since 2015-7-11
 */
public class TreeUtil {

	private TreeUtil() { throw new AssertionError("cannot instantiate static class TreeUtil"); }


	public static <R> void traverseLeafNodes(R tree, Predicate<R> hasChildren, Function<R, List<R>> childrenGetter, IndexedSubtreeConsumer<R> consumer) {
		traverseIndexedTree(0, 1, 0, null, tree, hasChildren, childrenGetter, consumer, true);
	}


	public static <R> void traverseAllNodes(R tree, Predicate<R> hasChildren, Function<R, List<R>> childrenGetter, IndexedSubtreeConsumer<R> consumer) {
		traverseIndexedTree(0, 1, 0, null, tree, hasChildren, childrenGetter, consumer, false);
	}


	public static <R> void traverseNodesDepthFirst(R tree, Predicate<R> hasChildren, Function<R, List<R>> childrenGetter, IndexedSubtreeConsumer<R> consumer) {
		traverseIndexedTree(0, 1, 0, null, tree, hasChildren, childrenGetter, consumer, false);
	}


	// simple tree traversal
	public static <R> void traverseLeafNodes(R tree, Predicate<R> hasChildren, Function<R, Collection<R>> childrenGetter, SubtreeConsumer<R> consumer) {
		traverseTree(0, null, tree, hasChildren, childrenGetter, consumer, true);
	}


	public static <R> void traverseAllNodes(R tree, Predicate<R> hasChildren, Function<R, Collection<R>> childrenGetter, SubtreeConsumer<R> consumer) {
		traverseTree(0, null, tree, hasChildren, childrenGetter, consumer, false);
	}


	// retrieve all levels of children from a tree
	public static <R> void retrieveNodesByDepth(R tree, boolean consumeOnlyLeafNodes, Predicate<R> hasChildren,
			Function<R, List<R>> childrenGetter, List<List<R>> dst) {
		traverseNodesByDepth(0, 1, 0, null, tree, hasChildren, childrenGetter, consumeOnlyLeafNodes, dst);
	}


	public static <R> void traverseNodesByDepthInPlace(R tree, boolean consumeOnlyLeafNodes, Predicate<R> hasChildren,
			Function<R, List<R>> childrenGetter, TreePathConsumer<R> consumer) {
		List<R> parentStack = new ArrayList<>();
		traverseNodesByDepthInPlace(0, 1, 0, null, tree, hasChildren, childrenGetter, consumeOnlyLeafNodes, parentStack, consumer);
	}


	// traversal functions
	public static <R> void traverseIndexedTree(int index, int size, int depth, R parent, R tree, Predicate<R> hasChildren, Function<R, List<R>> childrenGetter,
			IndexedSubtreeConsumer<R> consumer, boolean consumeOnlyLeafNodes) {
		if(!hasChildren.test(tree)) {
			consumer.accept(tree, index, size, depth, parent);
			// return early because no children
			return;
		}

		List<R> children = childrenGetter.apply(tree);
		int count = 0;

		int sizeI = children.size();
		while(count < sizeI) {
			if(count == 0) {
				depth++;
			}

			R subtree = children.get(count);
			if(subtree != null) {
				traverseIndexedTree(count, sizeI, depth, tree, subtree, hasChildren, childrenGetter, consumer, consumeOnlyLeafNodes);
			}

			count++;
		}

		if(count > 0) {
			if(!consumeOnlyLeafNodes) {
				consumer.accept(tree, 0, sizeI, depth - 1, parent);
			}

			depth--;
		}
	}


	public static <R> void traverseTree(int depth, R parent, R tree, Predicate<R> hasChildren, Function<R, Collection<R>> childrenGetter,
			SubtreeConsumer<R> consumer, boolean consumeOnlyLeafNodes) {
		if(!hasChildren.test(tree)) {
			consumer.accept(tree, depth, parent);
			// return early because no children
			return;
		}

		Collection<R> children = childrenGetter.apply(tree);
		int count = 0;

		if(children instanceof RandomAccess && children instanceof List) {
			List<R> childrenList = (List<R>)children;
			int sizeI = childrenList.size();
			while(count < sizeI) {
				if(count == 0) {
					depth++;
				}

				R subtree = childrenList.get(count);
				if(subtree != null) {
					traverseTree(depth, tree, subtree, hasChildren, childrenGetter, consumer, consumeOnlyLeafNodes);
				}

				count++;
			}
		}
		else {
			for(R subtree : children) {
				if(count == 0) {
					depth++;
				}

				if(subtree != null) {
					traverseTree(depth, tree, subtree, hasChildren, childrenGetter, consumer, consumeOnlyLeafNodes);
				}

				count++;
			}
		}

		if(count > 0) {
			if(!consumeOnlyLeafNodes) {
				consumer.accept(tree, depth - 1, parent);
			}

			depth--;
		}
	}


	public static <R> void removeAllNodes(int depth, R parent, R tree, Predicate<R> hasChildren, Function<R, List<R>> childrenGetter,
			SubtreeConsumer<R> consumer, Remover<R, R> remover, boolean consumeOnlyLeafNodes) {
		if(!hasChildren.test(tree)) {
			consumer.accept(tree, depth, parent);
			if(parent != null) {
				remover.accept(parent, tree);
			}
			// return early because no children
			return;
		}

		List<R> children = childrenGetter.apply(tree);

		int sizeI = children.size();
		if(sizeI > 0) {
			depth++;
		}

		for(int i = sizeI - 1; i > -1; i--) {
			R subtree = children.get(i);
			if(subtree != null) {
				removeAllNodes(depth, tree, subtree, hasChildren, childrenGetter, consumer, remover, consumeOnlyLeafNodes);
			}
		}

		if(sizeI > 0) {
			if(!consumeOnlyLeafNodes) {
				consumer.accept(tree, depth - 1, parent);
				if(parent != null) {
					remover.accept(parent, tree);
				}
			}

			depth--;
		}
	}


	public static <R> void traverseNodesByDepth(int index, int size, int depth, R parent, R tree, Predicate<R> hasChildren, Function<R, List<R>> childrenGetter,
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

		List<R> children = childrenGetter.apply(tree);
		int count = 0;

		int sizeI = children.size();
		while(count < sizeI) {
			if(count == 0) {
				depth++;
			}

			R subtree = children.get(count);
			if(subtree != null) {
				traverseNodesByDepth(count, sizeI, depth, tree, subtree, hasChildren, childrenGetter, consumeOnlyLeafNodes, dst);
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


	public static <R> void traverseNodesByDepthInPlace(int index, int size, int depth, R parent, R tree, Predicate<R> hasChildren, Function<R, List<R>> childrenGetter,
			boolean consumeOnlyLeafNodes, List<R> parentStack, TreePathConsumer<R> consumer) {
		if(!hasChildren.test(tree)) {
			consumer.accept(tree, depth, parentStack);
			// return early because no children
			return;
		}

		List<R> children = childrenGetter.apply(tree);
		int count = 0;

		parentStack.add(tree);

		int sizeI = children.size();
		while(count < sizeI) {
			if(count == 0) {
				depth++;
			}

			R subtree = children.get(count);
			if(subtree != null) {
				traverseNodesByDepthInPlace(count, sizeI, depth, tree, subtree, hasChildren, childrenGetter, consumeOnlyLeafNodes, parentStack, consumer);
			}

			count++;
		}

		parentStack.remove(parentStack.size() - 1);

		if(count > 0) {
			if(!consumeOnlyLeafNodes) {
				consumer.accept(tree, depth, parentStack);
			}

			depth--;
		}
	}

}
