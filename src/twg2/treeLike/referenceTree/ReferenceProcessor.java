package twg2.treeLike.referenceTree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import twg2.collections.builder.MapUtil;
import twg2.collections.tuple.Entries;
import twg2.collections.tuple.Tuples;

/**
 * @author TeamworkGuy2
 * @since 2015-4-19
 */
public class ReferenceProcessor {
	private static final int MAX_REF_DEPTH = 1000;

	/**
	 * @Param <T> the type of reference
	 * @param <R> the type of reference target object
	 * @param refHolders
	 * @param getRefs
	 * @param getRefTarget
	 * @return an entry, the key is a map of references to lists of reference tree entries belonging to each reference
	 * the value is a map of references to the target objects
	 */
	public static <T, R, A extends Collection<R>> Map.Entry<Map<Reference<T, R>, List<ReferenceTreeEntry<T, R>>>, Map<Reference<T, R>, R>> createFirstLevelRefTree(A refHolders,
			Function<R, ? extends Collection<T>> getRefs, Function<T, R> getRefTarget, Function<R, T> getTargetRef) {
		ReferenceTree<T, R> refTree = new ReferenceTree<>();
		Map<Reference<T, R>, R> refIdentifierMap = new HashMap<>();

		for(R refHolder : refHolders) {
			T refInfo = getTargetRef.apply(refHolder);
			Collection<T> holderRefs = getRefs.apply(refHolder);
			List<Reference<T, R>> childRefs = new ArrayList<>();
			for(T holderRef : holderRefs) {
				childRefs.add(new ReferenceImpl<T, R>(holderRef));
			}
			Reference<T, R> ref = new ReferenceImpl<>(refInfo);
			if(!refTree.contains(ref)) {
				refTree.addRef(ref, null, childRefs);
				refIdentifierMap.put(ref, refHolder);
			}
		}

		return Tuples.of(ReferenceTree.getRawSingleLevelTree(refTree), refIdentifierMap);
	}


	/**
	 * @param refHolders a collection of identifiers and reference holders
	 * @param getRefs
	 * @param getRefTarget
	 * @param getTargetRef
	 * @return a list of reference target objects to reference trees
	 */
	public static <T, R, A extends Collection<R>> List<Map.Entry<R, ReferenceTree<T, R>>> createRefTrees(A refHolders, Function<R, ? extends Collection<T>> getRefs,
			Function<T, R> getRefTarget, Function<R, T> getTargetRef, Reference<T, R> duplicateRef) {
		Map.Entry<Map<Reference<T, R>, List<ReferenceTreeEntry<T, R>>>, Map<Reference<T, R>, R>> refTree = createFirstLevelRefTree(refHolders, getRefs, getRefTarget, getTargetRef);
		Map<Reference<T, R>, R> refTreeIds = refTree.getValue();

		Map<Reference<T, R>, Map.Entry<ReferenceTreeEntry<T, R>, R>> refTreeSet = MapUtil.map(refTree.getKey(), (k, v) -> {
			if(v.size() > 1) {
				throw new IllegalStateException("expected exactly one reference entry per reference");
			}
			return Entries.of(k, Entries.of(v.get(0), refTreeIds.get(k)));
		});

		List<Map.Entry<R, ReferenceTree<T, R>>> refTrees = extractReferenceTreesFromFirstLevelRefTree(refTreeSet, (ref, addRefCallback) -> {
			System.out.println("\t******duplicate ref: " + ref);
			addRefCallback.accept(duplicateRef);
		});

		return refTrees;
	}


	public static <T, R> List<List<ReferenceTreeEntry<T, R>>> extractReferencesFromFirstLevelRefTree(Map<Reference<T, R>, ReferenceTreeEntry<T, R>> firstLevelTree,
			BiConsumer<ReferenceTreeEntry<T, R>, Consumer<ReferenceTreeEntry<T, R>>> handleDuplicate) {
		List<List<ReferenceTreeEntry<T, R>>> refRefs = new ArrayList<>();

		for(Map.Entry<Reference<T, R>, ReferenceTreeEntry<T, R>> entry : firstLevelTree.entrySet()) {
			List<ReferenceTreeEntry<T, R>> refs = extractReferenceFromFirstLevelRefTree(firstLevelTree, entry.getValue(), handleDuplicate);
			refRefs.add(refs);
		}

		return refRefs;
	}


	/**
	 * @param firstLevelTree
	 * @param root the root reference of which to get all child references
	 * @param handleDuplicate a function to call with duplicate references and a function which takes a reference and adds it to the list of results
	 * @return a list of references
	 */
	public static <T, R> List<ReferenceTreeEntry<T, R>> extractReferenceFromFirstLevelRefTree(Map<Reference<T, R>, ReferenceTreeEntry<T, R>> firstLevelTree,
			ReferenceTreeEntry<T, R> root, BiConsumer<ReferenceTreeEntry<T, R>, Consumer<ReferenceTreeEntry<T, R>>> handleDuplicate) {
		List<ReferenceTreeEntry<T, R>> resultRefs = new ArrayList<>();
		List<List<ReferenceTreeEntry<T, R>>> childrenStack = new ArrayList<>();

		// prep the stack with the root element's children
		if(root.childrenIm != null && root.childrenIm.size() > 0) {
			List<ReferenceTreeEntry<T, R>> children = new ArrayList<>(root.childrenIm);
			childrenStack.add(children);
		}

		// loop to follow all references (could be a recursive algorithm, but that could easily overflow)
		while(childrenStack.size() > 0) {
			// get the last reference list off the stack
			List<ReferenceTreeEntry<T, R>> children = childrenStack.get(childrenStack.size() - 1);
			// remove and process the last reference
			ReferenceTreeEntry<T, R> ref = children.remove(children.size() - 1);
			// if the reference has child references, add them to stack of reference lists (makes this tree traversal algorithm depth-first based)
			if(ref.childrenIm != null && ref.childrenIm.size() > 0) {
				List<ReferenceTreeEntry<T, R>> refChildren = new ArrayList<>(ref.childrenIm);
				childrenStack.add(refChildren);
			}
			// else if no children, lookup the reference
			else {
				ReferenceTreeEntry<T, R> lookupRef = firstLevelTree.get(ref.ref);
				if(lookupRef != null && lookupRef.childrenIm != null && lookupRef.childrenIm.size() > 0) {
					List<ReferenceTreeEntry<T, R>> refChildren = new ArrayList<>(lookupRef.childrenIm);
					childrenStack.add(refChildren);
				}
			}
			// add the reference to the list of result references
			if(resultRefs.contains(ref)) {
				if(handleDuplicate != null) {
					handleDuplicate.accept(ref, (r) -> resultRefs.add(r));
				}
			}
			else {
				resultRefs.add(ref);
			}

			// maintenance, remove each reference list as it becomes empty
			if(children.size() == 0) {
				childrenStack.remove(childrenStack.size() - 1);
			}
		}

		return resultRefs;
	}


	/**
	 * @param firstLevelTree
	 * @param root the root reference of which to get all child references
	 * @param handleCircularRef a function to call with duplicate references and a function which takes a reference and adds it to the list of results
	 * @return a list of references
	 */
	static <T, R, D> List<Map.Entry<D, ReferenceTree<T, R>>> extractReferenceTreesFromFirstLevelRefTree(Map<Reference<T, R>, Map.Entry<ReferenceTreeEntry<T, R>, D>> firstLevelTree,
			BiConsumer<ReferenceTreeEntry<T, R>, Consumer<Reference<T, R>>> handleCircularRef) {
		List<Map.Entry<D, ReferenceTree<T, R>>> refTrees = new ArrayList<>();

		for(Map.Entry<Reference<T, R>, Map.Entry<ReferenceTreeEntry<T, R>, D>> entry : firstLevelTree.entrySet()) {
			ReferenceTree<T, R> refTree = extractReferenceTreeFromFirstLevelRefTree(firstLevelTree, entry.getValue().getKey(), handleCircularRef);
			refTrees.add(Tuples.of(entry.getValue().getValue(), refTree));
		}

		return refTrees;
	}


	static <T, R, D> ReferenceTree<T, R> extractReferenceTreeFromFirstLevelRefTree(Map<Reference<T, R>, Map.Entry<ReferenceTreeEntry<T, R>, D>> firstLevelTree, ReferenceTreeEntry<T, R> root,
			BiConsumer<ReferenceTreeEntry<T, R>, Consumer<Reference<T, R>>> handleCircularRef) {
		ReferenceTree<T, R> resultTree = new ReferenceTree<>();

		List<List<ReferenceTreeEntry<T, R>>> stackChildRefs = new ArrayList<>();
		List<ReferenceTreeEntry<T, R>> stackParentRefs = new ArrayList<>();

		// prep the stack with the root element
		ReferenceTreeEntry<T, R> rootRefCopy = new ReferenceTreeEntry<T, R>(root.ref, 0, null, null);
		resultTree.addRef(rootRefCopy);

		// prep the stack with the root element's children
		if(root.childrenIm != null && root.childrenIm.size() > 0) {
			List<ReferenceTreeEntry<T, R>> children = new ArrayList<>(root.childrenIm);
			stackChildRefs.add(children);
		}
		stackParentRefs.add(root);

		final StringBuilder depthStyle = new StringBuilder();
		// TODO debug
		System.out.println(depthStyle + "root=" + root + ", children=" + root.childrenIm);
		depthStyle.append("\t");

		// loop to follow all references (could be a recursive algorithm, but that could easily overflow)
		AtomicBoolean added = new AtomicBoolean(false);
		AtomicBoolean notDup = new AtomicBoolean(true);
		int stackSize = 0;
		while((stackSize = stackChildRefs.size()) > 0) {
			added.set(false);
			notDup.set(true);

			if(stackSize > MAX_REF_DEPTH) {
				throw new IllegalStateException("maximum reference stack depth of " + MAX_REF_DEPTH + " reached");
			}

			final int level = stackParentRefs.size();
			// get the last reference list off the stack (makes this tree traversal algorithm depth-first based)
			List<ReferenceTreeEntry<T, R>> children = stackChildRefs.get(stackSize - 1);
			// if this stack is already done being processed, keep moving up the stack
			if(children == null) {
				stackChildRefs.remove(stackSize - 1);

				ReferenceTreeEntry<T, R> removedParent = stackParentRefs.remove(stackParentRefs.size() - 1);
				System.out.println(depthStyle + "remove parent: " + removedParent.getRef() + "");

				// TODO debug
				depthStyle.delete(depthStyle.length() - 1, depthStyle.length());
				System.out.println(depthStyle + "done child stack (remaining-depth=" + stackChildRefs.size() + ")\n");

				continue;
			}
			// remove and process a reference
			ReferenceTreeEntry<T, R> ref = children.remove(children.size() - 1);

			// function to add a reference to the reference tree
			Consumer<Reference<T, R>> addRef = (refToAdd) -> {
				ReferenceTreeEntry<T, R> parent = stackParentRefs.get(stackParentRefs.size() - 1);
				ReferenceTreeEntry<T, R> refCopy = new ReferenceTreeEntry<T, R>(refToAdd, level, parent, null);

				// TODO debug
				System.out.println(depthStyle + "add ref_" + level + "." + children.size() + ": " + ref + ", - parent=" + (parent != null ? parent.getRef() : "null"));

				resultTree.addRef(refCopy);
				parent = refCopy;

				if(notDup.get()) {
					added.set(true);
				}
			};

			// add the reference to the list of result references
			if(stackParentRefs.contains(ref)) {
				if(handleCircularRef != null) {
					notDup.set(false);
					handleCircularRef.accept(ref, addRef);
				}
			}
			else {
				addRef.accept(ref.ref);
			}

			// maintenance, remove each reference list as it becomes empty
			if(children.size() == 0) {
				stackChildRefs.remove(stackChildRefs.size() - 1);
			}

			boolean childrenAdded = false;
			// if the reference was added, check for children
			if(added.get() == true) {
				// if the reference has children, add them as one additional layer to the reference stack
				if(ref.childrenIm != null && ref.childrenIm.size() > 0) {
					if(children.size() == 0) {
						stackChildRefs.add(null);
					}

					List<ReferenceTreeEntry<T, R>> refChildren = new ArrayList<>(ref.childrenIm);
					stackChildRefs.add(refChildren);
					stackParentRefs.add(ref);
					childrenAdded = true;

					// TODO debug
					//System.out.println(depthStyle + "add ref " + level + "." + children.size() + ": " + ref);
					depthStyle.append("\t");
					System.out.println(depthStyle + "has children: " + refChildren);
				}
				// if no children, lookup the reference
				else {
					ReferenceTreeEntry<T, R> lookupRef = firstLevelTree.get(ref.ref).getKey();
					if(lookupRef != null && lookupRef.childrenIm != null && lookupRef.childrenIm.size() > 0) {
						if(children.size() == 0) {
							stackChildRefs.add(null);
						}

						List<ReferenceTreeEntry<T, R>> refChildren = new ArrayList<>(lookupRef.childrenIm);
						stackChildRefs.add(refChildren);
						stackParentRefs.add(ref);
						childrenAdded = true;

						// TODO debug
						//System.out.println(depthStyle + "add ref " + level + "." + children.size() + ": " + ref);
						depthStyle.append("\t");
						System.out.println(depthStyle + "lookup '" + lookupRef.ref + "' children: " + refChildren);
					}
				}
			}
			// if stack is empty and no children added, remove parent
			if(children.size() == 0 && !childrenAdded) {
				ReferenceTreeEntry<T, R> removedParent = stackParentRefs.remove(stackParentRefs.size() - 1);
				System.out.println(depthStyle + "remove parent: " + removedParent.getRef() + "");

				// TODO debug
				depthStyle.delete(depthStyle.length() - 1, depthStyle.length());
				System.out.println(depthStyle + "done child stack (remaining-depth=" + stackChildRefs.size() + ")\n");
			}
		}

		return resultTree;
	}

}
