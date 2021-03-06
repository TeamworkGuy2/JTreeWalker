package twg2.treeLike.test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Test;

import twg2.collections.builder.MapBuilder;
import twg2.treeLike.TreeBuilder;
import twg2.treeLike.simpleTree.SimpleTree;
import twg2.tuple.Entries;

/**
 * @author TeamworkGuy2
 * @since 2015-8-29
 */
public class TreeBuilderTest {

	@Test
	public void testBuild() {
		Map<String, Object> mapData = map(
			pair("lvl1 - 1/3", map(
			)),
			pair("lvl1 - 2/3", Arrays.asList(
				"lvl2 - 1/2",
				"lvl2 - 2/2"
			)),
			pair("lvl1 - 3/3", map(
				pair("lvl2 - 1/4", Arrays.asList(
					"lvl3 - 1/2",
					"lvl3 - 2/2"
				)),
				pair("lvl2 - 2/4", "val2.2"),
				pair("lvl2 - 2/4", "val2.3"),
				pair("lvl2 - 2/4", map(
					pair("lvl3 - 1/2", "val3.1"),
					pair("lvl3 - 2/2", map(
						pair("lvl4 - 1/2", "val4.1"),
						pair("lvl4 - 2/2", Arrays.asList("val4.2.1", "val4.2.2"))
					))
				))
			))
		);

		SimpleTree<Object> treeData = TreeBuilder.build(mapData.entrySet(),
				(obj) -> size(obj) > 0,
				(obj) -> children(obj));

		System.out.println(treeData);
	}


	public static int size(Object obj) {
		Collection<Object> children = children(obj);
		return children != null ? children.size() : 0;
	}


	public static Collection<Object> children(Object obj) {
		Collection<Object> childs = _children(obj);

		// TODO debugging
		//System.out.println("children of (" + (childs != null ? childs.size() : 0) + ") " + obj.getClass() + ": " + obj);

		return childs;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Collection<Object> _children(Object obj) {
		if(obj == null) {
			return null;
		}

		if(obj instanceof Map.Entry) {
			Object entryVal = ((Map.Entry)obj).getValue();
			if(entryVal instanceof Map) {
				return ((Map)entryVal).entrySet();
			}
			else if(entryVal instanceof Collection) {
				return ((Collection)entryVal);
			}
			else if(entryVal instanceof Set) {
				return ((Set)entryVal);
			}
			else {
				return null;
			}
		}
		else if(obj instanceof Map) {
			return ((Map)obj).entrySet();
		}
		else if(obj instanceof Collection) {
			return ((Collection)obj);
		}
		else if(obj instanceof Set) {
			return ((Set)obj);
		}
		else {
			return null;
		}
	}


	public static final <K, V> Map.Entry<K, V> pair(K key, V value) {
		return Entries.of(key, value);
	}


	@SafeVarargs
	public static final <K, V> Map<K, V> map(Entry<? extends K, ? extends V>... entries) {
		@SuppressWarnings("unchecked")
		Entry<K, V>[] entriesTyped = (Entry<K, V>[])entries;
		return MapBuilder.of(entriesTyped);
	}

}
