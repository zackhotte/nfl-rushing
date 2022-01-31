type Response<T> = {
  content: T[];
  pageable: Pageable;
  empty: boolean;
  first: boolean;
  last: boolean;
  number: number;
  numberOfElements: number;
  size: number;
  sort: Sortable;
  totalElements: number;
  totalPages: 33;
};

type Sortable = {
  empty: boolean;
  sorted: boolean;
  unsorted: boolean;
};

type Pageable = {
  sort: Sortable;
  offset: number;
  pageNumber: number;
  pageSize: number;
  paged: boolean;
  unpaged: boolean;
};

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

export type { PlayerStats, Response };
