package twg2.treeLike;

import java.util.Collection;
import java.util.List;
import java.util.RandomAccess;
import java.util.function.Function;
import java.util.function.Predicate;

import twg2.treeLike.simpleTree.SimpleTree;
import twg2.treeLike.simpleTree.SimpleTreeImpl;

/**
 * @author TeamworkGuy2
 * @since 2015-8-29
 */
public class TreeBuilder {

	private TreeBuilder() { throw new AssertionError("cannot instantiate static class TreeBuilder"); }


	public static <R> SimpleTreeImpl<R> build(R srcTree, Predicate<R> hasChildren, Function<R, Collection<R>> childrenGetter) {
		SimpleTreeImpl<R> tree = new SimpleTreeImpl<>(null);

		buildTree(tree, 0, null, srcTree, hasChildren, childrenGetter, false);

		return tree;
	}


	public static <R> void buildTree(SimpleTree<R> dstTree, int depth, R parent, R tree, Predicate<R> hasChildren, Function<R, Collection<R>> childrenGetter, boolean consumeOnlyLeafNodes) {
		if(!hasChildren.test(tree)) {
			dstTree.addChild(tree);
			// return early because no children
			return;
		}

		SimpleTree<R> subTree = null;
		Collection<R> children = childrenGetter.apply(tree);
		int count = 0;

		if(children instanceof RandomAccess && children instanceof List) {
			List<R> childrenList = (List<R>)children;
			int sizeI = childrenList.size();
			while(count < sizeI) {
				if(count == 0) {
					// pre-order
					if(!consumeOnlyLeafNodes) {
						subTree = dstTree.addChild(tree);
					}
					depth++;
				}

				R subtree = childrenList.get(count);
				if(subtree != null) {
					buildTree(subTree, depth, tree, subtree, hasChildren, childrenGetter, consumeOnlyLeafNodes);
				}

				count++;
			}
		}
		else {
			for(R subtree : children) {
				if(count == 0) {
					// pre-order
					if(!consumeOnlyLeafNodes) {
						subTree = dstTree.addChild(tree);
					}
					depth++;
				}

				if(subtree != null) {
					buildTree(subTree, depth, tree, subtree, hasChildren, childrenGetter, consumeOnlyLeafNodes);
				}

				count++;
			}
		}

		// post-order
		if(count > 0) {
			depth--;
		}
	}

}
