package twg2.treeLike;

import java.util.function.BiConsumer;

/**
 * @author TeamworkGuy2
 * @since 2015-7-12
 */
@FunctionalInterface
public interface Remover<P, C> extends BiConsumer<P, C> {

	@Override
	public void accept(P source, C valueToRemove);

}
