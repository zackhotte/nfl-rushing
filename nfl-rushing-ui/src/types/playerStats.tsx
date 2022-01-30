type PlayerStats = {
  id: number;
  player: string;
  team: string;
  postion: string;
  attempts: number;
  attemptsPerGame: number;
  yards: number;
  average: number;
  yardsPerGame: number;
  touchdowns: number;
  longestRun: string;
  firstDowns: number;
  firstDownPercentage: number;
  runsTwentyPlusYards: number;
  runsFourtyPlusYards: number;
  fumbles: number;
};

export default PlayerStats;
