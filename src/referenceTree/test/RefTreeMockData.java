package referenceTree.test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import twg2.collections.tuple.Tuples;
import twg2.collections.util.MapBuilder;

/**
 * @author TeamworkGuy2
 * @since 2015-4-23
 */
public class RefTreeMockData {

	static Map<String, List<String>> createTestTreeInfo() {
		Map<String, List<String>> treeInfo = MapBuilder.of(
			Tuples.of("data_A", Arrays.asList()),
			Tuples.of("data_B", Arrays.asList("view_A", "control_B")),
			Tuples.of("data_C", Arrays.asList("data_A", "data_B", "view_C")),
			Tuples.of("view_A", Arrays.asList("data_A")),
			Tuples.of("view_B", Arrays.asList("data_B", "control_A")),
			Tuples.of("view_C", Arrays.asList("data_B", "control_C")),
			Tuples.of("control_A", Arrays.asList()),
			Tuples.of("control_B", Arrays.asList()),
			Tuples.of("control_C", Arrays.asList("data_C", "view_B", "control_A"))
		);
		return treeInfo;
	}


	static Map<String, List<Map.Entry<String, Integer>>> expectTestTreeResult() {

		List<Map.Entry<String, Integer>> data_A = Arrays.asList();

		List<Map.Entry<String, Integer>> data_B = Arrays.asList(
			Tuples.of("control_B", 1),
			Tuples.of("view_A", 1),
				Tuples.of("data_A", 2)
		);

		List<Map.Entry<String, Integer>> data_C = Arrays.asList(
			Tuples.of("data_A", 1),
			Tuples.of("data_B", 1),
				Tuples.of("control_B", 2),
				Tuples.of("view_A", 2),
					Tuples.of("data_A", 3),
			Tuples.of("view_C", 1),
				Tuples.of("data_B", 2),
					Tuples.of("control_B", 3),
					Tuples.of("view_A", 3),
						Tuples.of("data_A", 4),

				Tuples.of("control_C", 2),
					Tuples.of("control_A", 3),
					Tuples.of("!!duplicate", 3), // data_C

					Tuples.of("view_B", 3),
						Tuples.of("control_A", 4),
						Tuples.of("data_B", 4),
							Tuples.of("control_B", 5),
							Tuples.of("view_A", 5),
								Tuples.of("data_A", 6)
		);

		List<Map.Entry<String, Integer>> view_A = Arrays.asList(
			Tuples.of("data_A", 1)
		);

		List<Map.Entry<String, Integer>> view_B = Arrays.asList(
			Tuples.of("control_A", 1),
			Tuples.of("data_B", 1),
				Tuples.of("control_B", 2),
				Tuples.of("view_A", 2),
					Tuples.of("data_A", 3)
		);

		List<Map.Entry<String, Integer>> view_C = Arrays.asList(
			Tuples.of("data_B", 1),
				Tuples.of("control_B", 2),
				Tuples.of("view_A", 2),
					Tuples.of("data_A", 3),

			Tuples.of("control_C", 1),
				Tuples.of("control_A", 2),

				Tuples.of("view_B", 2),
					Tuples.of("control_A", 3),
					Tuples.of("data_B", 3),
						Tuples.of("control_B", 4),
						Tuples.of("view_A", 4),
							Tuples.of("data_A", 5),

				Tuples.of("data_C", 2),
					Tuples.of("data_A", 3),
					Tuples.of("data_B", 3),
						Tuples.of("control_B", 4),
						Tuples.of("view_A", 4),
							Tuples.of("data_A", 5),
					Tuples.of("!!duplicate", 3) // view_C
		);

		List<Map.Entry<String, Integer>> control_A = Arrays.asList();

		List<Map.Entry<String, Integer>> control_B = Arrays.asList();

		List<Map.Entry<String, Integer>> control_C = Arrays.asList(
			Tuples.of("control_A", 1),

			Tuples.of("view_B", 1),
				Tuples.of("control_A", 2),
				Tuples.of("data_B", 2),
					Tuples.of("control_B", 3),
					Tuples.of("view_A", 3),
						Tuples.of("data_A", 4),

			Tuples.of("data_C", 1),
				Tuples.of("data_A", 2),
				Tuples.of("data_B", 2),
					Tuples.of("control_B", 3),
					Tuples.of("view_A", 3),
						Tuples.of("data_A", 4),

				Tuples.of("view_C", 2),
					Tuples.of("!!duplicate", 3), // control_C
					Tuples.of("data_B", 3),
						Tuples.of("control_B", 4),
						Tuples.of("view_A", 4),
							Tuples.of("data_A", 5)
		);

		Map<String, List<Map.Entry<String, Integer>>> treeRes = new HashMap<>();
		treeRes.put("data_A", data_A);
		treeRes.put("data_B", data_B);
		treeRes.put("data_C", data_C);
		treeRes.put("control_A", control_A);
		treeRes.put("control_B", control_B);
		treeRes.put("control_C", control_C);
		treeRes.put("view_A", view_A);
		treeRes.put("view_B", view_B);
		treeRes.put("view_C", view_C);

		return treeRes;
	}

}
