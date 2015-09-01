package twg2.treeLike.parameters;

import java.util.List;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.Predicate;

import lombok.val;
import twg2.treeLike.IndexedSubtreeConsumer;

/**
 * @author TeamworkGuy2
 * @since 2015-8-31
 * @param <T> the type of data in each tree node
 */
public class IndexedTreeTraverseParameters<T> extends AbstractTreeTraverseParameters<T, List<T>> {
	// package-private
	IndexedSubtreeConsumer<T> consumer;

	@Override
	public IndexedTreeTraverseParameters<T> setTree(T tree) { super.setTree(tree); return this; }
	@Override
	public IndexedTreeTraverseParameters<T> setOnlyVisitLeaves(boolean onlyVisitLeaves) { super.setOnlyVisitLeaves(onlyVisitLeaves); return this; }
	@Override
	public IndexedTreeTraverseParameters<T> setSkipNullRoot(boolean skipNullRoot) { super.setSkipNullRoot(skipNullRoot); return this; }
	@Override
	public IndexedTreeTraverseParameters<T> setHasChildren(Predicate<T> hasChildren) { super.setHasChildren(hasChildren); return this; }
	@Override
	public IndexedTreeTraverseParameters<T> setChildrenGetter(Function<T, List<T>> childrenGetter) { super.setChildrenGetter(childrenGetter); return this; }
	@Override
	public IndexedTreeTraverseParameters<T> setStartSubtreeFunc(IntConsumer startSubtreeFunc) { super.setStartSubtreeFunc(startSubtreeFunc); return this; }
	@Override
	public IndexedTreeTraverseParameters<T> setEndSubtreeFunc(IntConsumer endSubtreeFunc) { super.setEndSubtreeFunc(endSubtreeFunc); return this; }


	public IndexedTreeTraverseParameters(T tree, boolean onlyVisitLeaves, Predicate<T> hasChildren, Function<T, ? extends List<T>> childrenGetter,
			IndexedSubtreeConsumer<T> consumer, IntConsumer startSubtreeFunc, IntConsumer endSubtreeFunc) {
		this(tree, onlyVisitLeaves, false, hasChildren, childrenGetter, consumer, startSubtreeFunc, endSubtreeFunc);
	}


	@SuppressWarnings("unchecked")
	public IndexedTreeTraverseParameters(T tree, boolean onlyVisitLeaves, boolean skipNullRoot, Predicate<T> hasChildren, Function<T, ? extends List<T>> childrenGetter,
			IndexedSubtreeConsumer<T> consumer, IntConsumer startSubtreeFunc, IntConsumer endSubtreeFunc) {
		super(tree, onlyVisitLeaves, skipNullRoot, hasChildren, (Function<T, List<T>>) childrenGetter, startSubtreeFunc, endSubtreeFunc);
		this.consumer = consumer;
	}


	public IndexedSubtreeConsumer<T> getConsumerIndexed() {
		return this.consumer;
	}


	public IndexedTreeTraverseParameters<T> setConsumerIndexed(IndexedSubtreeConsumer<T> consumer) {
		this.consumer = consumer;
		return this;
	}


	public static <_T> IndexedTreeTraverseParameters<_T> of(_T tree, boolean onlyVisitLeaves, Predicate<_T> hasChildren, Function<_T, ? extends List<_T>> childrenGetter) {
		val params = new IndexedTreeTraverseParameters<_T>(tree, onlyVisitLeaves, hasChildren, childrenGetter, null, null, null);
		return params;
	}


	public static <_T> IndexedTreeTraverseParameters<_T> leafNodes(_T tree, Predicate<_T> hasChildren, Function<_T, ? extends List<_T>> childrenGetter) {
		val params = new IndexedTreeTraverseParameters<_T>(tree, true, hasChildren, childrenGetter, null, null, null);
		return params;
	}


	public static <_T> IndexedTreeTraverseParameters<_T> allNodes(_T tree, Predicate<_T> hasChildren, Function<_T, ? extends List<_T>> childrenGetter) {
		val params = new IndexedTreeTraverseParameters<_T>(tree, false, hasChildren, childrenGetter, null, null, null);
		return params;
	}

}