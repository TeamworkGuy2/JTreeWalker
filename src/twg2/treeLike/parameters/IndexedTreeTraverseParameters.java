package twg2.treeLike.parameters;

import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.Predicate;

import twg2.collections.interfaces.ListReadOnly;
import twg2.treeLike.IndexedTreeConsumer;
import twg2.treeLike.TreeTraversalOrder;

/**
 * @author TeamworkGuy2
 * @since 2015-8-31
 * @param <T> the type of data in each tree node
 */
public class IndexedTreeTraverseParameters<T> extends AbstractTreeTraverseParameters<T, T, ListReadOnly<? extends T>, IndexedTreeConsumer<T>> {
	// package-private
	IndexedTreeConsumer<T> consumer;

	@Override
	public IndexedTreeTraverseParameters<T> setTree(T tree) { super.setTree(tree); return this; }
	@Override
	public IndexedTreeTraverseParameters<T> setOnlyVisitLeaves(boolean onlyVisitLeaves) { super.setOnlyVisitLeaves(onlyVisitLeaves); return this; }
	@Override
	public IndexedTreeTraverseParameters<T> setSkipRoot(boolean skipRoot) { super.setSkipRoot(skipRoot); return this; }
	@Override
	public IndexedTreeTraverseParameters<T> setSkipNullRoot(boolean skipNullRoot) { super.setSkipNullRoot(skipNullRoot); return this; }
	@Override
	public IndexedTreeTraverseParameters<T> setHasChildren(Predicate<T> hasChildren) { super.setHasChildren(hasChildren); return this; }
	@Override
	public IndexedTreeTraverseParameters<T> setChildrenGetter(Function<T, ListReadOnly<? extends T>> childrenGetter) { super.setChildrenGetter(childrenGetter); return this; }
	@Override
	public IndexedTreeTraverseParameters<T> setStartSubtreeFunc(IntConsumer startSubtreeFunc) { super.setStartSubtreeFunc(startSubtreeFunc); return this; }
	@Override
	public IndexedTreeTraverseParameters<T> setEndSubtreeFunc(IntConsumer endSubtreeFunc) { super.setEndSubtreeFunc(endSubtreeFunc); return this; }


	public IndexedTreeTraverseParameters(T tree, boolean onlyVisitLeaves, TreeTraversalOrder traversalOrder, Predicate<T> hasChildren, Function<T, ListReadOnly<? extends T>> childrenGetter,
			IndexedTreeConsumer<T> consumer, IntConsumer startSubtreeFunc, IntConsumer endSubtreeFunc) {
		this(tree, onlyVisitLeaves, false, traversalOrder, hasChildren, childrenGetter, consumer, startSubtreeFunc, endSubtreeFunc);
	}


	public IndexedTreeTraverseParameters(T tree, boolean onlyVisitLeaves, boolean skipNullRoot, TreeTraversalOrder traversalOrder, Predicate<T> hasChildren, Function<T, ListReadOnly<? extends T>> childrenGetter,
			IndexedTreeConsumer<T> consumer, IntConsumer startSubtreeFunc, IntConsumer endSubtreeFunc) {
		super(tree, onlyVisitLeaves, skipNullRoot, traversalOrder, hasChildren, childrenGetter, startSubtreeFunc, endSubtreeFunc);
		this.consumer = consumer;
	}


	@Override
	public IndexedTreeConsumer<T> getConsumer() {
		return this.consumer;
	}


	@Override
	public IndexedTreeTraverseParameters<T> setConsumer(IndexedTreeConsumer<T> consumer) {
		return setConsumerIndexed(consumer);
	}


	public IndexedTreeConsumer<T> getConsumerIndexed() {
		return this.consumer;
	}


	public IndexedTreeTraverseParameters<T> setConsumerIndexed(IndexedTreeConsumer<T> consumer) {
		this.consumer = consumer;
		return this;
	}


	public static <_T> IndexedTreeTraverseParameters<_T> of(_T tree, boolean onlyVisitLeaves, TreeTraversalOrder traversalOrder, Predicate<_T> hasChildren, Function<_T, ListReadOnly<? extends _T>> childrenGetter) {
		var params = new IndexedTreeTraverseParameters<_T>(tree, onlyVisitLeaves, traversalOrder, hasChildren, childrenGetter, null, null, null);
		return params;
	}


	public static <_T> IndexedTreeTraverseParameters<_T> leafNodes(_T tree, TreeTraversalOrder traversalOrder, Predicate<_T> hasChildren, Function<_T, ListReadOnly<? extends _T>> childrenGetter) {
		var params = new IndexedTreeTraverseParameters<_T>(tree, true, traversalOrder, hasChildren, childrenGetter, null, null, null);
		return params;
	}


	public static <_T> IndexedTreeTraverseParameters<_T> allNodes(_T tree, TreeTraversalOrder traversalOrder, Predicate<_T> hasChildren, Function<_T, ListReadOnly<? extends _T>> childrenGetter) {
		var params = new IndexedTreeTraverseParameters<_T>(tree, false, traversalOrder, hasChildren, childrenGetter, null, null, null);
		return params;
	}

}
