package twg2.treeLike.parameters;

import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.Predicate;

import twg2.treeLike.TreeTraversalOrder;
import lombok.Getter;

/**
 * @author TeamworkGuy2
 * @since 2015-8-31
 * @param <T> the type of data in each tree node
 * @param <C> the type of children collection returned by the children getter
 */
public abstract class AbstractTreeTraverseParameters<T, C extends Iterable<T>> {
	public @Getter T tree;
	public @Getter boolean onlyVisitLeaves;
	/** whether to skip null root node - default false */
	public @Getter boolean skipNullRoot;
	public @Getter TreeTraversalOrder traversalOrder;
	public @Getter Predicate<T> hasChildren;
	public @Getter Function<T, C> childrenGetter;
	public @Getter IntConsumer startSubtreeFunc;
	public @Getter IntConsumer endSubtreeFunc;


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


	public AbstractTreeTraverseParameters<T, C> setTree(T tree) {
		this.tree = tree;
		return this;
	}


	public AbstractTreeTraverseParameters<T, C> setOnlyVisitLeaves(boolean onlyVisitLeaves) {
		this.onlyVisitLeaves = onlyVisitLeaves;
		return this;
	}


	public AbstractTreeTraverseParameters<T, C> setSkipNullRoot(boolean skipNullRoot) {
		this.skipNullRoot = skipNullRoot;
		return this;
	}


	public AbstractTreeTraverseParameters<T, C> setTraversalOrder(TreeTraversalOrder traversalOrder) {
		this.traversalOrder = traversalOrder;
		return this;
	}


	public AbstractTreeTraverseParameters<T, C> setHasChildren(Predicate<T> hasChildren) {
		this.hasChildren = hasChildren;
		return this;
	}


	public AbstractTreeTraverseParameters<T, C> setChildrenGetter(Function<T, C> childrenGetter) {
		this.childrenGetter = childrenGetter;
		return this;
	}


	public AbstractTreeTraverseParameters<T, C> setStartSubtreeFunc(IntConsumer startSubtreeFunc) {
		this.startSubtreeFunc = startSubtreeFunc;
		return this;
	}


	public AbstractTreeTraverseParameters<T, C> setEndSubtreeFunc(IntConsumer endSubtreeFunc) {
		this.endSubtreeFunc = endSubtreeFunc;
		return this;
	}

}
