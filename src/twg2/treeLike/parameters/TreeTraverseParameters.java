package twg2.treeLike.parameters;

import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.Predicate;

/**
 * @author TeamworkGuy2
 * @since 2015-9-5
 * @param <T> the type of data stored in this tree
 * @param <S> the type of child
 * @param <C> the type of child collection
 * @param <F> the type of consumer function that visits each tree node
 */
public interface TreeTraverseParameters<T, S, C extends Iterable<? extends S>, F> {

	public F getConsumer();

	public TreeTraverseParameters<T, S, C, F> setConsumer(F consumer);

	public T getTree();

	public TreeTraverseParameters<T, S, C, F> setTree(T tree);

	public boolean isOnlyVisitLeaves();

	public TreeTraverseParameters<T, S, C, F> setOnlyVisitLeaves(boolean onlyVisitLeaves);

	public boolean isSkipNullRoot();

	public TreeTraverseParameters<T, S, C, F> setSkipNullRoot(boolean skipNullRoot);

	public Predicate<T> getHasChildren();

	public TreeTraverseParameters<T, S, C, F> setHasChildren(Predicate<T> hasChildren);

	public Function<T, C> getChildrenGetter();

	public TreeTraverseParameters<T, S, C, F> setChildrenGetter(Function<T, C> childrenGetter);

	public IntConsumer getStartSubtreeFunc();

	public TreeTraverseParameters<T, S, C, F> setStartSubtreeFunc(IntConsumer startSubtreeFunc);

	public IntConsumer getEndSubtreeFunc();

	public TreeTraverseParameters<T, S, C, F> setEndSubtreeFunc(IntConsumer endSubtreeFunc);

}