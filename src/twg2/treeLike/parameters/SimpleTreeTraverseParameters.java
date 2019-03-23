package twg2.treeLike.parameters;

import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.Predicate;

import twg2.collections.interfaces.ListReadOnly;
import twg2.treeLike.IndexedTreeConsumer;
import twg2.treeLike.TreeTraversalOrder;
import twg2.treeLike.simpleTree.SimpleTree;

/**
 * @author TeamworkGuy2
 * @since 2015-9-1
 * @see AbstractTreeTraverseParameters
 * @see IndexedTreeTraverseParameters
 */
public class SimpleTreeTraverseParameters<T> extends IndexedTreeTraverseParameters<SimpleTree<T>> {
	// package-private
	IndexedTreeConsumer<T> simpleTreeConsumer;

	@Override
	public SimpleTreeTraverseParameters<T> setTree(SimpleTree<T> tree) { super.setTree(tree); return this; }
	@Override
	public SimpleTreeTraverseParameters<T> setOnlyVisitLeaves(boolean onlyVisitLeaves) { super.setOnlyVisitLeaves(onlyVisitLeaves); return this; }
	@Override
	public SimpleTreeTraverseParameters<T> setSkipRoot(boolean skipRoot) { super.setSkipRoot(skipRoot); return this; }
	@Override
	public SimpleTreeTraverseParameters<T> setSkipNullRoot(boolean skipNullRoot) { super.setSkipNullRoot(skipNullRoot); return this; }
	@Override
	public SimpleTreeTraverseParameters<T> setHasChildren(Predicate<SimpleTree<T>> hasChildren) { super.setHasChildren(hasChildren); return this; }
	@Override
	public SimpleTreeTraverseParameters<T> setChildrenGetter(Function<SimpleTree<T>, ListReadOnly<? extends SimpleTree<T>>> childrenGetter) { super.setChildrenGetter(childrenGetter); return this; }
	@Override
	public SimpleTreeTraverseParameters<T> setStartSubtreeFunc(IntConsumer startSubtreeFunc) { super.setStartSubtreeFunc(startSubtreeFunc); return this; }
	@Override
	public SimpleTreeTraverseParameters<T> setEndSubtreeFunc(IntConsumer endSubtreeFunc) { super.setEndSubtreeFunc(endSubtreeFunc); return this; }


	public SimpleTreeTraverseParameters(SimpleTree<T> tree, boolean onlyVisitLeaves, TreeTraversalOrder traversalOrder,
			IndexedTreeConsumer<T> consumer, IntConsumer startSubtreeFunc, IntConsumer endSubtreeFunc) {
		this(tree, onlyVisitLeaves, false, traversalOrder, consumer, startSubtreeFunc, endSubtreeFunc);
	}


	public SimpleTreeTraverseParameters(SimpleTree<T> tree, boolean onlyVisitLeaves, boolean skipNullRoot, TreeTraversalOrder traversalOrder,
			IndexedTreeConsumer<T> consumer, IntConsumer startSubtreeFunc, IntConsumer endSubtreeFunc) {
		super(tree, onlyVisitLeaves, skipNullRoot, traversalOrder, (t) -> t.hasChildren(), (t) -> t.getChildren(), null, startSubtreeFunc, endSubtreeFunc);
		this.setConsumerSimpleTree(consumer, skipNullRoot);
	}


	public IndexedTreeConsumer<T> getConsumerSimpleTree() {
		return this.simpleTreeConsumer;
	}


	public SimpleTreeTraverseParameters<T> setConsumerSimpleTree(IndexedTreeConsumer<T> consumer) {
		return setConsumerSimpleTree(consumer, this.isSkipNullRoot());
	}


	public SimpleTreeTraverseParameters<T> setConsumerSimpleTree(IndexedTreeConsumer<T> consumer, boolean skipNullRoot) {
		this.simpleTreeConsumer = consumer;
		super.setConsumerIndexed((SimpleTree<T> branch, int index, int size, int depth, SimpleTree<T> parentBranch) -> {
			T branchData = branch.getData();
			if(branchData != null || !skipNullRoot) {
				consumer.accept(branchData, index, size, depth, parentBranch != null ? parentBranch.getData() : null);
			}
		});
		return this;
	}


	public static <_D> SimpleTreeTraverseParameters<_D> of(SimpleTree<_D> tree, boolean visitOnlyLeaves, boolean skipNonNull, TreeTraversalOrder traversalOrder) {
		var params = new SimpleTreeTraverseParameters<_D>(tree, visitOnlyLeaves, skipNonNull, traversalOrder, null, null, null);
		return params;
	}


	public static <_D> SimpleTreeTraverseParameters<_D> of(SimpleTree<_D> tree, boolean visitOnlyLeaves, TreeTraversalOrder traversalOrder) {
		var params = new SimpleTreeTraverseParameters<_D>(tree, visitOnlyLeaves, traversalOrder, null, null, null);
		return params;
	}


	public static <_D> SimpleTreeTraverseParameters<_D> leafNodes(SimpleTree<_D> tree, boolean skipNullRoot, TreeTraversalOrder traversalOrder) {
		var params = new SimpleTreeTraverseParameters<_D>(tree, true, skipNullRoot, traversalOrder, null, null, null);
		return params;
	}


	public static <_D> SimpleTreeTraverseParameters<_D> leafNodes(SimpleTree<_D> tree, TreeTraversalOrder traversalOrder) {
		var params = new SimpleTreeTraverseParameters<_D>(tree, true, traversalOrder, null, null, null);
		return params;
	}


	public static <_D> SimpleTreeTraverseParameters<_D> allNodes(SimpleTree<_D> tree, boolean skipNullRoot, TreeTraversalOrder traversalOrder) {
		var params = new SimpleTreeTraverseParameters<_D>(tree, false, skipNullRoot, traversalOrder, null, null, null);
		return params;
	}


	public static <_D> SimpleTreeTraverseParameters<_D> allNodes(SimpleTree<_D> tree, TreeTraversalOrder traversalOrder) {
		var params = new SimpleTreeTraverseParameters<_D>(tree, false, traversalOrder, null, null, null);
		return params;
	}

}
