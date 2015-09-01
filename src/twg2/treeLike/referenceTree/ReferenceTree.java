package twg2.treeLike.referenceTree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * @author TeamworkGuy2
 * @since Apr 19, 2015
 */
public class ReferenceTree<T, R> {
	private static final int DEFAULT_LEVEL = 0;
	private Map<Reference<T, R>, List<ReferenceTreeEntry<T, R>>> refEntries;


	public ReferenceTree() {
		this.refEntries = new HashMap<>();
	}


	public boolean contains(Reference<T, R> ref) {
		return getRefEntry(ref) != null;
	}


	public void forEachRef(BiConsumer<T, ReferenceTreeEntry<T, R>> action) {
		refEntries.forEach((ref, refList) -> {
			refList.forEach((refEntry) -> {
				action.accept(ref.getRef(), refEntry);
			});
		});
	}


	public List<ReferenceTreeEntry<T, R>> getRefEntry(Reference<T, R> ref) {
		return this.refEntries.get(ref);
	}


	public void addRef(ReferenceTreeEntry<T, R> refEntry) {
		List<ReferenceTreeEntry<T, R>> refs = this.refEntries.get(refEntry.ref);
		if(refs == null) {
			refs = new ArrayList<>();
			this.refEntries.put(refEntry.ref, refs);
		}
		refs.add(refEntry);
	}


	public ReferenceTreeEntry<T, R> addRef(Reference<T, R> ref) {
		ReferenceTreeEntry<T, R> refEntry = new ReferenceTreeEntry<>(ref, DEFAULT_LEVEL);
		addRef(refEntry);
		return refEntry;
	}


	public ReferenceTreeEntry<T, R> addRef(Reference<T, R> ref, ReferenceTreeEntry<T, R> refParent) {
		ReferenceTreeEntry<T, R> refEntry = new ReferenceTreeEntry<>(ref, refParent.level + 1, refParent, null);
		addRef(refEntry);
		return refEntry;
	}


	public ReferenceTreeEntry<T, R> addRef(Reference<T, R> ref, ReferenceTreeEntry<T, R> refParent, Collection<Reference<T, R>> refChildren) {
		int refLevel = refParent != null ? refParent.level + 1 : DEFAULT_LEVEL;
		int refChildLevel = refLevel + 1;
		List<ReferenceTreeEntry<T, R>> refChildEntries = new ArrayList<>();
		for(Reference<T, R> refChild : refChildren) {
			refChildEntries.add(new ReferenceTreeEntry<>(refChild, refChildLevel));
		}
		ReferenceTreeEntry<T, R> refEntry = new ReferenceTreeEntry<>(ref, DEFAULT_LEVEL, refParent, refChildEntries);
		addRef(refEntry);
		return refEntry;
	}


	@Override
	public String toString() {
		return refEntries.toString();
	}


	public static <T, R> ReferenceTree<T, R> createSingleLevelTree(Map<Reference<T, R>, List<ReferenceTreeEntry<T, R>>> singleLevelTree) {
		ReferenceTree<T, R> tree = new ReferenceTree<>();
		tree.refEntries = singleLevelTree;
		return tree;
	}


	public static <T, R> Map<Reference<T, R>, List<ReferenceTreeEntry<T, R>>> getRawSingleLevelTree(ReferenceTree<T, R> tree) {
		return tree.refEntries;
	}


	/** Extracts a reference tree into a list of reference lists
	 * The first reference list contains the tree's root reference, the second reference list contains the root's children,
	 * the third reference list contains the children of the root's children, etc.
	 * @param tree
	 * @return A list of {@link ReferenceTreeEntry} lists where each list corresponds to a level of the original tree
	 */
	public static <T, R> List<List<ReferenceTreeEntry<T, R>>> treeToLevels(ReferenceTree<T, R> tree) {
		List<List<ReferenceTreeEntry<T, R>>> levels = new ArrayList<>();

		Map<Reference<T, R>, List<ReferenceTreeEntry<T, R>>> entriesCopy = new HashMap<>(tree.refEntries);

		int i = 0;
		while(entriesCopy.size() > 0) {
			List<ReferenceTreeEntry<T, R>> thisLvl = new ArrayList<>();

			Map.Entry<Reference<T, R>, List<ReferenceTreeEntry<T, R>>> entry = null;
			Iterator<Map.Entry<Reference<T, R>, List<ReferenceTreeEntry<T, R>>>> iter = entriesCopy.entrySet().iterator();
			while(iter.hasNext()) {
				entry = iter.next();
				Iterator<ReferenceTreeEntry<T, R>> refIter = entry.getValue().iterator();
				while(refIter.hasNext()) {
					ReferenceTreeEntry<T, R> ref = refIter.next();
					if(ref.level == i) {
						thisLvl.add(ref);
						refIter.remove();
					}
				}
				if(entry.getValue().size() == 0) {
					iter.remove();
				}
			}

			levels.add(thisLvl);
			i++;
		}

		return levels;
	}


	public static <T, R> boolean diffTrees(ReferenceTree<T, R> tree1, ReferenceTree<T, R> tree2) {
		List<List<ReferenceTreeEntry<T, R>>> t1Levels = treeToLevels(tree1);
		List<List<ReferenceTreeEntry<T, R>>> t2Levels = treeToLevels(tree2);

		int diffLevels = 0;
		for(int i = 0, size = Math.min(t1Levels.size(), t2Levels.size()); i < size; i++) {
			List<ReferenceTreeEntry<T, R>> t1LvlI = t1Levels.get(i);
			List<ReferenceTreeEntry<T, R>> t2LvlI = t2Levels.get(i);
			List<ReferenceTreeEntry<T, R>> t1LvlIRemaining = new ArrayList<>(t1LvlI);
			List<ReferenceTreeEntry<T, R>> t2LvlIRemaining = new ArrayList<>(t2LvlI);

			t1LvlIRemaining.removeAll(t2LvlI);
			t2LvlIRemaining.removeAll(t1LvlI);

			List<ReferenceTreeEntry<T, R>> remaining = new ArrayList<>(t1LvlIRemaining);
			remaining.addAll(t2LvlIRemaining);

			if(remaining.size() > 0) {
				diffLevels++;
			}

			System.out.println("diff lvl " + i + ": " + remaining + " (orig: " + t1LvlI.size() + ", " + t2LvlI.size() + ")");
		}

		if(t1Levels.size() != t2Levels.size()) {
			System.out.println("tree 1 '" +
					(t1Levels.size() > 0 ? t1Levels.get(0).size() > 0 ? t1Levels.get(0).get(0) : t1Levels.get(0) : "") +
					"' size " + t1Levels.size() + " does not equal tree 2 '" +
					(t2Levels.size() > 0 ? t2Levels.get(0).size() > 0 ? t2Levels.get(0).get(0) : t2Levels.get(0) : "") +
					"' size " + t2Levels.size());
		}

		return diffLevels == 0;
	}

}
