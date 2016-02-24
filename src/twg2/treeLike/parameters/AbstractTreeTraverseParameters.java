package twg2.treeLike.parameters;

import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.Predicate;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import twg2.treeLike.TreeTraversalOrder;

/**
 * @author TeamworkGuy2
 * @since 2015-8-31
 * @param <T> the type of data in each tree node
 * @param <C> the type of children collection returned by the children getter
 * @param <F> the type of consumer function that visits each tree node
 */
@Accessors(chain = true)
public abstract class AbstractTreeTraverseParameters<T, S, C extends Iterable<? extends S>, F> implements TreeTraverseParameters<T, S, C, F> {
	public @Getter @Setter T tree;
	public @Getter @Setter boolean onlyVisitLeaves;
	/** whether to skip the root node - default false */
	public @Getter @Setter boolean skipRoot;
	/** whether to skip null root node - default false */
	public @Getter @Setter boolean skipNullRoot;
	public @Getter @Setter TreeTraversalOrder traversalOrder;
	public @Getter @Setter Predicate<T> hasChildren;
	public @Getter @Setter Function<T, C> childrenGetter;
	public @Getter @Setter IntConsumer startSubtreeFunc;
	public @Getter @Setter IntConsumer endSubtreeFunc;


	public AbstractTreeTraverseParameters(T tree, boolean onlyVisitLeaves, boolean skipNullRoot, TreeTraversalOrder traversalOrder, Predicate<T> hasChildren,
			Function<T, C> childrenGetter, IntConsumer startSubtreeFunc, IntConsumer endSubtreeFunc) {
		this.tree = tree;
		this.onlyVisitLeaves = onlyVisitLeaves;
		this.skipNullRoot = skipNullRoot;
		this.traversalOrder = traversalOrder;
		this.hasChildren = hasChildren;
		this.childrenGetter = childrenGetter;
		this.startSubtreeFunc = startSubtreeFunc;
		this.endSubtreeFunc = endSubtreeFunc;
	}

/*
	@Override
	public AbstractTreeTraverseParameters<T, S, C, F> setTree(T tree) {
		this.tree = tree;
		return this;
	}


	@Override
	public AbstractTreeTraverseParameters<T, S, C, F> setOnlyVisitLeaves(boolean onlyVisitLeaves) {
		this.onlyVisitLeaves = onlyVisitLeaves;
		return this;
	}


	@Override
	public AbstractTreeTraverseParameters<T, S, C, F> setSkipNullRoot(boolean skipNullRoot) {
		this.skipNullRoot = skipNullRoot;
		return this;
	}


	public AbstractTreeTraverseParameters<T, S, C, F> setTraversalOrder(TreeTraversalOrder traversalOrder) {
		this.traversalOrder = traversalOrder;
		return this;
	}


	@Override
	public AbstractTreeTraverseParameters<T, S, C, F> setHasChildren(Predicate<T> hasChildren) {
		this.hasChildren = hasChildren;
		return this;
	}


	@Override
	public AbstractTreeTraverseParameters<T, S, C, F> setChildrenGetter(Function<T, C> childrenGetter) {
		this.childrenGetter = childrenGetter;
		return this;
	}


	@Override
	public AbstractTreeTraverseParameters<T, S, C, F> setStartSubtreeFunc(IntConsumer startSubtreeFunc) {
		this.startSubtreeFunc = startSubtreeFunc;
		return this;
	}


	@Override
	public AbstractTreeTraverseParameters<T, S, C, F> setEndSubtreeFunc(IntConsumer endSubtreeFunc) {
		this.endSubtreeFunc = endSubtreeFunc;
		return this;
	}
*/
}
