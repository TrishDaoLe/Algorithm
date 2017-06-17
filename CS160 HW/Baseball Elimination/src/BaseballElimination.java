import java.util.HashMap;
/*******************************************************************
 * 
 * Partner 1: Lyle Pierson Stachecki
 * Partner 2: Trish Dao Le
 * 
 * Partner 1 username: lpiersonstachecki
 * Partner 2 username: dle
 *
 *******************************************************************/
public class BaseballElimination {

	private int max;
	private int teams;
	private int totalRemainingGames;
	private HashMap<String, int[]> hashMap;
	private String[] teamNames;
	private boolean[][] Eliminated;

	/**
	 * create a baseball division from given filename in format specified below
	 * @param filename
	 *  name of input file
	 */
	public BaseballElimination(String filename) {
		max = 0;
		hashMap = new HashMap<String, int[]>();
		In file = new In(filename);
		String line = file.readLine();
		teams = Integer.parseInt(line);
		teamNames = new String[teams];
		String[] tokens;
		for(int i = 0; file.hasNextLine(); i++) {
			line = file.readLine();
			tokens = line.split(" +");
			int[] array;
			String key;
			if (tokens[0].equals("")) {

				key = tokens[1];
				array = new int[tokens.length - 1]; 
			} else {
				key = tokens[0];
				array = new int[tokens.length];
			}
			teamNames[i] = key;
			array[0] = i;
			for(int j = 1; j < array.length; j++) {
				if (tokens[0].equals("")) 
					array[j] = Integer.parseInt(tokens[j + 1]);
				else 
					array[j] = Integer.parseInt(tokens[j]);
			}
			hashMap.put(key, array);
		}
		findMax();
		Eliminated = new boolean[numberOfTeams()][2];
	}

	/**
	 * number of teams
	 * @return the number of teams.
	 */
	public int numberOfTeams() {
		return teams;
	}

	/**
	 * all teams
	 * @return an iterable of all the teams
	 */
	public Iterable<String> teams() {
		return hashMap.keySet();
	}

	/**
	 * number of wins for given team
	 * @param team
	 *  the name of the team
	 * @return
	 *  the number of wins for the given team
	 */
	public int wins(String team) {
		if (!hashMap.containsKey(team))
			throw new IllegalArgumentException("You enterd an invalid team");
		return hashMap.get(team)[1];
	}

	/**
	 * number of losses for given team
	 * @param team
	 *  the name of the team
	 * @return
	 * 	the number of losses for the given team 
	 */
	public int losses(String team) {
		if (!hashMap.containsKey(team))
			throw new IllegalArgumentException("You enterd an invalid team");
		return hashMap.get(team)[2];
	}

	/**
	 * number of remaining games for given team
	 * @param team
	 *  the name of the team
	 * @return
	 *  the number of remaining games for the given team
	 */
	public int remaining(String team){
		if (!hashMap.containsKey(team))
			throw new IllegalArgumentException("You enterd an invalid team");
		return hashMap.get(team)[3];
	}

	/**
	 * number of remaining games between team1 and team2
	 * @param team1
	 *  the name of a team
	 * @param team2
	 *  the name of a team
	 * @return
	 *  the number of remaing games between team1 and team 2
	 */
	public int against(String team1, String team2) {
		if (!hashMap.containsKey(team1) || !hashMap.containsKey(team2))
			throw new IllegalArgumentException("You enterd an invalid team");
		return hashMap.get(team1)[hashMap.get(team2)[0] + 4];
	}

	/**
	 * is given team eliminated?
	 * @param team
	 * @return
	 *  a boolean value. "true" if the team is eliminated, "false" if not
	 */
	public boolean isEliminated(String team) {
		if (Eliminated[hashMap.get(team)[0]][0] == true)
			return Eliminated[hashMap.get(team)[0]][1];
		return trivialElimination(team) || nonTrivialElimination(team);
	}

	/**
	 * subset R of teams that eliminates given team; null if not eliminated
	 * @param team
	 *  the name of the team
	 * @return
	 *  an iterable of the teams that eliminate that team.
	 *  null if the team is not eliminated
	 */
	public Iterable<String> certificateOfElimination(String team) {
		if (!hashMap.containsKey(team))
			throw new IllegalArgumentException("You enterd an invalid team");
		if (!isEliminated(team)) return null;
		Stack<String> certificate = new Stack<String>();
		if (trivialElimination(team)) {
			for (int i = 0; i < numberOfTeams(); i++) {
				if (wins(team) + remaining(team) < wins(teamNames[i]))
					certificate.push(teamNames[i]);
			}
			return certificate;
		}
		int teamsChoose2 = ((numberOfTeams() - 1) * (numberOfTeams() - 2)) / 2;
		int numberOfNodes = numberOfTeams() + 1 + teamsChoose2;
		FlowNetwork FN = new FlowNetwork(numberOfNodes);
		FN = makeFlowNetwork(team, FN, teamsChoose2);
		FordFulkerson ford = new FordFulkerson(FN, FN.V() - 2, FN.V() - 1);
		int a = 0;
		for (int i = 0; i < numberOfTeams() - 1;i ++) {
			if (i >= hashMap.get(team)[0])  a = i + 1;
			else  a = i;
			if (ford.inCut(i + teamsChoose2)) {
				certificate.push(teamNames[a]);
			}
		}
		return certificate;
	}

	/*
	 * finds the most amount of wins by one team
	 * @return
	 *  an int value representing the most amount of wins by a team
	 */
	private void findMax() {
		for (String x :hashMap.keySet()) 
			if (wins(x) > max)
				max = wins(x);
	}
	
	/*
	 * weather a given team is eliminated trivially
	 * @param team
	 *  the name of the team
	 */
	private boolean trivialElimination(String team) {
		Eliminated[hashMap.get(team)[0]][0] = true; 
		Eliminated[hashMap.get(team)[0]][1] = wins(team) + remaining(team) < max;
		return Eliminated[hashMap.get(team)[0]][1];
	}

	/*
	 * weather a given team is non trivially eliminated or not
	 * @param team
	 *  the name of the team
	 * @return
	 *  a boolean value "true" if eliminated, "false" if not
	 */
	private boolean nonTrivialElimination(String team) {
		int teamsChoose2 = ((numberOfTeams() - 1) * (numberOfTeams() - 2)) / 2;
		int numberOfNodes = numberOfTeams() + 1 + teamsChoose2;
		FlowNetwork FN = new FlowNetwork(numberOfNodes);
		FN = makeFlowNetwork(team, FN, teamsChoose2);
		FordFulkerson ford = new FordFulkerson(FN, FN.V() - 2, FN.V() - 1);
		Eliminated[hashMap.get(team)[0]][1] = 
				ford.value() < totalRemainingGames;
		return Eliminated[hashMap.get(team)[0]][1];
	}

	/*
	 * creates edges for a flow network given a FlowNetwork 
	 * a team and the number of game vertices
	 * @param team
	 *  the name of the team
	 * @param FN
	 *  a FlowNetwork of the right size
	 * @param gameVertices
	 *  the number of game vertices
	 * @return
	 *  a complete FlowNetwork
	 */
	private FlowNetwork makeFlowNetwork(String team, FlowNetwork FN, int gameVertices) {
		int source = FN.V() - 2;
		int sink = FN.V() - 1;
		int a = 0;
		int b = 0;
		int k = 0;
		totalRemainingGames = 0;
		
		//creates the edges between the source and game vertices
		// as well as the edges between the game vertices and the teams
		for (int i = 0; i < numberOfTeams() - 2; i++) {
			if (i >= hashMap.get(team)[0])  a = i + 1;
			else  a = i;
			for (int j = i + 1; j < numberOfTeams() - 1; j++) {
				if (j >= hashMap.get(team)[0])  b = j + 1;
				else  b = j;
				int capacity = against(teamNames[a], teamNames[b]);
				totalRemainingGames += capacity;
				FlowEdge e = new FlowEdge(source, k, capacity);
				FN.addEdge(e);
				e = new FlowEdge(k, i + gameVertices, Double.POSITIVE_INFINITY);
				FN.addEdge(e);
				e = new FlowEdge(k, j + gameVertices, Double.POSITIVE_INFINITY);
				FN.addEdge(e);
				k++;
			}

		}
		
		//creates the edges between the teams and the sink
		for (int i = 0; i < numberOfTeams() - 1; i++) {
			if (i >= hashMap.get(team)[0])  a = i + 1;
			else  a = i;
			int capacity = wins(team) + remaining(team) - wins(teamNames[a]);
			FlowEdge e = new FlowEdge(i + gameVertices, sink, capacity);
			FN.addEdge(e);
		}
		return FN;
	}

	/**
	 * unit testing
	 **/
	public static void main(String[] args) {
		BaseballElimination division = new BaseballElimination(args[0]);
		for (String team : division.teams()) {
			if (division.isEliminated(team)) {
				StdOut.print(team + " is eliminated by the subset R = { ");
				for (String t : division.certificateOfElimination(team))
					StdOut.print(t + " ");
				StdOut.println("}");
			}
			else {
				StdOut.println(team + " is not eliminated");
			}
		}
	}
}
